package com.concert.service.impl;

import com.concert.mail.MailRequest;
import com.concert.mail.MailResponse;
import com.concert.service.MailSendCallBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-24  16:38
 */
@Component
@Slf4j
public class DefaultMailSendCallback implements MailSendCallBack {
    @Override
    public void onSuccess(MailRequest mailRequest, MailResponse mailResponse) {
        log.info("邮件发送成功-messageId():{},cost:{}ms",mailResponse.getMessageId(),mailResponse.getCostTime());
    }

    @Override
    public void onFailure(MailRequest mailRequest, MailResponse mailResponse) {
        log.error("邮件发送失败");
    }
}
