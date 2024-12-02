package com.hhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhu.hhu.dto.UserDTO;
import com.hhu.hhu.entity.User;

public interface IUserService extends IService<User> {
    User userLogin(UserDTO userDTO);
}
