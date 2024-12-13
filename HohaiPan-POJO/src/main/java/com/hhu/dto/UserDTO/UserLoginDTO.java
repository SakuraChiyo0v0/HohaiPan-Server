package com.hhu.dto.UserDTO;

import com.hhu.annotation.EmailCheck;
import com.hhu.annotation.PasswordCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录数据传输对象，实现Serializable接口以支持序列化。
 */
@Data
public class UserLoginDTO implements Serializable {

    /** 用户邮箱 */
    @EmailCheck
    private String email;

    /** 用户密码 */
    @PasswordCheck
    private String password;

    /** 验证码 */
    @NotBlank(message = "验证码不能为空")
    private String checkCode;
}
