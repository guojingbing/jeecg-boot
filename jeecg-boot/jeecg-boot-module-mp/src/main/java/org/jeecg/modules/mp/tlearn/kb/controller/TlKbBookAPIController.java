package org.jeecg.modules.mp.tlearn.kb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbBookChapter;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbBookChapterService;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbBookContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 拾贝书籍
 * @Author:
 * @Date: 2020-01-03
 * @Version: V1.0
 */
@Api(tags = "小程序-拾贝-书籍")
@RestController
@RequestMapping("/mp/api/tl/kb/book")
@Slf4j
public class TlKbBookAPIController {
    @Autowired
    private ITlKbBookChapterService tlKbBookChapterService;
    @Autowired
    private ITlKbBookContentService tlKbBookContentService;
    /**
     * 书籍章节列表
     * @param bookId
     * @param searchKey
     * @return
     */
    @ApiOperation(value = "书籍章节列表", notes = "获取书籍章节列表")
    @GetMapping(value = "/{bookId}/chapters")
    public Result<?> doGetBookChapters(@PathVariable(name = "bookId") String bookId,
                                   @RequestParam(name = "searchKey", required = false) String searchKey) {
        Map map = new HashMap();
        List<TlKbBookChapter> list = tlKbBookChapterService.listBookChapters(bookId);
        if(!CollectionUtils.isEmpty(list)){
            List<TlKbBookChapter> firstList=new ArrayList<>();
            List<TlKbBookChapter> secondList=new ArrayList<>();
            JSONArray chArr=new JSONArray();
            for(TlKbBookChapter ch:list){
                if(ch.getLevel().intValue()==1){
                    firstList.add(ch);
                }else if(ch.getLevel().intValue()==2){
                    secondList.add(ch);
                }
            }
            for(TlKbBookChapter ch:firstList){
                JSONObject obj=new JSONObject();
                obj.put("nodeId",ch.getId());
                obj.put("nodeName",ch.getTitle());
                obj.put("nodeSort",ch.getOrderNum());
                obj.put("nodeLevel",ch.getLevel());
                JSONArray cArr=new JSONArray();
                for(TlKbBookChapter sech:secondList){
                    if(sech.getParentId().equalsIgnoreCase(ch.getId())){
                        JSONObject subObj=new JSONObject();
                        subObj.put("nodeId",sech.getId());
                        subObj.put("nodeName",sech.getTitle());
                        subObj.put("nodeSort",sech.getOrderNum());
                        subObj.put("nodeLevel",sech.getLevel());
                        subObj.put("contentId",sech.getContentId());
                        cArr.add(subObj);
                    }
                }
                obj.put("child",cArr);
                chArr.add(obj);
            }
            map.put("data",chArr);
        }else{
            map.put("data",null);
        }
        return Result.ok(map);
    }

    @ApiOperation(value = "章节内容", notes = "获取书籍章节内容")
    @GetMapping(value = "/cont/{contentId}")
    public Result<?> doGetBookContent(@PathVariable(name = "contentId") String contentId) {
        Map map = new HashMap();
        Map cont = tlKbBookContentService.selectBookContentMoreInfo(contentId);
        map.put("data",cont);
        return Result.ok(map);
    }
}
