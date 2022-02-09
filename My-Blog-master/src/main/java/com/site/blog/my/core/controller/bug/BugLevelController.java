package com.site.blog.my.core.controller.bug;

import com.site.blog.my.core.entity.BugLevel;
import com.site.blog.my.core.service.LevelService;
import com.site.blog.my.core.util.PageQueryUtil;
import com.site.blog.my.core.util.Result;
import com.site.blog.my.core.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/bug")
public class BugLevelController {

    @Resource
    private LevelService levelService;

    @GetMapping("/levels")
    public String levelPage(HttpServletRequest request) {
        request.setAttribute("path", "levels");
//        return "blog/bug/levels";
        return "blog/bug/levels";
    }

    /**
     * 分类列表
     */
    @RequestMapping(value = "/levels/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(levelService.getBlogLevelPage(pageUtil));
    }

    /**
     * 分类添加
     */
    @RequestMapping(value = "/levels/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestParam("levelName") String levelName,
                       @RequestParam("levelSts") String levelSts,
                       @RequestParam("levelIcon") String levelIcon) {
        if (StringUtils.isEmpty(levelName)) {
            return ResultGenerator.genFailResult("请输入BUG内容！");
        }
        if (StringUtils.isEmpty(levelSts)) {
            return ResultGenerator.genFailResult("请选择BUG级别！");
        }
        if (StringUtils.isEmpty(levelIcon)) {
            return ResultGenerator.genFailResult("请选择BUG图标！");
        }
        if (levelService.saveLevel(levelName,levelSts,levelIcon)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("BUG内容重复");
        }
    }


    /**
     * 分类修改
     */
    @RequestMapping(value = "/levels/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestParam("levelId") Integer levelId,
                         @RequestParam("levelName") String levelName,
                         @RequestParam("levelSts") String levelSts,
                         @RequestParam("levelIcon") String levelIcon) {
        if (StringUtils.isEmpty(levelName)) {
            return ResultGenerator.genFailResult("请输入BUG内容！");
        }
        if (StringUtils.isEmpty(levelIcon)) {
            return ResultGenerator.genFailResult("请选择BUG图标！");
        }
        if (levelService.updateLevel(levelId, levelName,levelSts, levelIcon)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("BUG内容重复");
        }
    }


    /**
     * BUG删除
     */
    @RequestMapping(value = "/levels/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (levelService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    /**
     * BUG删除
     */
    @RequestMapping(value = "/levels/deleteAll", method = RequestMethod.GET)
    @ResponseBody
    public Result deleteAll() {
        int num = levelService.getDelTotalLevels();
        if (levelService.deleteAll()){
            return ResultGenerator.genSuccessResult("清除成功,共清除失效BUG数为:"+num);
        }
        return ResultGenerator.genFailResult("删除失败");
    }
}
