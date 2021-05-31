package org.jeecg.modules.mp.healthy.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.controller.CommonController;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.mp.healthy.station.service.IHealthyStationUserService;
import org.jeecg.modules.mp.healthy.user.entity.HealthyUserFriend;
import org.jeecg.modules.mp.healthy.user.entity.HealthyUserSetting;
import org.jeecg.modules.mp.healthy.user.service.IHealthyRecordService;
import org.jeecg.modules.mp.healthy.user.service.IHealthyUserFriendService;
import org.jeecg.modules.mp.healthy.user.service.IHealthyUserSettingService;
import org.jeecg.modules.mp.nshare.user.entity.NshareUser;
import org.jeecg.modules.mp.nshare.user.service.INshareUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "小程序-近邻分享-健康信息记录档案")
@RestController
@RequestMapping("/mp/api/ns/h")
@Slf4j
public class HealthyUserAPIController extends CommonController {
    @Autowired
    private IHealthyRecordService recSer;
    @Autowired
    private IHealthyUserSettingService usSer;
    @Autowired
    private IHealthyUserFriendService ufSer;
    @Autowired
    private INshareUserService nshareUserService;
    @Autowired
    private IHealthyStationUserService suSer;

    @ApiOperation(value = "健康信息采集数据提交接口", notes = "提交健康信息")
    @PostMapping(value = "/rec")
    public Result<?> doPostHealthyRec(@RequestBody String jsonStr, HttpServletRequest req) {
        try {
            String token = req.getHeader(X_ACCESS_TOKEN);
            String userid = JwtUtil.getUsername(token);
            JSONObject paramJson = JSONObject.parseObject(jsonStr);
            JSONArray recArr = paramJson.getJSONArray("recs");
            recSer.saveRecBatch(recArr);
            return Result.ok("数据保存成功");
        } catch (Exception e) {
            log.error(e.toString());
        }
        return Result.error("数据保存失败");
    }

    @ApiOperation(value = "获取用户健康档案数据", notes = "根据指定类型获取用户健康档案数据")
    @GetMapping(value = "/recs")
    public Result<?> doGetHealthyRecs(@RequestParam(name = "pageIndex") Integer pageIndex,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                      @RequestParam(name = "userId", required = false) String userId,
                                      @RequestParam(name = "htype", required = false) Integer htype,
                                      @RequestParam(name = "startDate", required = false) String startDate,
                                      @RequestParam(name = "endDate", required = false) String endDate,
                                      HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        if (StringUtils.isBlank(userId)) {
            userId = JwtUtil.getUsername(token);
        }
        IPage<Map> pageList = recSer.loadList4API(pageSize,pageIndex,userId,htype,startDate,endDate);
        return Result.ok(pageList);
    }

    @ApiOperation(value = "用户健康设置", notes = "用户健康设置")
    @PostMapping(value = "/setting")
    public Result<?> doPostUserSetting(@RequestBody HealthyUserSetting userSetting, HttpServletRequest req) {
        HealthyUserSetting upUserSetting=userSetting;
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        if(userSetting.getId()==null||userSetting.getId().intValue()==0){
            //查询是否已经存在用户设置
            HealthyUserSetting ous = usSer.getOne(new LambdaQueryWrapper<HealthyUserSetting>().eq(HealthyUserSetting::getUserId, userId));
            if(ous!=null){
                upUserSetting=ous;
                upUserSetting.setDestSteps(userSetting.getDestSteps());
                upUserSetting.setHealthyServNumber(userSetting.getHealthyServNumber());
                upUserSetting.setLifeServNumber(userSetting.getLifeServNumber());
            }
        }
        upUserSetting.setUpdateTime(new Date());
        upUserSetting.setUserId(userId);
        try {
            Map map = new HashMap();
            usSer.saveOrUpdate(upUserSetting);
            userSetting = usSer.getOne(new LambdaQueryWrapper<HealthyUserSetting>().eq(HealthyUserSetting::getUserId, userId));
            map.put("userSettingInfo",userSetting);
            return Result.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("保存失败");
    }

    @ApiOperation(value = "添加亲友", notes = "发起亲友授权请求")
    @PostMapping(value = "/friend")
    public Result<?> doPostUserFriend(@RequestBody String jsonStr, HttpServletRequest req) {
        JSONObject paramJson = JSONObject.parseObject(jsonStr);
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);

        String frendsPhone = paramJson.getString("phone");
        //查询要添加亲友的用户是否存在
        NshareUser fuser = nshareUserService.getOne(new LambdaQueryWrapper<NshareUser>().eq(NshareUser::getPhone, frendsPhone));
        if(fuser==null){
            return Result.error("用户不存在");
        }

//        if(fuser.getId().equalsIgnoreCase(userId)){
//            return Result.error("不能添加自己为亲友");
//        }
        //查询是否已经存在用户亲友请求信息
        HealthyUserFriend ofrends = ufSer.getOne(new LambdaQueryWrapper<HealthyUserFriend>().eq(HealthyUserFriend::getUserId, userId).eq(HealthyUserFriend::getFriendId, fuser.getId()));
        if(ofrends!=null&&ofrends.getConfirmStatus().shortValue()==1){
            return Result.error("该用户已经是您的亲友不能重复添加");
        }
        if(ofrends==null){
            ofrends=new HealthyUserFriend();
            ofrends.setUserId(userId);
            ofrends.setFriendId(fuser.getId());
        }
        String frendsName = paramJson.getString("name");
        ofrends.setFriendName(frendsName);
        ofrends.setReqTime(new Date());
        ofrends.setConfirmStatus((short)0);

        ufSer.saveOrUpdate(ofrends);
        return Result.ok("请求成功，待对方确认");
    }

    @ApiOperation(value = "亲友授权确认", notes = "确认用户向我发起的授权请求")
    @PostMapping(value = "/confirm/friend")
    public Result<?> doConfirmUserFriend(@RequestBody HealthyUserFriend friend, HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        //查询是否已经存在用户亲友请求信息
        HealthyUserFriend ofriend = ufSer.getOne(new LambdaQueryWrapper<HealthyUserFriend>().eq(HealthyUserFriend::getId, friend.getId()));
        ofriend.setConfirmStatus(friend.getConfirmStatus());
        ofriend.setConfirmTime(new Date());
        ufSer.saveOrUpdate(ofriend);
        return Result.ok("亲友授权成功");
    }

    @ApiOperation(value = "获取用户亲友", notes = "获取已授权的用户亲友")
    @GetMapping(value = "/friends")
    public Result<?> doGetUserFriends(HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        List friends = ufSer.getUserFriends(userId);
        return Result.ok(friends);
    }

    @ApiOperation(value = "获取用户亲友授权请求", notes = "获取向我请求的亲友授权")
    @GetMapping(value = "/friend/reqs")
    public Result<?> doGetUserFriendReqs(HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        List friends = ufSer.getUserFriendReqs(userId);
        return Result.ok(friends);
    }
}