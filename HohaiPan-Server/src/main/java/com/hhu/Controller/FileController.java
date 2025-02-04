package com.hhu.controller;

import com.hhu.dto.FileDTO.FileListDTO;
import com.hhu.result.PageBean;
import com.hhu.result.Result;
import com.hhu.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IFileService fileService;
    @GetMapping("/list")
    public Result list(FileListDTO fileListDTO) {
        log.info("fileListDTO:{}",fileListDTO);
        PageBean fileList = fileService.getFileList(fileListDTO);
        log.info("fileList:{}",fileList);
        return Result.success(fileList);
    }
}
