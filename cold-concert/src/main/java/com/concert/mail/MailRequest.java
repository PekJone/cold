package com.concert.mail;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamSource;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-21  16:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailRequest {
    @NotBlank(message = "邮件收件人不能为空")
    private String to ;

    private String[] cc;
    private String[] bcc;
    @NotBlank(message = "邮件主题不能为空 ")
    private String subject;

    private String content;

    //模板相关
    private String templateName;
    private Map<String,Object> templateModel;

    //附件
    private List<Attachment> attachments;

    //内嵌资源
    private List<InlineResource> inlineResources;

    //业务标识
    private String businessType;
    private String traceId;
    @Data
    @Builder
    public static class Attachment {
        private String filename;
        private InputStreamSource data;
        private String contentType;
    }

    @Data
    @Builder
    public static class InlineResource {
        private String contentId;
        private InputStreamSource data;
        private String contentType;
    }


}
