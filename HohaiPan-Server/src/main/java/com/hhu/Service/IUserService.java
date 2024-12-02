package com.hhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhu.hhu.dto.UserDTO;
import com.hhu.hhu.entity.User;
import com.hhu.result.Result;
import jakarta.validation.constraints.NotNull;

public interface IUserService extends IService<User> {
    Result<String> userLogin(UserDTO userDTO);

    Result sendEmailCode(@NotNull String email, @NotNull Integer type);
}
