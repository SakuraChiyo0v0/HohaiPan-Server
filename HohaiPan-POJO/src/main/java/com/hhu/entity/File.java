package com.hhu.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.hhu.enums.CategoryCodeType;
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
@TableName("tb_file")
public class File implements Serializable {

    @TableId
    private String fileId;      // 文件ID
    private Long userId;        // 用户ID
    private String fileMd5;     // 文件MD5值
    private String filePid;     // 文件父ID
    private Long fileSize;      // 文件大小
    private String fileName;    // 文件名
    private String fileCover;   // 文件封面
    private String filePath;    // 文件路径
    private CategoryCodeType fileCategory;  // 文件分类
    private Byte fileType;      // 文件类型
    private Byte status;        // 文件转码状态
    private LocalDateTime recoveryTime;  // 回收站时间
    @Version
    private Integer version;    // 乐观锁
    @TableLogic
    private Byte deleted;       // 文件删除状态
    private LocalDateTime createTime;    // 创建时间
    private LocalDateTime updateTime;    // 更新时间

}
