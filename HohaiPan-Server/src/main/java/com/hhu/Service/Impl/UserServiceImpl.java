package com.hhu.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhu.exception.AccountLockedException;
import com.hhu.exception.NotFoundException;
import com.hhu.hhu.entity.User;
import com.hhu.hhu.enums.EmailCodeType;
import com.hhu.hhu.vo.UserVO;
import com.hhu.properties.EmailCodeProperties;
import com.hhu.properties.JwtProperties;
import com.hhu.result.Result;
import com.hhu.service.IUserService;
import com.hhu.constant.UserStatusConstant;
import com.hhu.exception.InvalidParamException;
import com.hhu.hhu.dto.UserDTO;
import com.hhu.mapper.UserMapper;
import com.hhu.utils.HHUEmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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

    @Override
    public Result<String> userLogin(UserDTO loginUserDTO) {
        String email = loginUserDTO.getEmail();
        if (StrUtil.isEmpty(email)) {
            throw new InvalidParamException("邮箱不能为空");
        }
        User user = lambdaQuery().eq(User::getEmail, email).one();
        if (user == null) {
            //用户不存在(邮箱不存在)
            throw new NotFoundException(ACCOUNT_NOT_FOUND);
        }
        if (!UserStatusConstant.ENABLE.equals(user.getStatus())) {
            //用户非启用状态
            throw new AccountLockedException(ACCOUNT_LOCKED);
        }
        //进行MD5加密看是否相同
        String loginPassword = MD5.create().digestHex16(loginUserDTO.getPassword());
        if (!loginPassword.equals(user.getPassword())) {
            //密码错误
            throw new InvalidParamException(PASSWORD_ERROR);
        }
        log.info("用户登录成功");
        //登陆成功 发放jwt令牌
        String token = UUID.randomUUID().toString(true);
        String tokenKey = LOGIN_TOKEN_KEY + token;

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        Map<String, Object> map = BeanUtil.beanToMap(userVO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue == null ? "" : fieldValue.toString()));

        stringRedisTemplate.opsForHash().putAll(tokenKey, map);
        stringRedisTemplate.expire(tokenKey, jwtProperties.getExpireTime(), TimeUnit.SECONDS);
        return Result.success(token);
    }

    @Override
    public Result sendEmailCode(String email, Integer type) {
        //获得验证码 存入redis
        Integer emailCode = (int)((Math.random() * 9 + 1) * 100000);
        String prefix = EmailCodeType.getPrefix(type);
        String key = prefix + email;
        stringRedisTemplate.opsForValue().set(key, String.valueOf(emailCode),
                emailCodeProperties.getExpireTime(),TimeUnit.SECONDS);
        hhuEmailUtils.sendEmail(email,emailCode,EmailCodeType.getSubject(type));
        return Result.success();
    }
}
