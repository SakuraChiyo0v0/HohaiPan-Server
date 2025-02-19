package com.hhu.controller;

import com.hhu.result.Result;
import com.hhu.service.IFileService;
import com.hhu.service.IShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/share")
public class ShareController {

    @Autowired
    private IShareService shareService;

    @GetMapping()
    public Result getShareFile(String shareCode){
        log.info("获取分享文件:{}",shareCode);
        return shareService.getShareFile(shareCode);
    }

    @PostMapping("/generate")
    public Result generateShareCode(String fileId){
        log.info("生成分享文件:{}",fileId);
        return shareService.generateShareCode(fileId);
    }
}
