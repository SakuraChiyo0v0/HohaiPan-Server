package com.hhu.enums;

import lombok.Getter;

/**
 * 验证码类型枚举
 */
@Getter
public enum CheckCodeType {

    Login(0,"checkCode:login"),
    Register(1,"checkCode:register"),
    ResetPwd(2,"checkCode:resetPwd"),
    SendEmail(3,"checkCode:sendEmail");

    private final int code;
    private final String type;

    CheckCodeType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static String getType(int code) {
        for (CheckCodeType type : CheckCodeType.values()) {
            if (type.getCode() == code) {
                return type.type;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
