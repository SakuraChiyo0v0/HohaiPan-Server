package com.hhu.controller;

import com.hhu.hhu.dto.UserDTO;
import com.hhu.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO user){
        log.info("用户登录:{}",user);
        return Result.success();
    }
}
