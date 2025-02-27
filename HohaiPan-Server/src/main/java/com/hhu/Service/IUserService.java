package com.hhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhu.dto.UserDTO.*;
import com.hhu.entity.User;
import com.hhu.result.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IUserService extends IService<User> {
    Result<String> userLogin(UserLoginDTO userDTO);

    Result sendEmailCode(@NotNull String email, @NotNull Integer emailCodeType);

    Result emailLogin(UserEmailLoginDTO userDTO);

    Result resetPwd(UserResetPwdDTO userDTO);

    Result register(@Valid UserRegisterDTO userDTO);

    Result getUserInfo(Long id);

    Result updateUserAvatar(UserAvatarUpdateDTO userAvatarUpdateDTO);
}
