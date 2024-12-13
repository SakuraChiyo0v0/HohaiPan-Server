package com.hhu.constant;

public class ValidationConstant {
    public static final String NICKNAME_MSG = "用户名格式错误!";
    public static final String EMAIL_MSG = "邮箱格式错误!";
    public static final String PASSWORD_MSG = "密码格式错误!";

    public static final String EMAIL_REGEXP = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    public static final String NICKNAME_REGEXP = "^[\\u4e00-\\u9fa5A-Za-z0-9._-]{2,16}$";
    public static final String PASSWORD_REGEXP = "^\\w{6,18}$";
}
