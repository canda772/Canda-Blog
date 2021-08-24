package com.site.blog.my.core.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/games")
public class GameController {

    @GetMapping("/index")
    public String gameList(HttpServletRequest request) {
        request.setAttribute("path", "games");
        return "admin/game";
    }

    @GetMapping("/CanvasFeed")
    public String CanvasFeed(HttpServletRequest request) {
        request.setAttribute("path", "games");
        return "canda/Game/CanvasFeed";
    }

    @GetMapping("/mofang")
    public String mofang(HttpServletRequest request) {
        request.setAttribute("path", "games");
        return "canda/Game/3D mofang";
    }
}
