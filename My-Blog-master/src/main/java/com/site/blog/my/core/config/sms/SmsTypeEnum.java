package com.site.blog.my.core.config.sms;

import org.apache.commons.lang3.StringUtils;

public enum  SmsTypeEnum {
    login("login")
    ;

    private String name;

    public String getName(){
        return name;
    }

    SmsTypeEnum(String name){
        this.name = name;
    }

    /**
     * 根据name获取短信类型
     */
    public static SmsTypeEnum valueOfName(String name){
        for (SmsTypeEnum typeEnum:SmsTypeEnum.values()){
            if (StringUtils.equalsIgnoreCase(typeEnum.getName(),name)){
                return typeEnum;
            }
        }
        return null;
    }
}
