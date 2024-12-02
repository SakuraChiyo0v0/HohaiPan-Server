package com.hhu.service.Impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhu.constant.UserStatusConstant;
import com.hhu.exception.AccountLockedException;
import com.hhu.exception.InvalidParamException;
import com.hhu.exception.NotFoundException;
import com.hhu.hhu.dto.UserDTO;
import com.hhu.hhu.entity.User;
import com.hhu.mapper.UserMapper;
import com.hhu.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.hhu.constant.MessageConstant.*;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User userLogin(UserDTO userDTO) {
        String email = userDTO.getEmail();
        if(StrUtil.isEmpty(email)){
            throw new InvalidParamException("邮箱不能为空");
        }
        User user = lambdaQuery().eq(User::getEmail,email).one();
        if(user==null){
            //用户不存在(邮箱不存在)
            throw new NotFoundException(ACCOUNT_NOT_FOUND);
        }
        if(user.getStatus().equals(UserStatusConstant.LOCKED)){
            //用户被锁定
            throw new AccountLockedException(ACCOUNT_LOCKED);
        }
        //进行MD5加密看是否相同
        String loginPassword = MD5.create().digestHex16(userDTO.getPassword());
        if(!loginPassword.equals(user.getPassword())){
            //密码错误
            throw new InvalidParamException(PASSWORD_ERROR);
        }
        return user;
    }
}
