package com.site.blog.my.core.service.impl;

import com.site.blog.my.core.controller.vo.BlogListVO;
import com.site.blog.my.core.dao.BlogCategoryMapper;
import com.site.blog.my.core.dao.BugMapper;
import com.site.blog.my.core.entity.Blog;
import com.site.blog.my.core.entity.BlogCategory;
import com.site.blog.my.core.entity.BugModel;
import com.site.blog.my.core.service.BugService;
import com.site.blog.my.core.util.PageQueryUtil;
import com.site.blog.my.core.util.PageResult;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultVO;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BugServiceImpl implements BugService {
    private static final Logger logger = LoggerFactory.getLogger(BugServiceImpl.class);
    @Resource
    private BugMapper bugMapper;
    @Resource
    private BlogCategoryMapper categoryMapper;


    @Override
    public ResultVO saveBugs(BugModel bugModel) {
        if (!ObjectUtils.allNotNull(bugModel)){
            return ResultVO.fail("保存失败");
        }
        try {
            int bugNum = bugMapper.selectByParamKey(bugModel);
            if (bugNum==0){
                bugMapper.insertBugInfo(bugModel);
            }else {
                bugMapper.updateBugInfo(bugModel);
            }
        }catch (Exception e){
            logger.info("保存失败,失败原因为:{[]}",e);
        }
        return ResultVO.success("保存成功");
    }


    @Override
    public PageResult getBugsForIndexPage(int page) {
        Map params = new HashMap();
        params.put("page", page);
        //每页8条
        params.put("limit", 8);
//        params.put("blogStatus", 1);//过滤发布状态下的数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        List<BugModel> blogList = bugMapper.selectAllBugInfo(pageUtil);
        int total = bugMapper.getTotalBugs(pageUtil);
        PageResult pageResult = new PageResult(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }


//    private List<BlogListVO> getBlogListVOsByBlogs(List<Blog> blogList) {
//        List<BlogListVO> blogListVOS = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(blogList)) {
//            List<Integer> categoryIds = blogList.stream().map(Blog::getBlogCategoryId).collect(Collectors.toList());
//            Map<Integer, String> blogCategoryMap = new HashMap<>();
//            if (!CollectionUtils.isEmpty(categoryIds)) {
//                List<BlogCategory> blogCategories = categoryMapper.selectByCategoryIds(categoryIds);
//                if (!CollectionUtils.isEmpty(blogCategories)) {
//                    blogCategoryMap = blogCategories.stream().collect(Collectors.toMap(BlogCategory::getCategoryId, BlogCategory::getCategoryIcon, (key1, key2) -> key2));
//                }
//            }
//            for (Blog blog : blogList) {
//                BlogListVO blogListVO = new BlogListVO();
//                BeanUtils.copyProperties(blog, blogListVO);
//                if (blogCategoryMap.containsKey(blog.getBlogCategoryId())) {
//                    blogListVO.setBlogCategoryIcon(blogCategoryMap.get(blog.getBlogCategoryId()));
//                } else {
//                    blogListVO.setBlogCategoryId(0);
//                    blogListVO.setBlogCategoryName("默认分类");
//                    blogListVO.setBlogCategoryIcon("/admin/dist/img/category/00.png");
//                }
//                blogListVOS.add(blogListVO);
//            }
//        }
//        return blogListVOS;
//    }
}
