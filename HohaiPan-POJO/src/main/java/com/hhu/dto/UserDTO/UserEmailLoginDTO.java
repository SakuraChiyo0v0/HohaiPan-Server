package com.hhu.dto.UserDTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserEmailLoginDTO implements Serializable {
    private String email;
    private String emailCode;
}
