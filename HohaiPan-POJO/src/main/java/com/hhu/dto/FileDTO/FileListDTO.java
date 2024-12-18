package com.hhu.dto.FileDTO;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

@Data
public class FileListDTO implements Serializable{
    Integer pageNum;
    Integer pageSize;
    String categoryCode;
    String fileNameFuzzy;
    String filePid;
}
