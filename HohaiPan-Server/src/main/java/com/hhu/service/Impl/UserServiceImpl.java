package com.hhu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhu.hhu.entity.User;
import com.hhu.mapper.UserMapper;
import com.hhu.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
