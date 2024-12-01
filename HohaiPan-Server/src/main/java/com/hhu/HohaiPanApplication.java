package com.hhu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class HohaiPanApplication {
    public static void main(String[] args) {
        SpringApplication.run(HohaiPanApplication.class, args);
        log.info("河海盘启动成功 d=====(￣▽￣*)b");
    }
}
