package com.concert.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-21  10:01
 */
@Service
public class SpringBootMailService {

    private JavaMailSender javaMailSender ;

    public void sendMailUtil(String to ,String subject,String htmlContent) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        helper.setFrom("742662307@qq.com");// 发件人
        helper.setTo(to); // 收件人
        helper.setSubject(subject); // 主题
        helper.setText(htmlContent, true); // 内容，true表示是HTML
        javaMailSender.send(message);
        System.out.println("邮件发送成功！");
    }
}
