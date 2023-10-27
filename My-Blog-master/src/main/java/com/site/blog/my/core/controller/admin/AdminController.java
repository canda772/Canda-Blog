package com.site.blog.my.core.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.site.blog.my.core.config.Constants;
import com.site.blog.my.core.config.cache.RedisCacheService;
import com.site.blog.my.core.config.sms.SmsTypeEnum;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.sms.SmsReturnBean;
import com.site.blog.my.core.service.*;
import com.site.blog.my.core.service.sms.SmsCodeService;
import com.site.blog.my.core.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminUserService adminUserService;
    @Resource
    private BlogService blogService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private LinkService linkService;
    @Resource
    private TagService tagService;
    @Resource
    private CommentService commentService;
    @Resource
    private SmsCodeService smsCodeService;


    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }


    @GetMapping({"/map"})
    public String canda() {
        return "canda/map";
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        request.setAttribute("categoryCount", categoryService.getTotalCategories());
        request.setAttribute("blogCount", blogService.getTotalBlogs());
        request.setAttribute("linkCount", linkService.getTotalLinks());
        request.setAttribute("tagCount", tagService.getTotalTags());
        request.setAttribute("commentCount", commentService.getTotalComments());
        return "admin/index";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "admin/login";
        }
        AdminUser adminUser = adminUserService.login(userName, password);
        String userLock = adminUser.getLocked();
        if (userLock.equals("1")){
            session.setAttribute("errorMsg", "该用户已被锁定,请联系管理员QQ:995829376");
            return "admin/login";
        }
        if (adminUser != null) {
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
//            session过期时间设置为7200秒 即两小时
            session.setMaxInactiveInterval(60 * 60 * 2);
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "登陆失败");
            return "admin/login";
        }
    }

    @PostMapping("/sendSmsCode")
    public String sendSmsCode(@RequestParam("mobileNo")String mobileNo,
                              HttpSession session) throws Exception {

        if (StringUtils.isEmpty(mobileNo)){
            session.setAttribute("errorMsg", "手机号不能为空");
            return "admin/register";
        }
        try {
            SmsReturnBean  smsReturnBean = smsCodeService.sendSmsCode(mobileNo, SmsTypeEnum.valueOfName("login"),"blog");
            if(!smsReturnBean.getErrorCode().equals("000000")){
                session.setAttribute("errorMsg", "短信发送失败,请重试");
                return "admin/register";
            }
        }catch (Exception e){
            session.setAttribute("errorMsg", "短信发送失败,请重试");
            return "admin/register";
        }
        session.setAttribute("errorMsg", "短信已发送");
        return "admin/register";
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getLoginUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }


    @PostMapping("/profile/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("loginUserName") String loginUserName,
                             @RequestParam("nickName") String nickName) {
        if (StringUtils.isEmpty(loginUserName) || StringUtils.isEmpty(nickName)) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updateName(loginUserId, loginUserName, nickName)) {
            return "success";
        } else {
            return "修改失败";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }
}
