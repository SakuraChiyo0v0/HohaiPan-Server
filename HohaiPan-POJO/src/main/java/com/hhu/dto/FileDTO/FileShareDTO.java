package com.hhu.dto.FileDTO;

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
public class FileShareDTO implements Serializable {

    // tb_file 表的字段
    private String fileId;              // 文件ID
    private Long userId;                // 用户ID
    private String fileMd5;             // 文件MD5值
    private String filePid;             // 文件父ID
    private Long fileSize;              // 文件大小
    private String fileName;            // 文件名
    private String fileCover;           // 文件封面
    private String filePath;            // 文件路径
    private CategoryCodeType fileCategory; // 文件分类
    private Byte fileType;              // 文件类型
    private Byte status;                // 文件转码状态
    private LocalDateTime recoveryTime; // 回收站时间
    private Integer version;            // 乐观锁
    private Byte deleted;               // 文件删除状态
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间

    // tb_share 表的字段
    private Integer shareId;            // 分享码ID
    private String shareCode;           // 分享码
    private String nickname;            // 用户名（冗余字段）
    private LocalDateTime shareTime;    // 分享时间
    private LocalDate expireTime;       // 截至日期
    private Integer useCount;           // 使用次数
}
