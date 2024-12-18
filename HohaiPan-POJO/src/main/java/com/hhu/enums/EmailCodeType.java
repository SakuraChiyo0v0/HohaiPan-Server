package com.hhu.enums;


import lombok.Getter;

import static com.hhu.constant.MessageConstant.UNKNOWN_ENUM_ERROR;

@Getter
public enum EmailCodeType {
    Register(1,"emailCode:register:","HohaiPan注册验证码"),
    ResetPwd(2,"emailCode:resetPwd:","HohaiPan找回密码验证码"),
    EmailLogin(3,"emailCode:emailLogin:","HohaiPan邮箱登录验证码");

    private final int code;
    private final String prefix;
    private final String subject;

    EmailCodeType(int code, String prefix,String subject) {
        this.code = code;
        this.prefix = prefix;
        this.subject = subject;
    }

    public static String getPrefix(int code) {
        for (EmailCodeType value : EmailCodeType.values()) {
            if (value.getCode() == code) {
                return value.getPrefix();
            }
        }
        throw new RuntimeException(UNKNOWN_ENUM_ERROR);
    }

    public static String getSubject(int code) {
        for (EmailCodeType value : EmailCodeType.values()) {
            if (value.getCode() == code) {
                return value.getSubject();
            }
        }
        throw new RuntimeException(UNKNOWN_ENUM_ERROR);
    }

}
