package com.site.blog.my.core.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台系统身份验证拦截器
 */
@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String requestServletPath = request.getServletPath();
        if ((requestServletPath.startsWith("/admin")) &&!(requestServletPath.contains("register"))&& null == request.getSession().getAttribute("loginUser")) {
            request.getSession().setAttribute("errorMsg", "请登录验证身份");
            request.getSession().setAttribute("key", "login");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        else if ((requestServletPath.contains("/bug")) && null == request.getSession().getAttribute("loginUser")){
            request.getSession().setAttribute("errorMsg", "请核验身份");
            request.getSession().setAttribute("key", "bug");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }else {
            request.getSession().removeAttribute("errorMsg");
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
