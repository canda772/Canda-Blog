package com.site.blog.my.core.util.entity;

import lombok.Data;

@Data
public class JdEntity {
    //用户名
    private String userName;
    //登录密码
    private String passWord;
    //登录方式
    private String loginType;
    //商品页面
    private String url;
}
