package com.hhu.properties;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@Data
public class SystemConfigProperties {
    @Value("${HohaiPan.SystemConfig.checkCodeOpen:true}")
    private boolean checkCodeOpen;  // 是否开启验证码 测试使用 上线切记要关闭


    @Value("${HohaiPan.SystemConfig.universalCode}")
    private String universalCode;  // 通用验证码
}
