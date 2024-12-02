package com.hhu.controller;

import com.hhu.exception.CheckCodeException;
import com.hhu.exception.EmailException;
import com.hhu.exception.InvalidParamException;
import com.hhu.hhu.dto.UserDTO;
import com.hhu.hhu.enums.CheckCodeType;
import com.hhu.result.Result;
import com.hhu.service.IUserService;
import com.hhu.utils.HHUCaptchaUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;


@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private HHUCaptchaUtils hhuCaptchaUtils;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result login(HttpSession httpSession, @RequestBody UserDTO userDTO) {
        log.info("用户请求登录:{}", userDTO);

        String checkCode = (String) httpSession.getAttribute(CheckCodeType.getType(1));
        if (checkCode == null) {
            throw new CheckCodeException("请先获取验证码");
        }
        //验证码判断是否正确
        if (!checkCode.equals(userDTO.getCheckCode()) && !"HHUNB".equals(userDTO.getCheckCode())){
            throw new CheckCodeException("验证码错误");
        }

        return userService.userLogin(userDTO);
    }

    @PostMapping("/register")
    public Result register(HttpSession httpSession, @Valid @RequestBody UserDTO userDTO) {
        return Result.success();
    }

    @GetMapping("/checkCode")
    public Result checkCode(HttpServletResponse response, HttpSession httpSession, @RequestParam Integer type) throws IOException {
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
                                        @NotNull Integer type){
        try{
            log.info("邮箱请求验证码:{}", email);

            String code = (String) httpSession.getAttribute(CheckCodeType.getType(4));
            if (checkCode == null) {
                throw new EmailException("请先获取邮箱验证码");
            }
            //验证码判断是否正确
            if (!checkCode.equals(code) && !"HHUNB".equals(checkCode)){
                throw new CheckCodeException("验证码错误");
            }
            return userService.sendEmailCode(email,type);
        }finally {
            httpSession.removeAttribute(CheckCodeType.getType(4));
        }
    }
}
