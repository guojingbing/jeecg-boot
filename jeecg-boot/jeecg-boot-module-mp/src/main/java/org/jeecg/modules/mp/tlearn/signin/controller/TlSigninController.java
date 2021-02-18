package org.jeecg.modules.mp.tlearn.signin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.controller.CommonController;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSignin;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninTerm;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninTermItem;
import org.jeecg.modules.mp.tlearn.signin.service.*;
import org.jeecg.modules.mp.tlearn.user.entity.TlUser;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Description: 打卡
 * @Author:
 * @Date: 2020-02-03
 * @Version: V1.0
 */
@RestController
@RequestMapping("/mp/api/tl/signin")
@Slf4j
public class TlSigninController extends CommonController {
    @Autowired
    private ITlSigninService tlSigninService;
    @Autowired
    private ITlSigninTermItemService tlSigninTermItemService;
    @Autowired
    private ITlSigninCommentService tlSigninCommentService;
    @Autowired
    ITlSigninTermItemDanmuService danmuSer;
    @Autowired
    ITlUserService tlUserService;
    @Autowired
    ITlSigninTermService tlSigninTermService;
    @Autowired
    private RedisUtil redisUtil;

    String[] EPIGRAMS = new String[]{"愚公移山，贵在不舍。", "只要功夫深，铁杵磨成针。", "古人学问无遗力，少壮工夫老始成。", "一日一钱，十日十钱。", "绳锯木断，水滴石穿。",
    "不积跬步，无以至千里。","不积小流，无以成江海。","精诚所至，金石为开。","逆水行舟用力撑，一篙松劲退千寻。","锲而不舍，金石可镂。","驽马十驾，功在不舍。","不经一翻彻骨寒，怎得梅花扑鼻香。",
    ""};

    /**
     * 查询有效的打卡信息
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获取有效日期内的所有打卡", notes = "按开始结束日期")
    @GetMapping(value = "/signins")
    public Result<?> loadAllSignin(HttpServletRequest req) {
        String openid = null;
        String uid = null;
        String token = req.getHeader(X_ACCESS_TOKEN);
        if (!StringUtils.isEmpty(token)) {
            openid = JwtUtil.getUsername(token);
            TlUser user = tlUserService.getOne(new LambdaQueryWrapper<TlUser>().eq(TlUser::getOpenid, openid));
            if (user != null) {
                uid = user.getId();
            }
        }
        List<Map> list = tlSigninService.selectAllTlSignin(uid);
        return Result.ok(list);
    }

    @ApiOperation(value = "获取指定打卡的当前参与信息", notes = "按主键打卡id获取")
    @GetMapping(value = "/{id}/cur/term")
    public Result<?> loadCurTerm(@PathVariable(name = "id") String id, HttpServletRequest req) {
        String openid = null;
        String uid = null;
        String token = req.getHeader(X_ACCESS_TOKEN);
        if (!StringUtils.isEmpty(token)) {
            openid = JwtUtil.getUsername(token);
            TlUser user = tlUserService.getOne(new LambdaQueryWrapper<TlUser>().eq(TlUser::getOpenid, openid));
            if (user != null) {
                uid = user.getId();
            }
        }
        List<Map> list = tlSigninService.selectSigninCurTerm(id, uid);
        Map reMap = new HashMap();
        reMap.put("term", CollectionUtils.isEmpty(list) ? null : list.get(0));
        reMap.put("id",id);
        //若没有当前参与信息返回一个随机的励志警句
        if (CollectionUtils.isEmpty(list)) {
            int max=EPIGRAMS.length-1;
            Random random = new Random();
            int index=random.nextInt(max);
            reMap.put("epigram", EPIGRAMS[index]);
        }
        return Result.ok(reMap);
    }

    @ApiOperation(value = "获取指定打卡参与信息", notes = "按主键id获取")
    @GetMapping(value = "/term/{id}")
    public Result<?> loadTerm(@PathVariable(name = "id") String id, HttpServletRequest req) {
        String openid = null;
        String token = req.getHeader(X_ACCESS_TOKEN);
        if (!StringUtils.isEmpty(token)) {
            openid = JwtUtil.getUsername(token);
        }
        //更新签到结束日期为当前日期
        TlSigninTerm term=tlSigninTermService.getById(id);
        term.setEndDate(new Date());
        tlSigninTermService.saveOrUpdate(term);

        List<Map> list = tlSigninTermItemService.selectSigninTermItems(id);
        //是否需要自动添加记录
        boolean needAddItem=false;
        if(!CollectionUtils.isEmpty(list)){
            if(list.get(0)!=null&&list.get(0).get("start_time")!=null){
                String sdate=DateUtils.formatDate(((Date)list.get(0).get("start_time")),"yyyy-MM-dd");
                String ndate=DateUtils.formatDate(new Date(),"yyyy-MM-dd");
                if(!sdate.equals(ndate)){
                    needAddItem=true;
                }
            }else{
                needAddItem=true;
            }
        }else{
            needAddItem=true;
        }

        Map map = new HashMap();
        //切换打卡内容
        if(needAddItem){
            map=changeSigninTermItem(term,1);
        }else{
            map.put("msg", "操作成功");
            map.put("errCode", "0");
        }
        map.put("term", term);
        return Result.ok(map);
    }

    @ApiOperation(value = "获取个人打卡足迹", notes = "分页获取")
    @GetMapping(value = "/{id}/{pageIndex}/items")
    public Result<?> queryPageList(@PathVariable(name = "id") String id,
                                   @PathVariable(name = "pageIndex") Integer pageIndex,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(name = "searchKey", required = false) String searchKey,
                                   @RequestParam(name = "isMine", required = false) String isMine,
                                   HttpServletRequest req) {
        String openid = null;
        String uid = null;
        String token = req.getHeader(X_ACCESS_TOKEN);
        if (!StringUtils.isEmpty(token)) {
            openid = JwtUtil.getUsername(token);
            TlUser user = tlUserService.getOne(new LambdaQueryWrapper<TlUser>().eq(TlUser::getOpenid, openid));
            if (user != null) {
                uid = user.getId();
            }
        }
        IPage<Map> pager = tlSigninTermItemService.loadList4API(pageSize, pageIndex, id, uid, searchKey);
        return Result.ok(pager);
    }

    @ApiOperation(value = "获取指定打卡详情信息", notes = "按主键id获取")
    @GetMapping(value = "/term/item/{id}")
    public Result<?> loadTermItem(@PathVariable(name = "id") String id, HttpServletRequest req) {
        String openid = null;
        String token = req.getHeader(X_ACCESS_TOKEN);
        if (!StringUtils.isEmpty(token)) {
            openid = JwtUtil.getUsername(token);
        }
        List<Map> list = tlSigninTermItemService.selectSigninTermItemById(id);
        Map reMap = new HashMap();
        reMap.put("item", list.get(0));
        List danmus = danmuSer.selectMapList(id);
        reMap.put("danmu", danmus);
        return Result.ok(reMap);
    }

    @ApiOperation(value = "获取指定打卡排行榜", notes = "分页获取")
    @GetMapping(value = "/{id}/{pageIndex}/rankings")
    public Result<?> loadSigninRankingList(@PathVariable(name = "id") String id,
                                           @PathVariable(name = "pageIndex") Integer pageIndex,
                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                           HttpServletRequest req) {
        Map map = new HashMap();
        String openid = null;
        String token = req.getHeader(X_ACCESS_TOKEN);
        if (!StringUtils.isEmpty(token)) {
            openid = JwtUtil.getUsername(token);
        }
        String uid = null;
        if (!StringUtils.isEmpty(token)) {
            TlUser user = tlUserService.getOne(new LambdaQueryWrapper<TlUser>().eq(TlUser::getOpenid, openid));
            if (user != null) {
                uid = user.getId();
            }
        }
        if(uid==null){
            map.put("msg", "token用户信息验证失败");
            map.put("errCode", "2");
            return Result.ok(map);
        }
        IPage<Map> pager = tlSigninService.loadPagerRankingList(pageSize, pageIndex, id,null);
        map.put("errCode", "0");
        map.put("top", pager.getRecords());
        IPage<Map> myPager = tlSigninService.loadPagerRankingList(pageSize, pageIndex, id,uid);
        map.put("mine", myPager.getRecords());
        return Result.ok(map);
    }

    @ApiOperation(value = "获取指定打卡评论信息", notes = "分页获取")
    @GetMapping(value = "/{id}/comments")
    public Result<?> loadSigninComments(@PathVariable(name = "id") String id,
                                        @PathVariable(name = "pageIndex") Integer pageIndex,
                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                        HttpServletRequest req) {
        String openid = null;
        String token = req.getHeader(X_ACCESS_TOKEN);
        if (!StringUtils.isEmpty(token)) {
            openid = JwtUtil.getUsername(token);
        }
        IPage<Map> pager = tlSigninCommentService.loadPagerMapList(pageSize, pageIndex, id);
        return Result.ok(pager);
    }

    @ApiOperation(value = "开始打卡", notes = "提交打卡参与信息")
    @PostMapping(value = "/term")
    public Result<?> doPostTerm(@RequestBody TlSigninTerm tlSigninTerm, HttpServletRequest req) {
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
        String uid = null;
        if (!StringUtils.isEmpty(token)) {
            TlUser user = tlUserService.getOne(new LambdaQueryWrapper<TlUser>().eq(TlUser::getOpenid, openid));
            if (user != null) {
                uid = user.getId();
            }
        }
        if(uid==null){
            map.put("msg", "token用户信息验证失败");
            map.put("errCode", "2");
            return Result.ok(map);
        }
        tlSigninTerm.setUserId(uid);
        tlSigninTerm.setStartDate(new Date());
        tlSigninTerm.setEndDate(new Date());
        tlSigninTermService.saveOrUpdate(tlSigninTerm);
        TlSigninTerm term = tlSigninTermService.getOne(new LambdaQueryWrapper<TlSigninTerm>().eq(TlSigninTerm::getSigninId, tlSigninTerm.getSigninId()).eq(TlSigninTerm::getStartDate, DateUtils.formatDate(tlSigninTerm.getStartDate(),"yyyy-MM-dd")));
        map.put("curTermId", term.getId());
        map.put("curSigninId", term.getSigninId());
        List<Map> list = tlSigninService.selectAllTlSignin(uid);
        map.put("signins", list);
        map.put("msg", "操作成功");
        map.put("errCode", "0");
        return Result.ok(map);
    }

    @ApiOperation(value = "更新每日阅读秒数", notes = "更新打卡每日阅读秒数，批量更新")
    @PostMapping(value = "/terms")
    public Result<?> doUpTerms(@RequestBody List<TlSigninTerm> terms, HttpServletRequest req) {
        Map map = new HashMap();
        String token = req.getHeader(X_ACCESS_TOKEN);
        if(CollectionUtils.isEmpty(terms)){
            map.put("msg", "没有需要更新的信息！");
            map.put("errCode", "2");
            return Result.ok(map);
        }
        Collection ts=new ArrayList();
        for(TlSigninTerm term:terms){
            TlSigninTerm t=tlSigninTermService.getById(term.getId());
            t.setSeconds(term.getSeconds());
            ts.add(t);
        }
        tlSigninTermService.saveOrUpdateBatch(ts);
        map.put("msg", "操作成功");
        map.put("errCode", "0");
        return Result.ok(map);
    }

    @ApiOperation(value = "切换打卡内容", notes = "为当前打卡进行中项目随机一个内容")
    @PostMapping(value = "/term/{id}/c/items")
    public Result<?> doChangeTermItem(@PathVariable(name = "id") String id, HttpServletRequest req) {
        Map map = new HashMap();
        String token = req.getHeader(X_ACCESS_TOKEN);
        //切换打卡内容
        TlSigninTerm term=tlSigninTermService.getById(id);
        map=changeSigninTermItem(term,2);
        List<TlSigninTermItem> items=tlSigninTermItemService.list(new LambdaQueryWrapper<TlSigninTermItem>().eq(TlSigninTermItem::getTermId, id).orderByDesc(TlSigninTermItem::getId));
        map.put("item",items.get(0));
        return Result.ok(map);
    }

    /**
     * 切换打卡内容
     * @param term
     * @return
     */
    public Map changeSigninTermItem(TlSigninTerm term,int src){
        Map map = new HashMap();
        //Redis并发锁
        String lockKey="TERM-"+term.getId();
        Boolean lock=redisUtil.multiSetIfAbsent(lockKey,System.currentTimeMillis());
        if(lock!=null&&lock){
            //随机一条打卡详情记录插入表中
            if(term==null){
                map.put("msg", "打卡参与信息不存在");
                map.put("errCode", "1");
                return map;
            }
            TlSignin signin=tlSigninService.getById(term.getSigninId());
            if(signin==null){
                map.put("msg", "该打卡已不存在");
                map.put("errCode", "1");
                return map;
            }
            List<Map> kbs=null;
            if (signin.getType().intValue()==1){
                kbs=tlSigninTermItemService.selectSigninPoetries();
            }else{
                kbs=tlSigninTermItemService.selectSigninKbs(signin.getType());
            }
            if(CollectionUtils.isEmpty(kbs)){
                map.put("msg", "知识库没有资源可以添加");
                map.put("errCode", "1");
                return map;
            }
            int max=kbs.size();
            Random random = new Random();
            int index=0;
            if(max>0){
                index=random.nextInt(max);
            }
            Map kb=kbs.get(index);
            TlSigninTermItem item=new TlSigninTermItem();
            item.setKbId(kb.get("id").toString());
            item.setLearnTitle(kb.get("title").toString());
            item.setStartTime(new Date());
            item.setEndTime(new Date());
            item.setTermId(term.getId());
            item.setSrc((short) src);
            tlSigninTermItemService.save(item);
            redisUtil.del(lockKey);
        }else{
            if(redisUtil.hasKey(lockKey)&&redisUtil.get(lockKey)!=null){
                //超时清除,10秒
                long lastTimestamp=Long.parseLong(redisUtil.get(lockKey).toString());
                if((System.currentTimeMillis()-lastTimestamp)>10000){
                    redisUtil.del(lockKey);
                }
            }
        }
        map.put("msg", "操作成功");
        map.put("errCode", "0");
        return map;
    }
}
