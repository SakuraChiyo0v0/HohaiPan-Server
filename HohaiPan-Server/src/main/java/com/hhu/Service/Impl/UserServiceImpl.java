package com.hhu.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhu.dto.UserDTO.UserEmailLoginDTO;
import com.hhu.dto.UserDTO.UserLoginDTO;
import com.hhu.dto.UserDTO.UserRegisterDTO;
import com.hhu.dto.UserDTO.UserResetPwdDTO;
import com.hhu.entity.User;
import com.hhu.enums.EmailCodeType;
import com.hhu.exception.DatabaseException;
import com.hhu.exception.UserAccountException;
import com.hhu.mapper.UserMapper;
import com.hhu.properties.SystemConfigProperties;
import com.hhu.vo.UserVO;
import com.hhu.properties.EmailCodeProperties;
import com.hhu.properties.JwtProperties;
import com.hhu.result.Result;
import com.hhu.service.IUserService;
import com.hhu.constant.UserStatusConstant;
import com.hhu.exception.InvalidParamException;
import com.hhu.utils.HHUEmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hhu.constant.MessageConstant.*;
import static com.hhu.constant.RedisConstant.LOGIN_TOKEN_KEY;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private EmailCodeProperties emailCodeProperties;
    @Autowired
    private HHUEmailUtils hhuEmailUtils;
    @Autowired
    private SystemConfigProperties systemConfigProperties;

    @Override
    public Result<String> userLogin(UserLoginDTO userDTO) {
        String email = userDTO.getEmail();
        User user = validateUserByEmail(email);
        //进行MD5加密看是否相同
        String loginPassword = MD5.create().digestHex16(userDTO.getPassword());
        if (!loginPassword.equals(user.getPassword())) {
            //密码错误
            throw new InvalidParamException(PASSWORD_ERROR);
        }
        return Result.success(loginSuccess(user));
    }

    @Override
    public Result emailLogin(UserEmailLoginDTO userDTO) {
        String email = userDTO.getEmail();
        CheckEmailCode(email, userDTO.getEmailCode(), EmailCodeType.EmailLogin.getCode());
        User user = validateUserByEmail(email);
        return Result.success(loginSuccess(user));
    }

    @Override
    public Result resetPwd(UserResetPwdDTO userDTO) {
        String email = userDTO.getEmail();
        User user = validateUserByEmail(email);
        CheckEmailCode(userDTO.getEmail(), userDTO.getEmailCode(), EmailCodeType.ResetPwd.getCode());
        String oldPassword = user.getPassword();
        String newPassword = MD5.create().digestHex16(userDTO.getPassword());
        if (newPassword.equals(oldPassword)) {
            //新密码与旧密码相同
            throw new InvalidParamException(SAME_PASSWORD);
        }
        log.info("用户{}重置密码", user.getUserId());
        boolean isUpdate = lambdaUpdate().eq(User::getUserId, user.getUserId())
                .set(User::getPassword, newPassword)
                .update();
        if (!isUpdate) {
            throw new DatabaseException(UPDATE_ERROR);
        }
        return Result.success();
    }

    @Override
    public Result register(UserRegisterDTO userDTO) {
        String email = userDTO.getEmail();
        CheckEmailCode(userDTO.getEmail(), userDTO.getEmailCode(), EmailCodeType.Register.getCode());
        User user = lambdaQuery().eq(User::getEmail, email).one();
        if (user != null) {
            throw new UserAccountException(ACCOUNT_EXIST);
        }
        User sameNickNameUser = lambdaQuery().eq(User::getNickname, userDTO.getNickname()).one();
        if (sameNickNameUser != null) {
            throw new UserAccountException(NICKNAME_EXIST);
        }
        User newUser = new User();
        BeanUtil.copyProperties(userDTO, newUser);

        //密码进行MD5加密
        String md5Password = MD5.create().digestHex16(userDTO.getPassword());
        log.info("加密前后密码:{}-{}", userDTO.getPassword(), md5Password);
        newUser.setPassword(md5Password);

        //Hutool生成雪花ID
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        newUser.setUserId(snowflake.nextId());

        log.info("用户{}注册", newUser.getUserId());
        save(newUser);
        return Result.success();
    }

    @Override
    public Result sendEmailCode(String email, Integer emailCodeType) {
        //获得验证码 存入redis
        Integer emailCode = (int) ((Math.random() * 9 + 1) * 100000);
        String prefix = EmailCodeType.getPrefix(emailCodeType);
        String key = prefix + email;
        stringRedisTemplate.opsForValue().set(key, String.valueOf(emailCode),
                emailCodeProperties.getExpireTime(), TimeUnit.SECONDS);
        hhuEmailUtils.sendEmail(email, emailCode, EmailCodeType.getSubject(emailCodeType));
        return Result.success();
    }

    private void CheckEmailCode(String email, String emailCode, Integer emailCodeType) {
        String prefix = EmailCodeType.getPrefix(emailCodeType);
        String key = prefix + email;
        String settingCode = stringRedisTemplate.opsForValue().get(key);
        if (systemConfigProperties.isCheckCodeOpen()) {
            if (StrUtil.isBlank(settingCode)) {
                throw new InvalidParamException(EMAIL_CODE_NOT_EXIST);
            }
            if (!StrUtil.equals(settingCode, emailCode)
                    && !systemConfigProperties.getUniversalCode().equals(emailCode)) {
                throw new InvalidParamException(EMAIL_CODE_ERROR);
            }
        }
        //判断校验通过 删除redis中的缓存
        stringRedisTemplate.delete(key);
    }

    private User validateUserByEmail(String email) {
        User user = lambdaQuery().eq(User::getEmail, email).one();
        if (user == null) {
            //用户不存在(邮箱不存在)
            throw new UserAccountException(ACCOUNT_NOT_EXIST);
        }
        if (!UserStatusConstant.ENABLE.equals(user.getStatus())) {
            //用户非启用状态
            throw new UserAccountException(ACCOUNT_LOCKED);
        }
        return user;
    }

    private String loginSuccess(User user) {
        //登陆成功 发放jwt令牌
        String token = UUID.randomUUID().toString(true);
        String tokenKey = LOGIN_TOKEN_KEY + token;

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        Map<String, Object> map = BeanUtil.beanToMap(userVO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue == null ? "" : fieldValue.toString()));

        stringRedisTemplate.opsForHash().putAll(tokenKey, map);
        //TODO 实现控制单用户的多TOKEN控制
        stringRedisTemplate.expire(tokenKey, jwtProperties.getExpireTime(), TimeUnit.SECONDS);
        //更新用户登录时间
        boolean isUpdate = lambdaUpdate().eq(User::getUserId, user.getUserId())
                .set(User::getLoginDate, LocalDateTime.now())
                .set(User::getUpdateTime, LocalDateTime.now())
                .set(User::getCreateBy, user.getUserId())
                .update();
        if(!isUpdate){
            throw new DatabaseException(UPDATE_ERROR);
        }
        log.info("用户{}登录成功", user.getUserId());
        return token;
    }
}
