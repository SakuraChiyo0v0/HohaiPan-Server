package com.hhu.dto.UserDTO;

import com.hhu.annotation.EmailCheck;
import com.hhu.annotation.NicknameCheck;
import com.hhu.annotation.PasswordCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterDTO implements Serializable {

    @EmailCheck
    private String email;

    @NicknameCheck
    private String nickname;

    @PasswordCheck
    private String password;

    @NotBlank(message = "邮箱验证码不能为空")
    private String emailCode;

}

