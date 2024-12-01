package com.hhu.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 邮箱验证码配置
 */
@Configuration
@Data
@Component
public class EmailCodeProperties {

    @Value("${HohaiPan.EmailCodeConfig.expireTime:60000}")
    private Integer expireTime;// 过期时间

    @Value("${HohaiPan.EmailCodeConfig.sendEmail}")
    private String sendEmail;

    @Value("${HohaiPan.EmailCodeConfig.coolDownTime}")
    private Integer coolDownTime;

    @Value("${HohaiPan.EmailCodeConfig.coolDownOpen}")
    private Boolean coolDownOpen;
}
