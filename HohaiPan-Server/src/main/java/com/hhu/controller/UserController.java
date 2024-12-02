package com.hhu.controller;
import com.hhu.hhu.dto.UserDTO;
import com.hhu.hhu.entity.User;
import com.hhu.result.Result;
import com.hhu.service.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.hhu.constant.CheckCodeConstant.*;

@RestController
@Slf4j
public class UserController {

    @Autowired private IUserService userService;

    @PostMapping("/login")
    public Result login(HttpSession httpSession,@RequestBody UserDTO userDTO){
        try {
            log.info("用户登录:{}",userDTO);

            User loginUser = userService.userLogin(userDTO);
            return Result.success(loginUser);
        }finally {
            httpSession.removeAttribute(CHECK_CODE_KEY);
        }
    }
}
