package com.ithema.cold.common.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-21  9:28
 */
public class JavaMailExample {
    public static void main(String[] args) {
        // 邮件服务器配置
        String host = "smtp.qq.com"; // 替换为你的SMTP服务器
        String username = "742662307@qq.com"; // 替换为你的邮箱
        String password = "742662307@qq.com"; // 替换为你的授权码
        String fromAddress = "742662307@qq.com";
        String toAddress = "2216587824@qq.com";

        // 设置邮件属性
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true"); // 需要认证:cite[2]:cite[4]
        props.put("mail.smtp.starttls.enable", "true"); // 使用STARTTLS安全连接:cite[5]

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
        try {
            //创建TimeMessage对象
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toAddress));
            message.setSubject("JavaMailApi  HTML 邮件测试");

            //创建HTML格式的MimeBodyPart
            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            String htmlContent = "<h1>这是一封HTML邮件</h1><p>这是由<strong>JavaMail API</strong>发送的测试邮件。</p>";
            htmlBodyPart.setContent(htmlContent, "text/html; charset=utf-8");

            // 创建Multipart容器，并添加HTML内容部分
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlBodyPart);

            // 将Multipart设置到消息中
            message.setContent(multipart);

            // 发送邮件
            Transport.send(message);
            System.out.println("邮件发送成功！");
        }catch (MessagingException e){
            e.printStackTrace();
        }


    }
}
