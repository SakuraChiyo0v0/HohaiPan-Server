package com.hhu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class FileShareVO  implements Serializable {

    // tb_file 表的字段
    private String fileId;              // 文件ID
    private String fileMd5;             // 文件MD5值
    private Long fileSize;              // 文件大小
    private String fileName;            // 文件名
    private String fileCover;           // 文件封面

    private CategoryCodeType fileCategory; // 文件分类
    private Byte fileType;              // 文件类型

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;   // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;   // 更新时间
    private String shareCode;           // 分享码

    private Long userId;           // 关联用户ID
    private String nickname;            // 用户名（冗余字段）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime shareTime;    // 分享时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate expireTime;       // 截至日期
    private Integer useCount;           // 使用次数
}
