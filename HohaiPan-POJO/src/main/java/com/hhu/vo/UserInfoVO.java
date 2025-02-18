package com.hhu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO implements Serializable {
    private Long userId;

    private String nickname;

    private String email;

    private String avatar;

    private String usedSpace;

    private String totalSpace;
}
