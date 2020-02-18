package org.jeecg.modules.mp.tlearn.poetry.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetry;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryComment;
import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryCommentService;
import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description: 拾贝古诗词知识库
 * @Author:
 * @Date: 2020-01-03
 * @Version: V1.0
 */
@Api(tags = "小程序-拾贝-古诗词")
@RestController
@RequestMapping("/mp/api/tl/kb/poe")
@Slf4j
public class TlKbPoetryAPIController {
    @Autowired
    private ITlKbPoetryService tlKbPoetryService;
    @Autowired
    private ITlKbPoetryCommentService tlKbPoetryCommentService;

    /**
     * 古诗词列表
     *
     * @param formId
     * @param pageIndex
     * @param pageSize
     * @param dynId
     * @return
     */
    @ApiOperation(value = "获取古诗词数据列表", notes = "分页获取古诗词")
    @GetMapping(value = "/{formId}/{pageIndex}/articles")
    public Result<?> queryPageList(@PathVariable(name = "formId") String formId,
                                   @PathVariable(name = "pageIndex") Integer pageIndex,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(name = "dynId", required = false) String dynId,
                                   @RequestParam(name = "searchKey", required = false) String searchKey) {
        IPage<Map> pageList = tlKbPoetryService.loadList4API(formId, dynId, pageSize, pageIndex,null,null,null);
        return Result.ok(pageList);
    }

    @ApiOperation(value = "古诗词搜索", notes = "古诗词搜索")
    @GetMapping(value = "/{pageIndex}/articles")
    public Result<?> searchPoetrys(
            @PathVariable(name = "pageIndex") Integer pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "bookId", required = false) String bookId,
            @RequestParam(name = "searchKey", required = false) String searchKey) {
        IPage<Map> pageList = tlKbPoetryService.loadList4API(null, null, pageSize, pageIndex,searchKey,null,bookId);
        return Result.ok(pageList);
    }

    @ApiOperation(value = "古诗词搜索", notes = "古诗词搜索")
    @GetMapping(value = "/tag/{pageIndex}/articles")
    public Result<?> doGetPoetrysByTag(
            @PathVariable(name = "pageIndex") Integer pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "tagStr", required = false) String tagStr) {
        if(tagStr.equalsIgnoreCase("爱好")){
            tagStr=null;
        }
        IPage<Map> pageList = tlKbPoetryService.loadList4API(null, null, pageSize, pageIndex,null,tagStr,null);
        return Result.ok(pageList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取古诗词信息", notes = "根据id获取古诗词信息")
    @GetMapping(value = "/article/{id}")
    public Result<?> queryById(@PathVariable(name = "id") String id) {
        TlKbPoetry tlKbPoetry = tlKbPoetryService.getById(id);
        if (tlKbPoetry == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(tlKbPoetry);

    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据古诗词id获取古诗词评论信息", notes = "根据古诗词id获取古诗词评论信息")
    @GetMapping(value = "/article/{id}/comments")
    public Result<?> queryTlKbPoetryCommentListByMainId(@PathVariable(name = "id") String id) {
        List<TlKbPoetryComment> tlKbPoetryCommentList = tlKbPoetryCommentService.selectByMainId(id);
        return Result.ok(tlKbPoetryCommentList);
    }
}
