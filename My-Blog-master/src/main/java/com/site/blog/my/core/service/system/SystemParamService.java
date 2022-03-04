package com.site.blog.my.core.service.system;

import com.site.blog.my.core.config.cache.CacheName;
import com.site.blog.my.core.dao.system.SystemParamMapper;
import com.site.blog.my.core.entity.system.SystemParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class SystemParamService {

    @Resource
    private SystemParamMapper systemParamMapper;

    /**
     * 通过参数名查找系统参数
     */
    @Cacheable(cacheNames = CacheName.SYSTEM_PARAM,cacheManager = "caffeineCacheManager")
    public SystemParam findByName(String name){
        return systemParamMapper.findByName(name);
    }

    /**
     * 获取参数值
     */
    @Cacheable(cacheNames = CacheName.SYSTEM_PARAM,cacheManager = "caffeineCacheManager")
    public String getValue(String name){
        SystemParam systemParam = systemParamMapper.findByName(name);
        if (systemParam==null){
           return null;
        }
        return systemParam.getValue();
    }
}
