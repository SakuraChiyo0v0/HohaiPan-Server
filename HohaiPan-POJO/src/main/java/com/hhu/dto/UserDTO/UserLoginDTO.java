package com.hhu.dto.UserDTO;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录数据传输对象，实现Serializable接口以支持序列化。
 */
@Data
public class UserLoginDTO implements Serializable {

    /** 用户邮箱 */
    private String email;

    /** 用户密码 */
    private String password;

    /** 验证码 */
    private String checkCode;
}
