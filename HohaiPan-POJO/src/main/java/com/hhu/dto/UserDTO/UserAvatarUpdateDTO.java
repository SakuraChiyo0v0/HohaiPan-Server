package com.hhu.dto.UserDTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAvatarUpdateDTO implements Serializable {
    private Long userId;
    private String avatarURL;
}
