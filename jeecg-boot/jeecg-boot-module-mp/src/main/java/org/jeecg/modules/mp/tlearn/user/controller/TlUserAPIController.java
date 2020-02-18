package org.jeecg.modules.mp.tlearn.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.mp.tlearn.user.entity.TlUser;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserService;
import org.jeecg.modules.mp.tlearn.util.AesCbcUtil;
import org.jeecg.modules.mp.tlearn.util.CrawlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
* @Description: 小程序用户
* @Author:
* @Date:   2020-02-03
* @Version: V1.0
*/
@Api(tags = "小程序-拾贝-用户")
@RestController
@RequestMapping("/mp/api/tl/user")
@Slf4j
public class TlUserAPIController {
   @Autowired
   private ITlUserService tlUserService;
    @Autowired
    private RedisUtil redisUtil;
   /**
    * 分页列表查询
    *
    * @param tlUser
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @GetMapping(value = "/list")
   public Result<?> queryPageList(TlUser tlUser,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<TlUser> queryWrapper = QueryGenerator.initQueryWrapper(tlUser, req.getParameterMap());
       Page<TlUser> page = new Page<TlUser>(pageNo, pageSize);
       IPage<TlUser> pageList = tlUserService.page(page, queryWrapper);
       return Result.ok(pageList);
   }

   /**
    * 获取微信解密敏感数据
    * @param jsonStr
    * @return
    */
   @ApiOperation(value = "获取微信用户敏感信息", notes = "获取微信用户敏感信息")
   @PostMapping(value = "/sendata")
   public Result<?> doGetSensitiveData(@RequestBody String jsonStr) {
       Map map = new HashMap();
       //登录凭证不能为空
       if (jsonStr == null || jsonStr.length() == 0) {
           map.put("status", 0);
           map.put("msg", "body请求参数获取失败");
           return Result.ok(map);
       }
       JSONObject paramJson=JSONObject.parseObject(jsonStr);
       String code=paramJson.getString("code");
       //登录凭证不能为空
       if (code == null || code.length() == 0) {
           map.put("status", 0);
           map.put("msg", "code 不能为空");
           return Result.ok(map);
       }

       //小程序唯一标识   (在微信小程序管理后台获取)
       String wxspAppid = "wxaaaa7ffa2289241c";
       //小程序的 app secret (在微信小程序管理后台获取)
       String wxspSecret = "88074a54a0bd5dab5e4d7027403695e6";
       //授权（必填）
       String grantType = "authorization_code";
       try {
           //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
           //发送请求
           Map params = new HashMap();
           params.put("appid",wxspAppid);
           params.put("secret",wxspSecret);
           params.put("js_code",code);
           params.put("grant_type",grantType);
           JSONObject json = CrawlerUtil.getForJSONObject("https://api.weixin.qq.com/sns/jscode2session", params,null,null,null);
           //获取会话密钥（session_key）
           String session_key = json.get("session_key").toString();
           //用户的唯一标识（openid）
           String openid = (String) json.get("openid");

           //查询是否存在已绑定的账号
           TlUser user = tlUserService.getOne(new LambdaQueryWrapper<TlUser>().eq(TlUser::getOpenid, openid));
           if(user!=null){
               map.put("status", 1);
               map.put("msg", "历史绑定用户");
               map.put("userInfo", user);

               // 生成token
               String token = JwtUtil.sign(user.getOpenid(), user.getOpenid());
               // 设置token缓存有效时间
               redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
               redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);
               map.put("token", token);

               return Result.ok(map);
           }

           String encryptedData=paramJson.getString("encryptedData");
           String iv=paramJson.getString("iv");
           //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
           String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
           if (null != result && result.length() > 0) {
               map.put("status", 1);
               map.put("msg", "解密成功");
               JSONObject userInfoJSON= JSONObject.parseObject(result);
               user=new TlUser();
               user.setOpenid(userInfoJSON.getString("openId"));
               user.setAvatarUrl(userInfoJSON.getString("avatarUrl"));
               user.setCity(userInfoJSON.getString("city"));
               user.setCountry(userInfoJSON.getString("country"));
               user.setGender(userInfoJSON.getIntValue("gender"));
               user.setNickName(userInfoJSON.getString("nickName"));
               user.setProvince(userInfoJSON.getString("province"));
               user.setUnionid(userInfoJSON.getString("unionId"));
               tlUserService.save(user);
               user=tlUserService.getOne(new LambdaQueryWrapper<TlUser>().eq(TlUser::getOpenid, openid));
//               Map user = new HashMap();
//               user.put("openid", userInfoJSON.get("openId"));
//               user.put("nickName", userInfoJSON.get("nickName"));
//               user.put("gender", userInfoJSON.get("gender"));
//               user.put("city", userInfoJSON.get("city"));
//               user.put("province", userInfoJSON.get("province"));
//               user.put("country", userInfoJSON.get("country"));
//               user.put("avatarUrl", userInfoJSON.get("avatarUrl"));
//               user.put("unionId", userInfoJSON.get("unionId"));
               map.put("userInfo", user);

               // 生成token
               String token = JwtUtil.sign(user.getOpenid(), user.getOpenid());
               // 设置token缓存有效时间
               redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
               redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);
               map.put("token", token);

               return Result.ok(map);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       map.put("status", 0);
       map.put("msg", "解密失败");
       return Result.ok(map);
   }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @PostMapping(value = "/info")
    public Result<?> doUpdateUser(@RequestBody TlUser tlUser) {
        Map map = new HashMap();
       if(tlUser==null|| StringUtils.isEmpty(tlUser.getId())){
           map.put("status", 0);
           map.put("msg", "缺少参数");
       }else{
           tlUserService.saveOrUpdate(tlUser);
           map.put("status", 1);
           map.put("msg", "保存成功");
       }
       return Result.ok(map);
    }
   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       TlUser tlUser = tlUserService.getById(id);
       if(tlUser==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(tlUser);
   }
}
