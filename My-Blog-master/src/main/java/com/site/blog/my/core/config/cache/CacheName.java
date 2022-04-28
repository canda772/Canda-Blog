package com.site.blog.my.core.config.cache;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CacheName {
    /** 系统参数映射缓存 表 system_param 数据*/
    public static final String SYSTEM_PARAM ="SYSTEM_PARAM";

    public static String[] getCaffeineCacheNames(){
        List<String> caffeineCacheNames = new ArrayList<>();
        caffeineCacheNames.add(SYSTEM_PARAM);
        return caffeineCacheNames.toArray(new String[caffeineCacheNames.size()]);
    }
}
