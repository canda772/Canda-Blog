package com.site.blog.my.core.entity.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class SmsReturnBean {
    public static final String SUCCESS_CODE="000000";
    public static final String SUCCESS_DESC="受理成功";
    public Object data;
    private String errorCode;
    private String errorMsg;

    public SmsReturnBean(){
        this.errorCode="000000";
        this.errorMsg="受理成功";
    }

    public SmsReturnBean(String errorCode,String errorDesc){
        this.errorCode = errorCode;
        this.errorMsg = errorDesc;
    }
}
