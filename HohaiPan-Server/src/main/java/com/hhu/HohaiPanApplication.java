package com.hhu;

import com.hhu.utils.HHUAliOssUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.lang.ref.SoftReference;

@SpringBootApplication
@MapperScan("com.hhu.mapper")
@Slf4j
public class HohaiPanApplication {
    public static void main(String[] args){
        ConfigurableApplicationContext run = SpringApplication.run(HohaiPanApplication.class, args);
        System.out.println(run);
        log.info("河海盘启动成功 d=====(￣▽￣*)b");
    }
}
