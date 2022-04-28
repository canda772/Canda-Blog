package com.site.blog.my.core.entity.sms;

import lombok.Data;

@Data
public class SmsCodeSendReq {
    private String userId;
    private String mobileNo;
    private String tranType;
    private String templateId;
}
