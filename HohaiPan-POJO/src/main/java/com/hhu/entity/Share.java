package com.hhu.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.hhu.enums.CategoryCodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_share")
public class Share implements Serializable {

    @TableId
    private Integer shareId;      // 分享码id
    private String shareCode;     // 分享码
    private String fileId;        // 关联文件id
    private Long userId;          // 关联用户id
    private LocalDateTime shareTime;       // 分享时间
    private LocalDate expireTime;      // 截至日期
    private Integer useCount;     // 使用次数

}
