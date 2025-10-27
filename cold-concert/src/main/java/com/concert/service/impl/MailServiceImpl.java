package com.concert.service.impl;

import com.concert.config.MailProperties;
import com.concert.exception.MailException;
import com.concert.mail.MailRequest;
import com.concert.mail.MailResponse;
import com.concert.service.MailSendCallBack;
import com.concert.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-22  17:13
 */
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final MailProperties mailProperties;
    private final ThreadPoolTaskExecutor  mailExecutor;
    private final MailSendCallBack mailSendCallBack ;

    public MailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine, MailProperties mailProperties, ThreadPoolExecutor mailExecutor, MailSendCallBack mailSendCallBack) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.mailProperties = mailProperties;
        this.mailSendCallBack = mailSendCallBack;
        this.mailExecutor = createMailExecutor();
    }


    private ThreadPoolTaskExecutor createMailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("mail-async");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public MailResponse sendSimpleMail(String to, String subject, String content) {

        return null;
    }

    @Override
    public MailResponse sendHtmlMail(String to, String subject, String content) {
        return null;
    }

    @Override
    public MailResponse sendTemplateMail(MailRequest request) {
        long startTime = System.currentTimeMillis();
        String traceId = StringUtils.isNotBlank(request.getTraceId())? request.getTraceId() : generateTraceId();
        try {
            validateMailRequest(request);
            MimeMessage mimeMessage = buildMimeMessage(request,traceId);
            mailSender.send(mimeMessage);
            MailResponse response = MailResponse.Success(getMessageId(mimeMessage),System.currentTimeMillis()-startTime);
            mailSendCallBack.onSuccess(request,response);
            log.info("邮件发送成功 - traceId: {}, to: {}, subject: {}",
                    traceId, request.getTo(), request.getSubject());
            return response;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMessageId(MimeMessage mimeMessage) throws MessagingException {
        String[] messageId = mimeMessage.getHeader("Message-ID");
        return messageId!=null && messageId.length>0?messageId[0]:"unknown";
    }

    private MimeMessage buildMimeMessage(MailRequest request, String traceId) throws MessagingException {
         MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                hasAttachmentOrInlineResources(request),
                mailProperties.getDefaultEncoding()
                );
        //设置基本信息
        helper.setTo(request.getTo().split(","));
        if(ArrayUtils.isNotEmpty(request.getCc())){
            helper.setCc(request.getCc());
        }
        if(ArrayUtils.isNotEmpty(request.getBcc())){
            helper.setBcc(request.getBcc());
        }
        helper.setSubject(request.getSubject());
        helper.setFrom(mailProperties.getUsername());
        //设置邮件内容
        String content = buildMailContent(request);
        helper.setText(content,true);
        //处理附件
        handleAttachments(helper,request);

        //处理内嵌资源
        handleInlineResources(helper,request);

        mimeMessage.setHeader("x-Trace-Id",traceId);
        mimeMessage.setHeader("X-Business-Type",request.getBusinessType());
        mimeMessage.setHeader("X-Send-Time", LocalDateTime.now().toString());
        return mimeMessage;
    }

    private void handleInlineResources(MimeMessageHelper helper, MailRequest request) throws MessagingException {
        if(request.getInlineResources()!=null){
            for (MailRequest.InlineResource resource : request.getInlineResources()){
                helper.addInline(resource.getContentId(),
                        resource.getData(),
                        resource.getContentType());
            }
        }
    }

    private void handleAttachments(MimeMessageHelper helper, MailRequest request) throws MessagingException {
         if(request.getAttachments()!=null){
             for (MailRequest.Attachment attachment: request.getAttachments()){
                 helper.addAttachment(attachment.getFilename(),
                         attachment.getData(),
                         attachment.getContentType());
             }
         }

    }

    private String buildMailContent(MailRequest request) {
        if(StringUtils.isNotBlank(request.getTemplateName())){
            //使用模板引擎渲染内容
            Context context = new Context();
            if(request.getTemplateModel()!=null){
                context.setVariables(request.getTemplateModel());
            }
            return templateEngine.process(request.getTemplateName(),context);
        }else{
            return request.getContent();
        }
    }

    private boolean hasAttachmentOrInlineResources(MailRequest request) {
       return (request.getAttachments()!=null && !request.getAttachments().isEmpty())||
               (request.getInlineResources()!=null && !request.getInlineResources().isEmpty());
    }


    private void validateMailRequest(MailRequest request) {
        if(StringUtils.isBlank(request.getTemplateName())&& StringUtils.isBlank(request.getContent())){
            throw new MailException("邮件内容或模板名称不能同时为空");
        }
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString().replace("-","").substring(0,16);
    }

    @Override
    public MailResponse sendMailWithAttachment(MailRequest request) {
        return null;
    }

    @Override
    public MailResponse sendMailAsync(MailRequest request) {
        return null;
    }

    @Override
    public MailResponse sendBatchMail(List<MailRequest> requests) {
        return null;
    }
}
