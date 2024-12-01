package com.hhu.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 验证码配置
 */
@Configuration
@Data
@Component
public class CaptchaProperties {

    //验证码类型
    @Value("${HohaiPan.CaptchaConfig.CaptchaType:LineCaptcha}")
    private String CaptchaType;

    //验证码宽度
    @Value("${HohaiPan.CaptchaConfig.CaptchaWidth:130}")
    private Integer CaptchaWidth;

    //验证码高度
    @Value("${HohaiPan.CaptchaConfig.CaptchaHeight:38}")
    private Integer CaptchaHeight;

    //验证码数量
    @Value("${HohaiPan.CaptchaConfig.CodeCount:4}")
    private Integer CaptchaCount;

    //干扰线段数量
    @Value("${HohaiPan.CaptchaConfig.LineCount:10}")
    private Integer LineCount;

    //干扰圆圈数量
    @Value("${HohaiPan.CaptchaConfig.CircleCount:10}")
    private Integer CircleCount;

    //干扰扭曲程度
    @Value("${HohaiPan.CaptchaConfig.Thickness:5}")
    private Integer Thickness;
}
