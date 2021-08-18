package com.site.blog.my.core.service.impl;

import com.site.blog.my.core.service.testService;
import org.springframework.stereotype.Service;

@Service
public class testServiceImpl implements testService {
    @Override
    public void transfer(){
        System.out.println("调用dao层,完成转账主业务.");
    }
}
