package com.concert.service;

import com.concert.mail.MailRequest;
import com.concert.mail.MailResponse;
import com.sun.mail.imap.protocol.MailboxInfo;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-22  15:36
 */
public interface MailService {

    /**
     * 发送简单邮件
     * @param to
     * @param subject
     * @param content
     * @return
     */
    MailResponse sendSimpleMail(String to,String subject, String content);

    /**
     * 发送html邮件
     * @param to
     * @param subject
     * @param content
     * @return
     */
    MailResponse sendHtmlMail(String to ,String subject,String content);

    /**
     * 发送给模板邮件
     * @param request
     * @return
     */
    MailResponse sendTemplateMail(MailRequest request);

    /**
     * 发送带附件的邮件+
     * @param request
     * @return
     */
    MailResponse sendMailWithAttachment(MailRequest request);

    /**
     * 异步发送邮件
     * @param request
     * @return
     */
    MailResponse sendMailAsync(MailRequest request);

    /**
     * 批量发送邮件
     */
    MailResponse sendBatchMail(List<MailRequest> requests);

}
