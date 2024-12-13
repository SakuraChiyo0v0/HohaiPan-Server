package com.hhu.dto.UserDTO;

import com.hhu.annotation.EmailCheck;
import com.hhu.annotation.PasswordCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户重置密码数据传输对象
 * 用于在系统各组件间传递用户重置密码所需的信息
 */
@Data
public class UserResetPwdDTO implements Serializable {

    /** 用户邮箱 */
    @EmailCheck
    private String email;

    /** 用户旧密码 */
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    /** 用户新密码 */
    @PasswordCheck
    private String password;

    /** 邮箱验证码 */
    @NotBlank(message = "邮箱验证码不能为空")
    private String emailCode;

    /** 是否登录标志 */
    private Boolean isLogin;
}
