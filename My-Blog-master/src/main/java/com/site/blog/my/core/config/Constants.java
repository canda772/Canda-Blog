package com.site.blog.my.core.config;

/**
 * 配置信息
 */
public class Constants {
//    public final static String FILE_UPLOAD_DIC = "F:/project/blog/My-Blog-master/src/main/resources/templates/upload/";///opt/deploy/upload/ 上传文件的默认url前缀，根据部署设置自行修改
    public final static String FILE_UPLOAD_DIC = "C:/upload/";///opt/deploy/upload/ 上传文件的默认url前缀，根据部署设置自行修改

    //短信验证码在cache中的key
    public final static String SMS_CACHE_PREFIX="SMS_";
    //短信验证结果在cache中的key
    public final static String SMS_CACHE_VERITY_RESULT="SMS_RESULT_";
}
