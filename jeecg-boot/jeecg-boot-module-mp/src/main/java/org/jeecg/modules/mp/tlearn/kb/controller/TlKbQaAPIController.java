package org.jeecg.modules.mp.tlearn.kb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.controller.CommonController;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbQa;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbQaThumbs;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbQaService;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbQaThumbsService;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubQa;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserSubQaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 拾贝问答
 * @Author:
 * @Date: 2020-01-03
 * @Version: V1.0
 */
@Api(tags = "小程序-拾贝-问答")
@RestController
@RequestMapping("/mp/api/tl/kb/qa")
@Slf4j
public class TlKbQaAPIController extends CommonController {
    @Autowired
    private ITlKbQaService tlKbQaService;
    @Autowired
    private ITlKbQaThumbsService tlKbQaThumbsService;
    @Autowired
    private ITlUserSubQaService tlUserSubQaService;
    /**
     * 问答知识列表
     * @param searchKey
     * @return
     */
    @ApiOperation(value = "问答列表", notes = "按分类获取问答列表")
    @GetMapping(value = "/{type}/{pageIndex}/qas")
    public Result<?> doGetBookChapters(@PathVariable(name = "type") String type,
                                       @PathVariable(name="pageIndex") Integer pageIndex,
                                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                       @RequestParam(name="shareId", required = false) String shareId,
                                       @RequestParam(name = "searchKey", required = false) String searchKey,
                                       HttpServletRequest req) {
        String openid=null;
        String token = req.getHeader(X_ACCESS_TOKEN);
        if(!StringUtils.isEmpty(token)){
            openid = JwtUtil.getUsername(token);
        }
        IPage<Map> pageList = tlKbQaService.loadList4API(pageSize, pageIndex, type, openid, searchKey, shareId);
        return Result.ok(pageList);
    }


    @ApiOperation(value = "点赞", notes = "点赞或取消点赞")
    @PostMapping(value = "/thumbs")
    public Result<?> doPostThumbs(@RequestBody TlKbQa tlKbQa, HttpServletRequest req) {
        Map map = new HashMap();
        String token = req.getHeader(X_ACCESS_TOKEN);
        if(StringUtils.isEmpty(token)){
            map.put("msg", "没有token信息！");
            map.put("errCode", "2");
            return Result.ok(map);
        }
        String openid = JwtUtil.getUsername(token);
        if(StringUtils.isEmpty(openid)){
            map.put("msg", "无效的token！");
            map.put("errCode", "2");
            return Result.ok(map);
        }
        if (tlKbQa == null || StringUtils.isEmpty(tlKbQa.getId())) {
            map.put("msg", "缺少参数！");
            map.put("errCode", "1");
            return Result.ok(map);
        }
        String qaId = tlKbQa.getId();
        TlKbQa qa = tlKbQaService.getById(qaId);

        if (qa == null) {
            map.put("msg", "信息不存在！");
            map.put("errCode", "1");
            return Result.ok(map);
        }

        TlKbQaThumbs thumbs = tlKbQaThumbsService.selectQaThumbsByOpenid(qaId, openid);
        if (tlKbQa.getQaType() == 1 && thumbs == null) {
            thumbs = new TlKbQaThumbs();
            thumbs.setQaId(qaId);
            thumbs.setUserId(openid);
            thumbs.setCreateTime(new Timestamp(System.currentTimeMillis()));
            tlKbQaThumbsService.saveOrUpdate(thumbs);

            List<TlKbQaThumbs> list = tlKbQaThumbsService.selectByMainId(qaId);
            qa.setThumbsNum(list.size());
            for (TlKbQaThumbs t : list) {
                if (t.getUserId().equals(openid)) {
                    map.put("thumbsId", t.getId());
                    break;
                }
            }
            map.put("thumbsNum", list.size());
        }
        if (tlKbQa.getQaType() == 2 && thumbs != null) {
            tlKbQaThumbsService.removeById(thumbs.getId());

            List<TlKbQaThumbs> list = tlKbQaThumbsService.selectByMainId(qaId);
            qa.setThumbsNum(list.size());
            map.put("thumbsNum", list.size());
        }
        tlKbQaService.saveOrUpdate(qa);
        map.put("msg", "操作成功");
        map.put("errCode", "0");
        return Result.ok(map);
    }

    @ApiOperation(value = "收藏", notes = "收藏或取消收藏")
    @PostMapping(value = "/coll")
    public Result<?> doPostCollect(@RequestBody TlKbQa tlKbQa, HttpServletRequest req) {
        Map map = new HashMap();
        String token = req.getHeader(X_ACCESS_TOKEN);
        if(StringUtils.isEmpty(token)){
            map.put("msg", "没有token信息！");
            map.put("errCode", "2");
            return Result.ok(map);
        }
        String openid = JwtUtil.getUsername(token);
        if(StringUtils.isEmpty(openid)){
            map.put("msg", "无效的token！");
            map.put("errCode", "2");
            return Result.ok(map);
        }
        if (tlKbQa == null || StringUtils.isEmpty(tlKbQa.getId())) {
            map.put("msg", "缺少参数！");
            map.put("errCode", "1");
            return Result.ok(map);
        }
        String qaId = tlKbQa.getId();
        TlKbQa qa = tlKbQaService.getById(qaId);

        if (qa == null) {
            map.put("msg", "信息不存在！");
            map.put("errCode", "1");
            return Result.ok(map);
        }

        TlUserSubQa uqa = tlUserSubQaService.selectUserQaById(qaId, openid);
        if (tlKbQa.getQaType() == 1 && uqa == null) {
            uqa = new TlUserSubQa();
            uqa.setQaId(qaId);
            uqa.setUserId(openid);
            uqa.setCreateTime(new Timestamp(System.currentTimeMillis()));
            tlUserSubQaService.saveOrUpdate(uqa);

            List<TlUserSubQa> list = tlUserSubQaService.selectByQaId(qaId);
            qa.setCollectionNum(list.size());
            for (TlUserSubQa t : list) {
                if (t.getUserId().equals(openid)) {
                    map.put("collectionId", t.getId());
                    break;
                }
            }
            map.put("collectionNum", list.size());
        }
        if (tlKbQa.getQaType() == 2 && uqa != null) {
            tlUserSubQaService.removeById(uqa.getId());
            List<TlUserSubQa> list = tlUserSubQaService.selectByQaId(qaId);
            qa.setCollectionNum(list.size());
            map.put("collectionNum", list.size());
        }
        tlKbQaService.saveOrUpdate(qa);
        map.put("msg", "操作成功");
        map.put("errCode", "0");
        return Result.ok(map);
    }

    @ApiOperation(value = "分享", notes = "记录分享次数")
    @PostMapping(value = "/share")
    public Result<?> doPostShare(@RequestBody TlKbQa tlKbQa,
                                 HttpServletRequest req) {
        Map map = new HashMap();
        if (tlKbQa == null || StringUtils.isEmpty(tlKbQa.getId())) {
            map.put("msg", "缺少参数！");
            map.put("errCode", "1");
            return Result.ok(map);
        }
        String qaId = tlKbQa.getId();
        TlKbQa qa = tlKbQaService.getById(qaId);

        if (qa == null) {
            map.put("msg", "信息不存在！");
            map.put("errCode", "1");
            return Result.ok(map);
        }
        int shareNum = qa.getShareNum() == null ? 1 : qa.getShareNum().intValue() + 1;
        qa.setShareNum(shareNum);
        tlKbQaService.saveOrUpdate(qa);
        map.put("msg", "操作成功");
        map.put("errCode", "0");
        map.put("shareNum", shareNum);
        return Result.ok(map);
    }
}
