package com.site.blog.my.core.service.impl;

import com.site.blog.my.core.dao.BugLevelMapper;
import com.site.blog.my.core.dao.BugMapper;
import com.site.blog.my.core.entity.BugLevel;
import com.site.blog.my.core.service.IdGeneratorService;
import com.site.blog.my.core.service.LevelService;
import com.site.blog.my.core.util.PageQueryUtil;
import com.site.blog.my.core.util.PageResult;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class LevelServiceImpl implements LevelService {
    @Resource
    private BugLevelMapper bugLevelMapper;
    @Resource
    private BugMapper bugMapper;
    @Resource
    private IdGeneratorService idGeneratorService;

    @Override
    public PageResult getBlogLevelPage(PageQueryUtil pageUtil) {
        List<BugLevel> levelList = bugLevelMapper.findLevelList(pageUtil);
        int total = bugLevelMapper.getTotalLevels(pageUtil);
        PageResult pageResult = new PageResult(levelList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalLevels() {
        return bugLevelMapper.getTotalLevels(null);
    }

    @Override
    public int getDelTotalLevels() {
        return bugLevelMapper.getDelTotalLevels(null);
    }

    @Override
    public Boolean saveLevel(String levelName,String levelSts, String levelIcon) {
        BugLevel temp = bugLevelMapper.selectByLevelName(levelName);
        if (temp == null) {
            BugLevel bugLevel = new BugLevel();
            bugLevel.setLevelId(idGeneratorService.nextIdStr());
            bugLevel.setLevelName(levelName);
            bugLevel.setIsDeleted(new Byte("0"));
            bugLevel.setLevelSts(levelSts);
            bugLevel.setLevelCreateTime(new Date());
            bugLevel.setLevelIcon(levelIcon);
            return bugLevelMapper.insertSelective(bugLevel) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateLevel(Integer levelId, String levelName, String levelSts,String levelIcon) {
        BugLevel bugLevel = bugLevelMapper.selectByPrimaryKey(levelId);
        if (bugLevel != null) {
            bugLevel.setLevelIcon(levelIcon);
            bugLevel.setLevelName(levelName);
            bugLevel.setLevelSts(levelSts);
            bugLevel.setLevelUpdateTime(new Date());
            //修改分类实体
//            bugMapper.updateBugLevels(levelName, bugLevel.getLevelId(), new Integer[]{levelId});
            return bugLevelMapper.updateByPrimaryKeySelective(bugLevel) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //修改tb_blog表
//        bugMapper.updateBugLevels("默认分类", 0, ids);
        //删除分类数据
        return bugLevelMapper.deleteBatch(ids) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteAll() {
        //修改tb_blog表
//        bugMapper.updateBugLevels("默认分类", 0, ids);
        //删除分类数据
        return bugLevelMapper.deleteAll() > 0;
    }

    @Override
    public List<BugLevel> getAllLevels() {
        return bugLevelMapper.findLevelList(null);
    }

}
