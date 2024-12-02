package com.hhu.hhu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    // 邮箱
    private String email;

    //昵称
    private String nickname;

    //密码
    private String password;

    //图片验证码
    private String checkCode;

    //邮箱验证码
    private String emailCode;
}
