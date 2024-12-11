package com.hhu.dto.UserDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterDTO implements Serializable {
    private String email;
    private String nickname;
    private String password;
    private String rePassword;
    private String checkCode;
}

