package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.Blog;
import com.site.blog.my.core.entity.BlogTagRelation;
import com.site.blog.my.core.entity.BugModel;
import com.site.blog.my.core.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BugMapper {
    //增加
    int insertBugInfo(BugModel record);
    //(批量)删除
    int deleteBugInfo(Integer[] ids);
    //修改
    int updateBugInfo(BugModel record);
    //单个查询
    int selectByParamKey(BugModel record);
    //查询所有的Bug单
    List<BugModel> selectAllBugInfo(PageQueryUtil pageUtil);

    List<BugModel> findBugListByType(@Param("type") int type, @Param("limit") int limit);
    //根据关键字查询某些Bug单
    List<BugModel> selectByKeyWords(BugModel record);

    int getTotalBugs(PageQueryUtil pageUtil);
}
