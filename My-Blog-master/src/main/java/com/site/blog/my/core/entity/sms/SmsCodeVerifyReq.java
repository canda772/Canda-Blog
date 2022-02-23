package com.site.blog.my.core.entity.sms;

import lombok.Data;

@Data
public class SmsCodeVerifyReq {
    private String userId;
    private String smsCode;
    private String tranType;
}
