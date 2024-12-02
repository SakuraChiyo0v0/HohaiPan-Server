package com.hhu.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import com.hhu.properties.CaptchaProperties;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public  class HHUCaptchaUtils {

   @Autowired
   private CaptchaProperties captchaProperties;

    //生成二维码
    public String createCode(HttpServletResponse response) throws IOException {
        //获得基础属性
        String type = captchaProperties.getCaptchaType();
        int width = captchaProperties.getCaptchaWidth();
        int height = captchaProperties.getCaptchaHeight();
        int codeCount = captchaProperties.getCaptchaCount();

        //设置响应头
        response.setContentType("image/png");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        String checkCode;

        try{
            if(type.equals("ShearCaptcha")){
                ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(width, height, codeCount, captchaProperties.getThickness());
                shearCaptcha.write(response.getOutputStream());
                checkCode = shearCaptcha.getCode();
            }else if(type.equals("CircleCaptcha")){
                CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(width, height, codeCount, captchaProperties.getCircleCount());
                circleCaptcha.write(response.getOutputStream());
                checkCode = circleCaptcha.getCode();
            }else{
                //都不匹配时 则默认为LineCaptcha
                LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, codeCount, captchaProperties.getLineCount());
                lineCaptcha.write(response.getOutputStream());
                checkCode = lineCaptcha.getCode();
            }
        }finally {
            //关闭getOutputStream流
            response.getOutputStream().close();
        }
        return checkCode;
    }
}
