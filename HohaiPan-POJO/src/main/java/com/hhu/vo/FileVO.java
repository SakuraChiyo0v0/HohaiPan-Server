package com.hhu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class FileVO implements Serializable {
    private String fileId;
    private String fileName;
    private Long fileSize;
    private CategoryCodeType fileCategory;
    private Byte fileType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
