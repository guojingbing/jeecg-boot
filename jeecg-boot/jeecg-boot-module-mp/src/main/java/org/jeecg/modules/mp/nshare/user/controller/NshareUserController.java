package org.jeecg.modules.mp.nshare.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.mp.nshare.user.entity.NshareUser;
import org.jeecg.modules.mp.nshare.user.service.INshareUserService;
import org.jeecg.modules.mp.tlearn.util.AesCbcUtil;
import org.jeecg.modules.mp.tlearn.util.CrawlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 社区分享终端用户
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
 @Api(tags = "小程序-近邻分享-用户")
 @RestController
 @RequestMapping("/mp/api/ns/user")
@Slf4j
public class NshareUserController extends JeecgController<NshareUser, INshareUserService> {
	@Autowired
	private INshareUserService nshareUserService;
    @Autowired
    private RedisUtil redisUtil;
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
       String wxspAppid = "wxa4e7cbe5d9288fd5";
       //小程序的 app secret (在微信小程序管理后台获取)
       String wxspSecret = "ef39e1d61320926ccd582cacd891c5bd";
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
           NshareUser user = nshareUserService.getOne(new LambdaQueryWrapper<NshareUser>().eq(NshareUser::getOpenid, openid).eq(NshareUser::getUserType, 1));
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
               user=new NshareUser();
               user.setOpenid(userInfoJSON.getString("openId"));
               user.setAvatarUrl(userInfoJSON.getString("avatarUrl"));
               user.setCity(userInfoJSON.getString("city"));
               user.setCountry(userInfoJSON.getString("country"));
               user.setGender(userInfoJSON.getIntValue("gender"));
               user.setUserName(userInfoJSON.getString("nickName"));
               user.setProvince(userInfoJSON.getString("province"));
               user.setUnionid(userInfoJSON.getString("unionId"));
               user.setUserType(1);
               user.setIsUse(1);
               nshareUserService.save(user);
               user=nshareUserService.getOne(new LambdaQueryWrapper<NshareUser>().eq(NshareUser::getOpenid, openid));
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
	/**
	 * 分页列表查询
	 *
	 * @param nshareUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(NshareUser nshareUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<NshareUser> queryWrapper = QueryGenerator.initQueryWrapper(nshareUser, req.getParameterMap());
		Page<NshareUser> page = new Page<NshareUser>(pageNo, pageSize);
		IPage<NshareUser> pageList = nshareUserService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param nshareUser
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody NshareUser nshareUser) {
		nshareUserService.save(nshareUser);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param nshareUser
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody NshareUser nshareUser) {
		nshareUserService.updateById(nshareUser);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		nshareUserService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.nshareUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		NshareUser nshareUser = nshareUserService.getById(id);
		if(nshareUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(nshareUser);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param nshareUser
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, NshareUser nshareUser) {
        return super.exportXls(request, nshareUser, NshareUser.class, "社区分享终端用户");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, NshareUser.class);
    }

}
