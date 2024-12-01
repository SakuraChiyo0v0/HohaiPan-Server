package com.hhu.hhu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResetPwdDTO implements Serializable {

    private String email;

    private String oldPassword;//主界面调用参数

    private String password;

    private String checkCode;

    private String emailCode;

    private Boolean isLogin;//判断是否是在Login界面进行重置密码
}
