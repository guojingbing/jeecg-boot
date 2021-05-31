package org.jeecg.modules.mp.nshare.comm.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.http.HttpUtil;
import org.jeecg.common.system.controller.CommonController;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.mp.common.service.ICommonUtilHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Api(tags = "小程序-近邻分享-其他接口")
@RestController
@RequestMapping("/mp/api/ns/other")
@Slf4j
public class OtherAPIController extends CommonController {
    @Autowired
    private ICommonUtilHandler cuHandler;
    public static String baseUrl="http://www.dodojk.com/";
    @ApiOperation(value = "dodojk GET接口调用包装", notes = "包装GET方式调用dodojk外部接口")
    @PostMapping(value = "/api/get")
    public Result<?> doGetAPI(@RequestBody JSONObject params,
                                    HttpServletRequest req) {
        String url=baseUrl+params.getString("uri");
        try {
            JSONObject jsonObject = HttpUtil.getForJSONObject(url,params,null,null,null);
            System.out.println(params);
            System.out.println(jsonObject);
            return Result.ok(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("接口调用失败");
    }

    @ApiOperation(value = "dodojk POST接口调用包装", notes = "包装POST方式调用dodojk外部接口")
    @PostMapping(value = "/api/post")
    public Result<?> doPostAPI(@RequestBody JSONObject params,
                               HttpServletRequest req) {
        String url=baseUrl+params.getString("uri");
        params.put("obj.Imei", UUID.randomUUID().toString());
        params.put("obj.cat_date", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        try {
            JSONObject jsonObject = HttpUtil.postForJSONObject(url,params,null,null,null);
            log.error(jsonObject.toJSONString());
            return Result.ok(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        return Result.error("接口调用失败");
    }

    @ApiOperation(value = "获取TRTC用户UserSig", notes = "腾讯实时语音视频UserSig获取")
    @GetMapping(value = "/trtc/config")
    public Result<?> doGetUserSig(@RequestParam(name = "userId") String userId,
                                  @RequestParam(name = "appid") String appid,
                                  HttpServletRequest req) {
        Map map = cuHandler.getTRTCConfig(userId,appid);
        return Result.ok(map);
    }
}