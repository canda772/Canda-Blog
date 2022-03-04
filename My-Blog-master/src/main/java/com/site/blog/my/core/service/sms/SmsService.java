package com.site.blog.my.core.service.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
import com.alibaba.fastjson.JSONObject;
import com.site.blog.my.core.entity.sms.SmsReturnBean;
import com.site.blog.my.core.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 短信服务
 * 短信发送 模板  查询短信发送结果等
 */
@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    public SmsReturnBean sendSms(String mobileNo, String templateId, JSONObject jsonObject, String channelNo) throws Exception {
        SmsReturnBean smsReturnBean = new SmsReturnBean();
        //templateId:SMS_190268796
        List<String> args = new ArrayList<>();
        Client client = SmsService.createClient("key", "se");
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(mobileNo)
                .setSignName("天天爱代码")
                .setTemplateCode(templateId)
                .setTemplateParam("{\"code\":\""+jsonObject.getString("smsCode")+"\"}");
        // 复制代码运行请自行打印 API 的返回值
        try {
            client.sendSms(sendSmsRequest);
        }catch (Exception e){
            logger.info("短信发送失败，手机号:[{}],失败原因：[{}]",e,mobileNo);
            smsReturnBean.setErrorCode("999999");
            smsReturnBean.setErrorMsg("短信发送失败");
            return smsReturnBean;
        }
        smsReturnBean.setErrorCode("000000");
        smsReturnBean.setErrorMsg("短信发送成功");
        return smsReturnBean;
    }

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
}


