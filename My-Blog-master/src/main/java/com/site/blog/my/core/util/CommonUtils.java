package com.site.blog.my.core.util;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.security.SecureRandom;

@Slf4j
public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private static SecureRandom random = new SecureRandom();


    /**
     * 生成验证码
     * @param length 验证码长度
     * @param hasChar 是否包含字母（不包含则为纯数字）
     * @return
     */
    public static String getRandomString(int length,boolean hasChar){
        StringBuilder ret = new StringBuilder();
        for (int i=0;i<length;i++){
            if (hasChar){
                boolean isChar =(random.nextInt(2)%2==0); //输出字母还是数字
                if (isChar){
                    int choice = random.nextInt(2)%2==0?65:97;//取得大写字母还是小写字母
                    ret.append((char)(choice+random.nextInt(26)));
                }else {
                    ret.append(random.nextInt(10));
                }
            }else {
                ret.append(random.nextInt(10));
            }
        }
        return ret.toString();
    }
}
