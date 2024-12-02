package com.hhu.hhu.enums;

import lombok.Getter;

/**
 * 验证码类型枚举
 */
@Getter
public enum CheckCodeType {

    Login(1,"checkCode:login"),
    Register(2,"checkCode:register"),
    ResetPwd(3,"checkCode:resetPwd"),
    Email(4,"checkCode:email");

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
