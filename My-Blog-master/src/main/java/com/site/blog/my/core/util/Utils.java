package com.site.blog.my.core.util;

import com.site.blog.my.core.util.entity.JdEntity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static void jingdong(JdEntity jdEntity) throws Exception{
        //浏览器驱动路径
        System.setProperty("webdriver.chrome.driver","C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");

        //设置秒杀时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSSSSSSS");
        Date date = sdf.parse("2022-04-14 14:07:00 000000000");

        //1、打开浏览器
        ChromeDriver browser = new ChromeDriver();
        Actions actions = new Actions(browser);

        //浏览器窗口最大化
        browser.manage().window().maximize();

        //2、输入网址
        browser.get("https://www.jd.com/");
        Thread.sleep(1000);

        //3、点击登录
        browser.findElement(By.linkText("你好，请登录")).click();
        Thread.sleep(1000);


        switch (jdEntity.getLoginType()){
            case "1":
                //定位输入框元素
                WebElement txtbox = browser.findElement(By.id("loginname"));
                txtbox.sendKeys(jdEntity.getUserName());
                WebElement btn = browser.findElement(By.id("nloginpwd"));
                btn.sendKeys(jdEntity.getPassWord());
                browser.findElement(By.className("submit-btn")).click();
                break;
            case "2":
                //4、扫码登录
                browser.findElement(By.className("login-tab-l")).click();
                Thread.sleep(10000);
                break;
            default:
                break;
        }


        //5、进入商品页面
        browser.get(jdEntity.getUrl());
        Thread.sleep(3000);

        //6、加入购物车
        browser.findElement(By.id("InitCartUrl")).click();
        Thread.sleep(3000);


        //5、进入购物车页面
        browser.get("https://cart.jd.com/cart_index");
        Thread.sleep(3000);
        System.out.println("已进入购物车页面");

        //6、点击选择第一个按钮
        browser.findElement(By.xpath("//*[@id=\"J_Order_s_2208847254305_1\"]/div[1]/div/div/label")).click();

        Thread.sleep(2000);
        while (true){
            //当前时间
            Date now = new Date();
            System.out.println("正在轮询结算，当前时间："+now);
            if(now.after(date)){
                if(browser.findElement(By.linkText("结 算")).isEnabled()){
                    browser.findElement(By.linkText("结 算")).click();
                    System.out.println("结算成功");
                    break;
                }

            }
        }
        Thread.sleep(5000);
    }


















    public static void suNing() throws Exception{
        //浏览器驱动路径
        System.setProperty("webdriver.chrome.driver","C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");

        //设置秒杀时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSSSSSSS");
        Date date = sdf.parse("2022-04-14 14:07:00 000000000");

        //1、打开浏览器
        ChromeDriver browser = new ChromeDriver();
        Actions actions = new Actions(browser);

        //浏览器窗口最大化
        browser.manage().window().maximize();


        //2、输入网址
        browser.get("https://www.suning.com/");
        Thread.sleep(1000);

        //3、点击登录-扫码登录
        browser.findElement(By.linkText("请登录")).click();
        Thread.sleep(15000);


        //进入商品页面-茅台
        browser.get("https://search.suning.com/%E8%8C%85%E5%8F%B0/");
        Thread.sleep(100);
        browser.findElement(By.id("ssdsn_search_pro_baoguang-1-0-1_1_01:0000000000_12354857650")).click();
        Thread.sleep(2000);
        browser.findElement(By.id("addCart")).click();

//        String yuyue = browser.findElement(By.id("addCart")).getText();
//        Thread.sleep(2000);
//        System.out.println(yuyue);
//        Boolean flag = true;
//        while (true){
//            if ("立即预约".equals(yuyue) && flag){
//                browser.findElement(By.tagName("item_12354857650_gmq_yyljyy")).click();
//                flag = false;
//            }else {
//                break;
//            }
//        }




        //5、进入购物车页面
        browser.get("https://cart.taobao.com/cart.htm");
        Thread.sleep(3000);
        System.out.println("已进入购物车页面");

        //6、点击选择第一个按钮
        browser.findElement(By.xpath("//*[@id=\"J_Order_s_2208847254305_1\"]/div[1]/div/div/label")).click();

        Thread.sleep(2000);
        //结算页面
        while (true){
            //当前时间
            Date now = new Date();
            System.out.println("正在轮询结算，当前时间："+now);
            if(now.after(date)){
                if(browser.findElement(By.linkText("结 算")).isEnabled()){
                    browser.findElement(By.linkText("结 算")).click();
                    System.out.println("结算成功");
                    break;
                }
            }
        }
        Thread.sleep(100);

        while (true){
            browser.findElement(By.className("go-btn")).click();
            break;
        }

        return;
    }


















    public static void taobao() throws Exception{
        //浏览器驱动路径
        System.setProperty("webdriver.chrome.driver","C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");

        //设置秒杀时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSSSSSSS");
        Date date = sdf.parse("2022-04-14 14:07:00 000000000");

        //1、打开浏览器
        ChromeDriver browser = new ChromeDriver();
        Actions actions = new Actions(browser);

        //浏览器窗口最大化
        browser.manage().window().maximize();


        //2、输入网址
        browser.get("https://www.taobao.com");
        Thread.sleep(1000);
        //3、点击登录
        browser.findElement(By.linkText("亲，请登录")).click();
        Thread.sleep(1000);

        //4、扫码登录
        browser.findElement(By.className("icon-qrcode")).click();
        Thread.sleep(10000);


        //进入商品页面



        //5、进入购物车页面
        browser.get("https://cart.taobao.com/cart.htm");
        Thread.sleep(3000);
        System.out.println("已进入购物车页面");

        //6、点击选择第一个按钮
        browser.findElement(By.xpath("//*[@id=\"J_Order_s_2208847254305_1\"]/div[1]/div/div/label")).click();

        Thread.sleep(2000);
        //结算页面
        while (true){
            //当前时间
            Date now = new Date();
            System.out.println("正在轮询结算，当前时间："+now);
            if(now.after(date)){
                if(browser.findElement(By.linkText("结 算")).isEnabled()){
                    browser.findElement(By.linkText("结 算")).click();
                    System.out.println("结算成功");
                    break;
                }
            }
        }
        Thread.sleep(100);

        while (true){
            browser.findElement(By.className("go-btn")).click();
            break;
        }

        return;
    }

    public static void main(String[] args) throws Exception {
        JdEntity jdEntity = new JdEntity();
        jdEntity.setUrl("https://item.jd.com/100050245918.html");
        jdEntity.setUserName("");
        jdEntity.setPassWord("");
        jdEntity.setLoginType("2");


        System.out.println("开始测试");
        suNing();
        System.out.println("开始测试");
    }

}
