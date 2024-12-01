package com.hhu.hhu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private Long userId; // 用户id

    private String userName; // 姓名

    private String nickname; // 用户名

    private String password; // 密码

    private String phoneNumber; // 手机号

    private String email; // 邮箱

    private Character sex; // 性别

    private String avatar; // 头像地址

    private Character status; // 账号状态

    private Character delFlag; // 删除标志

    private String userType; // 用户类型

    private String loginIp; // 最后登录IP

    private LocalDateTime loginDate; // 最后登录时间

    private LocalDateTime createTime; // 创建时间

    private Long createBy; // 创建者

    private LocalDateTime updateTime; // 更新时间

    private Long updateBy; // 更新者

    private String remark; // 备注

}
