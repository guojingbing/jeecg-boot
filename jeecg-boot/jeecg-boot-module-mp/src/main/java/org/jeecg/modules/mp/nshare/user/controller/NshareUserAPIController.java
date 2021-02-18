package org.jeecg.modules.mp.nshare.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tencentyun.TLSSigAPIv2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.controller.CommonController;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopAdmin;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopAdminService;
import org.jeecg.modules.mp.nshare.user.entity.NshareUser;
import org.jeecg.modules.mp.nshare.user.entity.NshareUserCart;
import org.jeecg.modules.mp.nshare.user.entity.NshareUserTeam;
import org.jeecg.modules.mp.nshare.user.service.INshareUserCartService;
import org.jeecg.modules.mp.nshare.user.service.INshareUserService;
import org.jeecg.modules.mp.nshare.user.service.INshareUserTeamService;
import org.jeecg.modules.mp.tlearn.util.AesCbcUtil;
import org.jeecg.modules.mp.tlearn.util.CrawlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Description: 社区分享终端用户
 * @Author:
 * @Date: 2020-02-17
 * @Version: V1.0
 */
@Api(tags = "小程序-近邻分享-用户")
@RestController
@RequestMapping("/mp/api/ns/u")
@Slf4j
public class NshareUserAPIController extends CommonController {
    @Autowired
    private INshareUserService nshareUserService;
    @Autowired
    INshareDistriShopAdminService shopAdminService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private INshareUserTeamService nshareUserTeamService;
    @Autowired
    private INshareUserCartService nshareUserCartService;

    private static final Map<String, Map<String, String>> appAuth = new HashMap<>();

    static {
        Map<String, String> config = new HashMap();
        config.put("MP-SECRET", "a07fe08b99b99242a822ba83e65d0db4");
//        config.put("TRTC-SDKAPPID","1400474322");
//        config.put("TRTC-SECRET","739991025da854e1c67ca2bf1809a564c324926b22dfc1cdac2d7726622a5ea2");
        //近邻
        appAuth.put("wx5879c81e4cfc8ef5", config);
        config = new HashMap();
        config.put("MP-SECRET", "756a7872834755e493adb2a481a045c6");
//        config.put("TRTC-SDKAPPID","1400474322");
//        config.put("TRTC-SECRET","739991025da854e1c67ca2bf1809a564c324926b22dfc1cdac2d7726622a5ea2");
        //多多
        appAuth.put("wx035e90552bbb21fb", config);
        //多多锦医未
        config = new HashMap();
        config.put("MP-SECRET", "f1369440e6c36daf2d1758dd731192d0");
        config.put("TRTC-SDKAPPID", "1400474322");
        config.put("TRTC-SECRET", "739991025da854e1c67ca2bf1809a564c324926b22dfc1cdac2d7726622a5ea2");
        appAuth.put("wx004c4e41ee5ca5f8", config);
    }

    @ApiOperation(value = "获取微信用户敏感信息", notes = "获取微信用户敏感信息")
    @PostMapping(value = "/sendata")
    public Result<?> doGetSensitiveData(@RequestBody String jsonStr) {
        Map map = new HashMap();
        //登录凭证不能为空
        if (jsonStr == null || jsonStr.length() == 0) {
            return Result.error("body请求参数获取失败");
        }
        JSONObject paramJson = JSONObject.parseObject(jsonStr);
        String code = paramJson.getString("code");
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            return Result.error("code 不能为空");
        }

        String wxspAppid, wxspSecret;
        //授权（必填）
        String grantType = "authorization_code";
        //小程序端设置的appid
        String appid = paramJson.getString("appid");
        if (StringUtils.isBlank(appid)) {
            wxspAppid = "wx035e90552bbb21fb";
        } else {
            wxspAppid = appid;
        }
        wxspSecret = appAuth.get(wxspAppid).get("MP-SECRET");
        try {
            //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
            //发送请求
            Map params = new HashMap();
            params.put("appid", wxspAppid);
            params.put("secret", wxspSecret);
            params.put("js_code", code);
            params.put("grant_type", grantType);
            System.out.println(wxspAppid);
            System.out.println(wxspSecret);
            System.out.println(code);
            System.out.println(grantType);
            JSONObject json = CrawlerUtil.getForJSONObject("https://api.weixin.qq.com/sns/jscode2session", params, null, null, null);
            if(json==null||json.get("session_key")==null){
                return Result.error("session_key获取失败");
            }
            //获取会话密钥（session_key）
            String session_key = json.get("session_key").toString();
            map.put("sessionKey", session_key);
            //用户的唯一标识（openid）
            String openid = (String) json.get("openid");
            //查询是否存在已绑定的账号
            NshareUser user = nshareUserService.getOne(new LambdaQueryWrapper<NshareUser>().eq(NshareUser::getOpenid, openid).eq(NshareUser::getAppid, wxspAppid));
            if (user != null) {
                // 生成token
                String token = JwtUtil.sign(user.getId(), user.getId());
                // 设置token缓存有效时间
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
                map.put("userInfo", user);
                map.put("token", token);
                //验证是否是店铺管理人
                NshareDistriShopAdmin shopUser = shopAdminService.getOne(new LambdaQueryWrapper<NshareDistriShopAdmin>().eq(NshareDistriShopAdmin::getUserId, user.getId()));
                map.put("shopUser", shopUser);
                //用户社群信息
                List<NshareUserTeam> userTeams = nshareUserTeamService.selectByMainId(user.getId());
                map.put("userTeams", userTeams);
            } else {
                String encryptedData = paramJson.getString("encryptedData");
                String iv = paramJson.getString("iv");
                //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
                String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
                if (null != result && result.length() > 0) {
                    JSONObject userInfoJSON = JSONObject.parseObject(result);
                    user = new NshareUser();
                    user.setOpenid(userInfoJSON.getString("openId"));
                    user.setAvatarUrl(userInfoJSON.getString("avatarUrl"));
                    user.setCity(userInfoJSON.getString("city"));
                    user.setCountry(userInfoJSON.getString("country"));
                    user.setGender(userInfoJSON.getIntValue("gender"));
                    user.setUserName(userInfoJSON.getString("nickName"));
                    user.setProvince(userInfoJSON.getString("province"));
                    user.setUnionid(userInfoJSON.getString("unionId"));
                    user.setAppid(wxspAppid);
                    user.setIsUse(1);
                    nshareUserService.save(user);
                    user = nshareUserService.getOne(new LambdaQueryWrapper<NshareUser>().eq(NshareUser::getOpenid, openid).eq(NshareUser::getAppid, wxspAppid));
                    if (StringUtils.isEmpty(user.getPhone())) {
                        map.put("isRegUser", false);
                    } else {
                        map.put("isRegUser", true);
                        //验证是否是店铺管理人
                        NshareDistriShopAdmin shopUser = shopAdminService.getOne(new LambdaQueryWrapper<NshareDistriShopAdmin>().eq(NshareDistriShopAdmin::getUserId, user.getId()));
                        map.put("shopUser", shopUser);
                    }
                    //不返回手机号
                    user.setPhone(null);
                    map.put("userInfo", user);

                    // 生成token
                    String token = JwtUtil.sign(user.getId(), user.getId());
                    // 设置token缓存有效时间
                    redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
                    redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
                    map.put("token", token);
                }
            }
            //TRTC UserSig
            long sdkAppId=Long.parseLong(appAuth.get(wxspAppid).get("TRTC-SDKAPPID"));
            String sdkSecret=appAuth.get(wxspAppid).get("TRTC-SECRET");
            TLSSigAPIv2 api = new TLSSigAPIv2(sdkAppId, sdkSecret);
            String userId=user.getPhone();
            String userSig = api.genUserSig(userId, 3 * 86400);
            map.put("sdkAppID", sdkAppId);
            map.put("userSig", userSig);
            return Result.ok(map);
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//            log.error("doGetSensitiveData请求处理失败>>>>>>"+e.getMessage(),e);
        }
        return Result.error("解密失败");
    }

    @ApiOperation(value = "获取微信运动步数信息", notes = "解密微信运动步数")
    @PostMapping(value = "/werundata")
    public Result<?> doGetWeRunData(@RequestBody String jsonStr) {
        Map map = new HashMap();
        //登录凭证不能为空
        if (jsonStr == null || jsonStr.length() == 0) {
            return Result.error("body请求参数获取失败");
        }
        JSONObject paramJson = JSONObject.parseObject(jsonStr);
        String code = paramJson.getString("code");
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            return Result.error("code 不能为空");
        }

        String wxspAppid, wxspSecret;
        //授权（必填）
        String grantType = "authorization_code";
        //小程序端设置的appid
        String appid = paramJson.getString("appid");
        if (StringUtils.isBlank(appid)) {
            wxspAppid = "wx035e90552bbb21fb";
        } else {
            wxspAppid = appid;
        }
        wxspSecret = appAuth.get(wxspAppid).get("MP-SECRET");

        try {
            //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
            //发送请求
            Map params = new HashMap();
            params.put("appid", wxspAppid);
            params.put("secret", wxspSecret);
            params.put("js_code", code);
            params.put("grant_type", grantType);
            JSONObject json = CrawlerUtil.getForJSONObject("https://api.weixin.qq.com/sns/jscode2session", params, null, null, null);
            if(json==null||json.get("session_key")==null){
                return Result.error("session_key获取失败");
            }
            //获取会话密钥（session_key）
            String session_key = json.get("session_key").toString();
            //用户的唯一标识（openid）
            String openid = (String) json.get("openid");

            //查询是否存在已绑定的账号
            NshareUser user = nshareUserService.getOne(new LambdaQueryWrapper<NshareUser>().eq(NshareUser::getOpenid, openid).eq(NshareUser::getAppid, wxspAppid));
            if (user == null) {
                return Result.error("用户信息获取失败");
            }

            String encryptedData = paramJson.getString("encryptedData");
            String iv = paramJson.getString("iv");
            //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                JSONObject decryptData = JSONObject.parseObject(result);
                JSONArray stepInfoList = decryptData.getJSONArray("stepInfoList");

                map.put("steps", stepInfoList);
                return Result.ok(map);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return Result.error("解密失败");
    }

    @ApiOperation(value = "更新用户信息", notes = "用户绑定手机号等信息")
    @PostMapping(value = "/auth/user")
    public Result<?> doPostBindUser(@RequestBody NshareUser user, HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        if (user == null || StringUtils.isEmpty(user.getPhone())) {
            return Result.error("缺少参数");
        }
        NshareUser u = nshareUserService.getById(userId);
        if (u == null) {
            return Result.error("用户信息不存在");
        }
        u.setPhone(user.getPhone());
        u.setFkUserId(user.getFkUserId());
        if (user.getAvatarUrl() != null) {
            u.setAvatarUrl(user.getAvatarUrl());
        }
        if (user.getRealName() != null) {
            u.setRealName(user.getRealName());
        }
        if (user.getUserCode() != null) {
            u.setUserCode(user.getUserCode());
        }
        if (user.getProvince() != null) {
            u.setProvince(user.getProvince());
        }
        if (user.getCity() != null) {
            u.setCity(user.getCity());
        }
        if (user.getDistrict() != null) {
            u.setDistrict(user.getDistrict());
        }
        u.setServNumber(user.getServNumber());

        nshareUserService.saveOrUpdate(u);
//        u=nshareUserService.getById(userId);
        return Result.ok("操作成功");
    }

    @ApiOperation(value = "更新用户社群站点信息", notes = "群主注册")
    @PostMapping(value = "/auth/team")
    public Result<?> doPostUserTeam(@RequestBody NshareUserTeam team, HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        if (StringUtils.isBlank(team.getId())) {
            NshareUserTeam userTeam = nshareUserTeamService.getOne(new LambdaQueryWrapper<NshareUserTeam>().eq(NshareUserTeam::getUserId, userId).eq(NshareUserTeam::getTeamName, team.getTeamName()));
            if (userTeam != null) {
                return Result.error("社群已存在不能重复注册");
            }
            team.setUserId(userId);
            team.setTeamStatus(0);
        }
        nshareUserTeamService.saveOrUpdate(team);
        //用户社群信息
        List<NshareUserTeam> userTeams = nshareUserTeamService.selectByMainId(userId);
        return Result.ok(userTeams);
    }

    @ApiOperation(value = "更新用户社群站点使用状态", notes = "更新用户社群站点使用状态")
    @PostMapping(value = "/auth/use/team")
    public Result<?> doPostUserTeamUseStatus(@RequestBody NshareUserTeam team, HttpServletRequest req) {
//        String token = req.getHeader(X_ACCESS_TOKEN);
//        String userId = JwtUtil.getUsername(token);
        if (StringUtils.isBlank(team.getId())) {
            return Result.error("缺少主键参数");
        }
        NshareUserTeam userTeam = nshareUserTeamService.getById(team.getId());
        if (userTeam.getUseStatus() != null && userTeam.getUseStatus() == 1) {
            userTeam.setUseStatus(0);
        } else {
            userTeam.setUseStatus(1);
        }
        nshareUserTeamService.saveOrUpdate(userTeam);
        return Result.ok("操作成功");
    }

    @ApiOperation(value = "获取用户信息", notes = "根据手机号获取用户信息")
    @GetMapping(value = "/user")
    public Result<?> doGetUserByPhone(@RequestParam(name = "phone") String phone) {
        QueryWrapper<NshareUser> queryWrapper = new QueryWrapper<NshareUser>();
        queryWrapper.eq("phone", phone.trim());
        NshareUser user = nshareUserService.getOne(queryWrapper);
        return Result.ok(user);
    }

    @ApiOperation(value = "获取附近站点信息", notes = "根据站点距离排序")
    @GetMapping(value = "/{pageIndex}/teams")
    public Result<?> doGetUserTeams(@PathVariable(name = "pageIndex") Integer pageIndex,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                    @RequestParam(name = "searchKey", required = false) String searchKey,
                                    @RequestParam(name = "byUser", required = false) Boolean byUser,
                                    @RequestParam(name = "forShop", required = false) Integer forShop,
                                    @RequestParam(name = "lng", required = false) BigDecimal lng,
                                    @RequestParam(name = "lat", required = false) BigDecimal lat,
                                    HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = null;
        String shopId = null;
        if (byUser != null && !!byUser) {
            userId = JwtUtil.getUsername(token);
        } else {
            if (forShop != null) {
                NshareDistriShopAdmin admin = shopAdminService.getOne(new LambdaQueryWrapper<NshareDistriShopAdmin>().eq(NshareDistriShopAdmin::getUserId, JwtUtil.getUsername(token)));
                if (admin != null) {
                    shopId = admin.getShopId();
                }
            }
        }
        IPage<Map> pageList = nshareUserTeamService.loadList4API(pageSize, pageIndex, lng, lat, searchKey, userId, shopId);
        return Result.ok(pageList);
    }

    @ApiOperation(value = "获取站点信息", notes = "根据站点编号获取站点信息")
    @GetMapping(value = "/team")
    public Result<?> doGetUserTeam(@RequestParam(name = "stationId") String stationId) {
        QueryWrapper<NshareUserTeam> queryWrapper = new QueryWrapper<NshareUserTeam>();
        queryWrapper.eq("stationId", stationId.trim());
        NshareUserTeam team = nshareUserTeamService.getOne(queryWrapper);
        return Result.ok(team);
    }

    @ApiOperation(value = "获取用户购物车信息", notes = "可选择指定取货站点、商品获取所有购物车信息")
    @GetMapping(value = "/auth/carts")
    public Result<?> doGetUserCarts(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                    @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                    @RequestParam(name = "stationId", required = false) String stationId,
                                    @RequestParam(name = "goodsId", required = false) String goodsId,
                                    HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        if (pageSize == null) {
            pageSize = Integer.MAX_VALUE;
        }
        IPage<Map> pageList = nshareUserCartService.loadList4API(pageSize, pageIndex, userId, stationId, goodsId);
        if (pageList != null) {
            List<Map> list = pageList.getRecords();
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                if (map.get("imgs") == null) {
                    continue;
                }
                String imgStr = map.get("imgs").toString();
                String[] imgList = imgStr.split(",");
                map.remove("imgs");
                map.put("imgList", imgList);
            }
        }
        return Result.ok(pageList);
    }

    @ApiOperation(value = "更新用户购物车", notes = "更新用户购物车，加减数量")
    @PostMapping(value = "/auth/cart")
    public Result<?> doPostUserCart(@RequestBody NshareUserCart entity, HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        NshareUserCart upEn = nshareUserCartService.getOne(new LambdaQueryWrapper<NshareUserCart>().eq(NshareUserCart::getUserId, userId).eq(NshareUserCart::getStationId, entity.getStationId()).eq(NshareUserCart::getGoodsId, entity.getGoodsId()));
        if (upEn != null) {
            upEn.setNum(upEn.getNum() + entity.getNum());
        } else {
            upEn = entity;
            upEn.setUserId(userId);
        }
        upEn.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        nshareUserCartService.saveOrUpdate(upEn);
        List<NshareUserCart> carts = nshareUserCartService.selectByMainId(userId, entity.getStationId());
        int cartNum = 0;
        if (!CollectionUtils.isEmpty(carts)) {
            for (NshareUserCart cart : carts) {
                cartNum += cart.getNum();
            }
        }
        return Result.ok(cartNum);
    }

    @ApiOperation(value = "删除用户购物车商品", notes = "删除指定购物车商品")
    @DeleteMapping(value = "/auth/cart")
    public Result<?> doDeleteUserCart(@RequestBody List<NshareUserCart> list, HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        Collection colls = new ArrayList();
        for (NshareUserCart cart : list) {
            colls.add(cart.getId());
        }
        if (CollectionUtils.isEmpty(colls)) {
            return Result.error("没有要删除购物车商品");
        }
        nshareUserCartService.removeByIds(colls);

//        IPage<Map> pageList=nshareUserCartService.loadList4API(Integer.MAX_VALUE,1,userId,entity.getStationId(),entity.getGoodsId());
//        if(pageList==null|| CollectionUtils.isEmpty(pageList.getRecords())){
//            return Result.error("没有要删除购物车商品");
//        }
//        List<String> ids=new ArrayList<>();
//        for(Map c:pageList.getRecords()){
//            ids.add(c.get("id").toString());
//        }
//        nshareUserCartService.removeByIds(ids);
//        List<NshareUserCart> carts=nshareUserCartService.selectByMainId(userId,entity.getStationId());
//        int cartNum=0;
//        if(!CollectionUtils.isEmpty(carts)){
//            for(NshareUserCart cart:carts){
//                cartNum+=cart.getNum();
//            }
//        }
        return Result.ok("删除成功");
    }

    @ApiOperation(value = "获取TRTC用户UserSig", notes = "腾讯实时语音视频UserSig获取")
    @GetMapping(value = "/auth/userSig")
    public Result<?> doGetUserSig(@RequestParam(name = "userId") String userId,
                                  @RequestParam(name = "appid") String appid,
                                  HttpServletRequest req) {
        long sdkAppId=Long.parseLong(appAuth.get(appid).get("TRTC-SDKAPPID"));
        String sdkSecret=appAuth.get(appid).get("TRTC-SECRET");
        TLSSigAPIv2 api = new TLSSigAPIv2(sdkAppId, sdkSecret);
        String userSig = api.genUserSig(userId, 3 * 86400);
        Map map = new HashMap();
        map.put("sdkAppID", sdkAppId);
        map.put("userSig", userSig);
        return Result.ok(map);
    }
}
