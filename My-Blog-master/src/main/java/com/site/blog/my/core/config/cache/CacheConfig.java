package com.site.blog.my.core.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * 本地缓存  默认一天过期
     */
    @Primary
    @Bean
    public CaffeineCacheManager caffeineCacheManager(){
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(CacheName.getCaffeineCacheNames());
        Caffeine<Object,Object> caffeine = Caffeine.newBuilder().maximumSize(100000);
        caffeine.expireAfterWrite(1, TimeUnit.DAYS);
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
