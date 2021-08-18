package com.site.blog.my.core.util;

import com.site.blog.my.core.service.impl.testServiceImpl;
import com.site.blog.my.core.service.testService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class testUtils implements InvocationHandler {

    private com.site.blog.my.core.service.testService testService;

    public  testUtils(testService testService){
        this.testService=testService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        return method.invoke(testService,args);
    }

    public void before(){
        System.out.println("对转账人身份进行验证.");
    }

    public static void main(String[] args){
        //创建目标对象
        testService test = new testServiceImpl();
        //创建代理对象
        testService proxy = (testService)Proxy.newProxyInstance(
                test.getClass().getClassLoader(),
                test.getClass().getInterfaces(),
                new testUtils(test)
        );
        proxy.transfer();
    }
}
