package com.site.blog.my.core.controller.bug;

import com.site.blog.my.core.entity.Blog;
import com.site.blog.my.core.entity.BugModel;
import com.site.blog.my.core.service.*;
import com.site.blog.my.core.util.PageResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/bug")
public class BugController {

    public static String theme = "bug";

    @Resource
    private BugService bugService;


    @GetMapping("/bugs")
    public String list(HttpServletRequest request) {
        request.setAttribute("path", "bugs");
        return "blog/bug/bug";
    }

    @GetMapping("/bugs/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        return "blog/"+theme+"/edit";
    }

    @GetMapping("/bugs/edit/{blogId}")
    public String edit(HttpServletRequest request, @PathVariable("blogId") String bugId) {
        request.setAttribute("path", "edit");
//        BugModel bug = bugService.getBlogById(bugId);
//        if (bug == null) {
//            return "error/error_400";
//        }
//        request.setAttribute("bug", bug);
        return "blog/"+theme+"/edit";
    }
}
