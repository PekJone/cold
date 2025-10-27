package com.concert.service;

import com.concert.mail.MailRequest;
import com.concert.mail.MailResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-22  17:21
 */

public interface MailSendCallBack {

    void onSuccess(MailRequest mailRequest, MailResponse mailResponse);

    void onFailure(MailRequest mailRequest, MailResponse mailResponse);

    default void onBeforeSend(MailRequest request){

    }


}
