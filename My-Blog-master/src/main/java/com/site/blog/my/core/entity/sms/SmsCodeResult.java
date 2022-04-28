package com.site.blog.my.core.entity.sms;

import lombok.Data;

@Data
public class SmsCodeResult {
    /**
     * 短信验证码结果 S成功 F失败
     */
    private String result;
    private String errorCode;
    private String errorMsg;

    public boolean isSuccess(){
        return "S".equals(result);
    }
}
