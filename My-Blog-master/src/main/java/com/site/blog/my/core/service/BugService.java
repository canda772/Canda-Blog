package com.site.blog.my.core.service;


import com.site.blog.my.core.controller.vo.SimpleBlogListVO;
import com.site.blog.my.core.entity.Blog;
import com.site.blog.my.core.entity.BugModel;
import com.site.blog.my.core.util.PageQueryUtil;
import com.site.blog.my.core.util.PageResult;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultVO;

import java.util.List;

public interface BugService {

    ResultVO saveBugs(BugModel bugModel);
//
//    PageResult getBugsPage(PageQueryUtil pageUtil);
//
//    Boolean deleteBatch(Integer[] ids);
//
//    int getTotalBugs();
//
//    /**
//     * 根据id获取详情
//     */
//    Blog getBugsById(String id);
//
//    /**
//     * 后台修改
//     */
//    String updateBugs(BugModel bugModel);
//    /**
//     * 根据搜索获取bugs
//     */
//    PageResult getBugsPageBySearch(String keyword, int page);
    /**
     * bugs详情
     */
    PageResult getBugsForIndexPage(int page);
}
