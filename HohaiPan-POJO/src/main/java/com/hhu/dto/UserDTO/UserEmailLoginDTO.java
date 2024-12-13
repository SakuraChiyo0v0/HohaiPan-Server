package com.hhu.dto.UserDTO;

import com.hhu.annotation.EmailCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserEmailLoginDTO implements Serializable {

    @EmailCheck
    private String email;

    @NotBlank(message = "邮箱验证码不能为空")
    private String emailCode;
}
