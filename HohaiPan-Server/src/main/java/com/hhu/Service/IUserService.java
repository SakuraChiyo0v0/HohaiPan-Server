package com.hhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhu.hhu.dto.UserDTO;
import com.hhu.hhu.entity.User;
import com.hhu.result.Result;

public interface IUserService extends IService<User> {
    Result<String> userLogin(UserDTO userDTO);
}
