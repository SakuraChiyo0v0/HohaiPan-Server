package com.hhu.controller;

import com.hhu.service.IUserService;
import com.hhu.utils.HHUAliOssUtils;
import com.hhu.utils.HHUPartitionedBloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@Slf4j
@RestController
public class TestController {

    @Autowired private IUserService userService;
    @Autowired private HHUAliOssUtils hhuAliOssUtils;
    @Autowired private HHUPartitionedBloomFilter hhuPartitionedBloomFilter;

    @GetMapping("/file/1")
    public String test01() throws Exception {
        //hhuPartitionedBloomFilter.add("hello","test");
        log.info("test01");
        return "l";
    }

    @GetMapping("/file/2")
    public String test02(){
        //boolean hello = hhuPartitionedBloomFilter.contains("hello","test");
        //log.info("hello:{}", hello);
        //boolean hello2 = hhuPartitionedBloomFilter.contains("hello2","test");
        //log.info("hello:{}", hello2);
        return "2";
    }
}
