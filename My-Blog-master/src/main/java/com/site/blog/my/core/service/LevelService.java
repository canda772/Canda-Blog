package com.site.blog.my.core.service;

import com.site.blog.my.core.entity.BlogCategory;
import com.site.blog.my.core.entity.BugLevel;
import com.site.blog.my.core.util.PageQueryUtil;
import com.site.blog.my.core.util.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;


public interface LevelService {
    /**
     * 查询级别的分页数据
     *
     * @param pageUtil
     * @return
     */
    PageResult getBlogLevelPage(PageQueryUtil pageUtil);

    int getTotalLevels();

    int getDelTotalLevels();

    /**
     * 添加级别数据
     */
    Boolean saveLevel(String LevelName,String levelSts,String levelIcon);

    Boolean updateLevel(Integer LevelId, String levelName, String levelSts,String levelIcon);

    Boolean deleteBatch(Integer[] ids);

    Boolean deleteAll();

    List<BugLevel> getAllLevels();
}
