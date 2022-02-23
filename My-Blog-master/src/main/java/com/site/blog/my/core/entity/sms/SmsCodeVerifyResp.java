package com.site.blog.my.core.entity.sms;

import lombok.Data;

@Data
public class SmsCodeVerifyResp {
    private String userId;
    private String result;
}
