package com.site.blog.my.core.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

public class ReflectUtil {

    public static <T> T json2Bean(String jsonStr,Class<T> clz){
        if (StringUtils.isBlank(jsonStr)){
            return null;
        }
        try {
            return JSON.parseObject(jsonStr,clz);
        }catch (Exception e){
            return null;
        }
    }
}
