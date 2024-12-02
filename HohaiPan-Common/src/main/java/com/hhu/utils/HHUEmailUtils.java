package com.hhu.utils;

import com.hhu.constant.MessageConstant;
import com.hhu.exception.EmailException;
import com.hhu.properties.EmailCodeProperties;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

@Component
@Slf4j
public class HHUEmailUtils {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private EmailCodeProperties emailCodeProperties;

    public void sendEmail(String toEmail,Integer emailCode,String subject) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setText(buildContent(emailCode + ""), true);
            helper.setTo(toEmail);
            helper.setFrom("HohaiPan" + '<' + "1203521235@QQ.com" + '>');
            javaMailSender.send(message);
            log.info("邮件已成功发送到:{},邮件验证码为:{}",toEmail,emailCode);
        }catch(Exception e){
            log.error("发送邮件失败{}", e);
            throw new EmailException(MessageConstant.EMAIL_SEND_FAILED);
        }

    }

    /**
     * 读取邮件模板
     * 替换模板中的信息
     *
     * @param title 内容
     * @return
     */
    public String buildContent(String title) {
        //加载邮件html模板
        //在模板中定义了{0},把他替换为我们自定义的值：参数title
        Resource resource = new ClassPathResource("mailTemplate/mailTemplate.ftl");
        InputStream inputStream = null;
        BufferedReader fileReader = null;
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            inputStream = resource.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            log.info("发送邮件读取模板失败{}", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer.toString(), title);
    }

}

