package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.BlogCategory;
import com.site.blog.my.core.entity.BugLevel;
import com.site.blog.my.core.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BugLevelMapper {
    int deleteByPrimaryKey(String levelId);

    int insert(BugLevel record);

    int insertSelective(BugLevel record);

    BugLevel selectByPrimaryKey(Integer levelId);

    BugLevel selectByLevelName(String levelName);

    int updateByPrimaryKeySelective(BugLevel record);

    int updateByPrimaryKey(BugLevel record);

    List<BugLevel> findLevelList(PageQueryUtil pageUtil);

    List<BugLevel> selectByLevelsIds(@Param("levelIds") List<Integer> levelIds);

    int getTotalLevels(PageQueryUtil pageUtil);

    int getDelTotalLevels(PageQueryUtil pageUtil);

    int deleteBatch(Integer[] ids);

    int deleteAll();
}
