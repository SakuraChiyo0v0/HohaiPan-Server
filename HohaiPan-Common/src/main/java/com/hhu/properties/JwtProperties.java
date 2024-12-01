package com.hhu.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@Data
public class JwtProperties {

    @Value("${HohaiPan.JwtConfig.expireTime:7200000}")
    private Long expireTime;

    @Value("${HohaiPan.JwtConfig.secretKey:hhu}")
    private String secretKey;

    @Value("${HohaiPan.JwtConfig.tokenName:token}")
    private String tokenName;
}
