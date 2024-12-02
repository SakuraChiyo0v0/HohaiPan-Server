package com.hhu.controller;
import com.hhu.hhu.dto.UserDTO;
import com.hhu.result.Result;
import com.hhu.service.IUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import static com.hhu.constant.CheckCodeConstant.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;
    @PostMapping("/login")
    public Result login(HttpSession httpSession, @RequestBody UserDTO userDTO) {
        try {
            log.info("用户登录:{}", userDTO);
            log.error("woc");
            return userService.userLogin(userDTO);
        } finally {
            httpSession.removeAttribute(CHECK_CODE_KEY);
        }
    }

    @PostMapping("/register")
    public Result register(HttpSession httpSession,@Valid @RequestBody UserDTO userDTO) {
        return Result.success();
    }
}
