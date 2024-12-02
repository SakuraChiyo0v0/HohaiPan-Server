package com.hhu.controller;

import com.hhu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired private IUserService userService;

    @GetMapping("/file/1")
    public String test01(){
        return "HelloWorld";
    }

    @GetMapping("/file/2")
    public String test02(){
        return userService.list().toString();
    }
}
