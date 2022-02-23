package com.site.blog.my.core.entity.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

/**
 * 短信验证码值-redis缓存对象
 */
public class SmsCodeCache {
    String code;
    long sendTime;

    @Override
    public String toString() {
        return "SmsCodeCache{" +
                "code='" + code + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}
