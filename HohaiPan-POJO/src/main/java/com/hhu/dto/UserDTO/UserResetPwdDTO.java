package com.hhu.dto.UserDTO;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户重置密码数据传输对象
 * 用于在系统各组件间传递用户重置密码所需的信息
 */
@Data
public class UserResetPwdDTO implements Serializable {

    /** 用户邮箱 */
    private String email;

    /** 用户旧密码 */
    private String oldPassword;

    /** 用户新密码 */
    private String password;

    /** 验证码 */
    private String checkCode;

    /** 邮箱验证码 */
    private String emailCode;

    /** 是否登录标志 */
    private Boolean isLogin;
}
