package com.site.blog.my.core.config.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {
    private static Logger log = LoggerFactory.getLogger(RedisCacheService.class);
    @Autowired
    private RedisTemplate<String,String> stringRedisTemplate;

//    @Value("${service.engine.queue}")
//    private String serviceEngineQueue;
//
//    @Value("{service.retry.queue}")
//    private String serviceRetryQueue;

    /**
     * 获取缓存值
     */
    private String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }


    /**
     * 删除缓存值
     */
    private void deleteValue(String key){
        stringRedisTemplate.delete(key);
    }



    /**
     * 从cache中获取短信验证码
     */
    public String getCodeFromCache(String key){
        return this.get(key);
    }


    /**
     * 从cache中清除短信验证码
     */
    public void removeCodeFromCache(String key){
         this.deleteValue(key);
    }

    /**
     * 放入缓存数据并设置超时时间
     */
    public void putCodeToCacheAndTime(String key,String code,long time){
        putCodeToCache(key,code,time);
    }

    public void putCodeToCache(String key,String code,long time){
        stringRedisTemplate.opsForValue().set(key,code);
        System.out.println("成功写入缓存，缓存key: "+key);
        if (time != 0){
            stringRedisTemplate.expire(key,time, TimeUnit.MILLISECONDS);
        }
    }

}
