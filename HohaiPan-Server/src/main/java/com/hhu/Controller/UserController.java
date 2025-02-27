package com.hhu.controller;

import cn.hutool.core.util.StrUtil;
import com.hhu.dto.UserDTO.*;
import com.hhu.exception.CheckCodeException;
import com.hhu.enums.CheckCodeType;
import com.hhu.properties.SystemConfigProperties;
import com.hhu.result.Result;
import com.hhu.service.IUserService;
import com.hhu.utils.HHUCaptchaUtils;
import com.hhu.utils.HHUThreadLocalUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.Map;

import static com.hhu.constant.MessageConstant.*;


@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private HHUCaptchaUtils hhuCaptchaUtils;
    @Autowired
    private SystemConfigProperties systemConfigProperties;

    @PostMapping("/login")
    public Result login(HttpSession httpSession,@Valid @RequestBody UserLoginDTO user) {
        log.info("用户请求登录:{}", user);
        checkCode(httpSession, user.getCheckCode(), CheckCodeType.Login.getCode());
        return userService.userLogin(user);
    }

    @PostMapping("/emailLogin")
    public Result emailLogin(@Valid @RequestBody UserEmailLoginDTO user) {
        log.info("用户请求邮箱登录:{}", user);
        return userService.emailLogin(user);
    }

    @PostMapping("/register")
    public Result register(@Valid @RequestBody UserRegisterDTO userDTO) {
        log.info("用户请求注册:{}", userDTO);
        return userService.register(userDTO);
    }

    @PutMapping("/resetPwd")
    public Result resetPwd(@Valid @RequestBody UserResetPwdDTO userDTO) {
        log.info("用户请求重置密码:{}", userDTO);
        return userService.resetPwd(userDTO);
    }
    @PutMapping("/logout")
    public Result logout(){
        //TODO 登出功能
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
                                @NotNull Integer emailCodeType) {
        try {
            log.info("邮箱请求验证码:{}", email);
            checkCode(httpSession, checkCode, CheckCodeType.SendEmail.getCode());
            return userService.sendEmailCode(email, emailCodeType);
        } finally {
            httpSession.removeAttribute(CheckCodeType.getType(emailCodeType));
        }
    }

    @GetMapping("/userInfo")
    public Result getUserInfo() {
        Long userId = HHUThreadLocalUtil.getUserId();
        log.info("用户:{}获取用户信息   ", userId);
        return Result.success(userService.getUserInfo(userId));
    }

    @PutMapping("/updateAvatar")
    public Result updateUserAvatar(@RequestBody UserAvatarUpdateDTO userAvatarUpdateDTO){
        return userService.updateUserAvatar(userAvatarUpdateDTO);
    }

    private void checkCode(HttpSession httpSession, String checkCode, Integer typeCode) {
        String settingCode = (String) httpSession.getAttribute(CheckCodeType.getType(typeCode));
        if (systemConfigProperties.isCheckCodeOpen()) {
            if (settingCode == null) {
                throw new CheckCodeException(CHECK_CODE_NOT_EXIST);
            }
            //验证码判断是否正确
            if (!settingCode.equals(checkCode)
                    && !systemConfigProperties.getUniversalCode().equals(checkCode)) {
                throw new CheckCodeException(CHECK_CODE_ERROR);
            }
        }
    }


}
