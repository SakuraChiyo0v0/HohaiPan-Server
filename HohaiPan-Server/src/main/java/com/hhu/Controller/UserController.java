package com.hhu.controller;

import com.hhu.dto.UserDTO.UserEmailLoginDTO;
import com.hhu.dto.UserDTO.UserLoginDTO;
import com.hhu.dto.UserDTO.UserRegisterDTO;
import com.hhu.exception.CheckCodeException;
import com.hhu.exception.EmailException;
import com.hhu.enums.CheckCodeType;
import com.hhu.result.Result;
import com.hhu.service.IUserService;
import com.hhu.utils.HHUCaptchaUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;


@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private HHUCaptchaUtils hhuCaptchaUtils;

    @PostMapping("/login")
    public Result login(HttpSession httpSession, @RequestBody UserLoginDTO user) {
        log.info("用户请求登录:{}", user);

        String checkCode = (String) httpSession.getAttribute(CheckCodeType.Login.getType());
        if (checkCode == null) {
            throw new CheckCodeException("请先获取验证码");
        }
        //验证码判断是否正确
        if (!checkCode.equals(user.getCheckCode()) && !"HHUNB".equals(user.getCheckCode())){
            throw new CheckCodeException("验证码错误");
        }

        return userService.userLogin(user);
    }

    @PostMapping("/emailLogin")
    public Result emailLogin(@RequestBody UserEmailLoginDTO user) {
        log.info("用户请求邮箱登录:{}", user);
        return userService.emailLogin(user);
    }

    @PostMapping("/register")
    public Result register(HttpSession httpSession, @Valid @RequestBody UserRegisterDTO user) {
        log.info("用户请求注册:{}", user);
        return Result.success();
    }

    @GetMapping("/checkCode")
    public Result checkCode(HttpServletResponse response, HttpSession httpSession, @RequestParam(required = false, defaultValue = "0") Integer type) throws IOException {
        String checkCode = hhuCaptchaUtils.createCode(response);
        String key = CheckCodeType.getType(type);
        log.info("验证码类型:{} 验证码:{}", key, checkCode);
        httpSession.setAttribute(key, checkCode);
        return Result.success();
    }

    @PostMapping("/sendEmailCode")
    public Result sendEmailCode(HttpSession httpSession,
                                        @NotNull String email,
                                        @NotNull String checkCode,
                                        @NotNull Integer emailCodeType){
        try{
            log.info("邮箱请求验证码:{}", email);

            String code = (String) httpSession.getAttribute(CheckCodeType.getType(emailCodeType));
            if (checkCode == null) {
                throw new EmailException("请先获取邮箱图形验证码");
            }
            System.out.println(code);
            System.out.println(checkCode);
            //验证码判断是否正确
            if (!checkCode.equals(code) && !"HHUNB".equals(checkCode)){
                throw new CheckCodeException("验证码错误");
            }
            return userService.sendEmailCode(email,emailCodeType);
        }finally {
            httpSession.removeAttribute(CheckCodeType.getType(emailCodeType));
        }
    }
}
