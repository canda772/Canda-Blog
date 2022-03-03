package com.site.blog.my.core.service.sms;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jcraft.jsch.Logger;
import com.site.blog.my.core.config.Constants;
import com.site.blog.my.core.config.cache.RedisCacheService;
import com.site.blog.my.core.config.sms.SmsTypeEnum;
import com.site.blog.my.core.entity.sms.SmsCodeCache;
import com.site.blog.my.core.entity.sms.SmsCodeResult;
import com.site.blog.my.core.entity.sms.SmsReturnBean;
import com.site.blog.my.core.entity.system.SystemParam;
import com.site.blog.my.core.service.system.SystemParamService;
import com.site.blog.my.core.util.CommonUtils;
import com.site.blog.my.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 短信验证码服务
 */
@Service
@Slf4j
public class smsCodeService {

    @Autowired
    private SmsService smsService;
    @Autowired
    private RedisCacheService cacheService;
    @Autowired
    private SystemParamService systemParamService;

    /**登录验证码过期时间*/
    private static final String SMSCODE_EXPIRE_TIME="smscode.expire.time.login.";
    /**固定验证码*/
    private static final String SMS_CODE_TEST_VALUE="sms.code.test.value.";
    /**短信模板id*/
    private static final String SMSCODE_TYPE="sms.code.type.";

    /**
     * 发送短信验证码
     */
    public String senSmsCode(String mobile, SmsTypeEnum smsTypeEnum,String channel) throws Exception {
        return sendSmsCode(mobile,smsTypeEnum,null,null,channel);
    }

    /**
     * 发送短信验证码
     * 如果短信发送申请流水为空，则以手机号作为 redis缓存key
     */
    public String sendSmsCode(String mobile, SmsTypeEnum smsTypeEnum, String smsRequestNo, Map<String,Object> dataMap, String channel) throws Exception {
        //生成随机短信验证码位数
        String randomCode = CommonUtils.getRandomString(6,false);
        String value = systemParamService.getValue(SMS_CODE_TEST_VALUE+channel);
        if (StringUtils.isNotBlank(value)){
            randomCode = value;
        }
        SmsCodeCache smsCode = new SmsCodeCache(randomCode,System.currentTimeMillis());

        //设置短信验证码过期时间
        String expireTime = systemParamService.getValue(SMSCODE_EXPIRE_TIME+channel);
        long time = 80L;
        if (StringUtils.isNotBlank(expireTime)){
            time = Long.valueOf(expireTime);
        }
        String cacheKey = mobile;
        if (StringUtils.isNotBlank(smsRequestNo)){
            cacheKey=smsRequestNo;
        }
        String smsCacheKey = getSmsCacheKey(cacheKey,channel);


        /**
         * 根据类型和渠道号查询templateId
         */
        String templateId =getSmsTemplateId(smsTypeEnum,channel);

        //调用短信发送服务发送短信
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("smsCode",randomCode);

        if (MapUtils.isNotEmpty(dataMap)){
            jsonObject.putAll(dataMap);
        }

        SmsReturnBean smsReturnBean = smsService.sendSms(mobile,templateId,jsonObject,channel);
        if (!smsReturnBean.SUCCESS_CODE.equals(smsReturnBean.getErrorCode())){
            //抛自定义错
        }
        cacheService.removeCodeFromCache(smsCacheKey);

        //验证码放入redis缓存
        cacheService.putCodeToCache(smsCacheKey,smsCode.toString(),time);

        log.info("验证码发送成功，手机号：[{}],短信验证码:[{}]",mobile,randomCode);
        return randomCode;
    }


    /**
     * 短信验证码核验
     * @param mobile
     * @param smsRequestNo
     * @param smsCode
     * @param channel
     * @return
     */
    public SmsCodeResult verifySmsCode(String mobile,String smsRequestNo,String smsCode,String channel){
        String cacheKey = mobile;
        if (StringUtils.isNotBlank(smsRequestNo)){
            cacheKey = smsRequestNo;
        }
        String smsCacheKey = getSmsCacheKey(cacheKey,channel);
        String cacheSmsCode = cacheService.getCodeFromCache(smsCacheKey);
        SmsCodeResult smsCodeResult = new SmsCodeResult();
        if (StringUtils.isBlank(cacheSmsCode)){
            //不存在
        }
        SmsCodeCache codeCache = ReflectUtil.json2Bean(cacheSmsCode,SmsCodeCache.class);
        if (StringUtils.equalsIgnoreCase(smsCode,codeCache.getCode().trim())){
            //成功
            cacheService.removeCodeFromCache(smsCacheKey);
        }

        return smsCodeResult;

    }


    /**
     * 生成短信验证码缓存key
     */
    private String getSmsCacheKey(String cacheCode,String channel){
        return Constants.SMS_CACHE_PREFIX+channel+"_"+cacheCode;
    }

    private String getSmsTemplateId(SmsTypeEnum smsType,String channel){
        return systemParamService.getValue(SMSCODE_TYPE+smsType.getName()+"."+channel);
    }
}
