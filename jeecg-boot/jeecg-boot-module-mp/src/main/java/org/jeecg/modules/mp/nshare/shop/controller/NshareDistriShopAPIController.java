package org.jeecg.modules.mp.nshare.shop.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.controller.CommonController;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.mp.nshare.comm.entity.NshareCommAttach;
import org.jeecg.modules.mp.nshare.comm.service.INshareCommAttachService;
import org.jeecg.modules.mp.nshare.shop.entity.*;
import org.jeecg.modules.mp.nshare.shop.service.*;
import org.jeecg.modules.mp.nshare.shop.vo.NshareDistriShopPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送店铺
 * @Author:
 * @Date: 2020-03-05
 * @Version: V1.0
 */
@Api(tags = "小程序-近邻分享-配送店铺")
@RestController
@RequestMapping("/mp/api/ns/s")
@Slf4j
public class NshareDistriShopAPIController extends CommonController {
    @Autowired
    private INshareDistriShopService nshareDistriShopService;
    @Autowired
    private INshareDistriShopAdminService nshareDistriShopAdminService;
    @Autowired
    private INshareDistriShopStationService nshareDistriShopStationService;
    @Autowired
    private INshareDistriShopGoodsCatService nshareDistriShopGoodsCatService;
    @Autowired
    private INshareCommAttachService nshareCommAttachService;
    @Autowired
    private INshareDistriShopPromotionService promotionService;
    @Autowired
    private INshareDistriShopTeamService nshareDistriShopTeamService;

    @ApiOperation(value = "获取店铺列表", notes = "可按距离排序，可搜索")
    @GetMapping(value = "/{pageIndex}/shops")
    public Result<?> doGetShops(@PathVariable(name = "pageIndex") Integer pageIndex,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(name = "searchKey", required = false) String searchKey,
                                @RequestParam(name = "lng", required = false) BigDecimal lng,
                                @RequestParam(name = "lat", required = false) BigDecimal lat,
                                HttpServletRequest req) {
        IPage<Map> pageList = nshareDistriShopService.loadList4API(pageSize, pageIndex, lng, lat, searchKey);
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
                map.put("img_list", imgList);
            }
        }
        return Result.ok(pageList);
    }

    @ApiOperation(value = "获取店铺信息", notes = "根据id获取店铺信息")
    @GetMapping(value = "/{id}")
    public Result<?> doGetShopById(@PathVariable(name = "id") String id,
                                   HttpServletRequest req) {
        NshareDistriShopPage shopPage = new NshareDistriShopPage();
        NshareDistriShop shop = nshareDistriShopService.getById(id);
        BeanUtils.copyProperties(shop, shopPage);
        List<NshareDistriShopAdmin> admins = nshareDistriShopAdminService.selectByMainId(id);
        shopPage.setAdmins(admins);
        List<NshareDistriShopGoodsCat> cats = nshareDistriShopGoodsCatService.selectByMainId(id);
        shopPage.setCats(cats);
        List<NshareCommAttach> imgs = nshareCommAttachService.selectByBuss("nshare_distri_shop", id, "LOGO");
        List<String> imgList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(imgs)) {
            for (NshareCommAttach img : imgs) {
                imgList.add(img.getAttachPath());
            }
        }
        shopPage.setImgList(imgList);
        List<NshareDistriShopStation> stations = nshareDistriShopStationService.selectByMainId(id);
        shopPage.setStations(stations);

        List<Map> teams=nshareDistriShopTeamService.selectShopTeams(id);
        shopPage.setTeams(teams);
        return Result.ok(shopPage);
    }

    @ApiOperation(value = "获取店铺品类添加信息", notes = "用户登录后，获取商品分类信息用于添加店铺品类")
    @GetMapping(value = "/{shopid}/cats")
    public Result<?> doGetShopCats(@PathVariable(name = "shopid") String shopid,
                                   HttpServletRequest req) {
        List catList = nshareDistriShopGoodsCatService.selectMapToAdd(shopid);
        return Result.ok(catList);
    }

    @ApiOperation(value = "添加、更新店铺", notes = "用户登录后添加或更新店铺信息")
    @PostMapping(value = "/auth/shop")
    public Result<?> doPostShop(@RequestBody NshareDistriShopPage nshareDistriShopPage,
                                HttpServletRequest req) {
        NshareDistriShop shop = new NshareDistriShop();
        BeanUtils.copyProperties(nshareDistriShopPage, shop);
        shop.setShopLevel("1");
        if (StringUtils.isEmpty(shop.getId())) {
            shop.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        shop.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        shop.setOwnerId(userId);

        String teamJson= JSON.toJSONString(nshareDistriShopPage.getTeams());
        List<NshareDistriShopTeam> teams=JSON.parseArray(teamJson,NshareDistriShopTeam.class);
        //新增
        if (StringUtils.isEmpty(shop.getId())) {
            //验证同一个店主是否存在同名店铺
            List<NshareDistriShop> shops = nshareDistriShopService.findShopsByOwnerId(userId);
            if (!CollectionUtils.isEmpty(shops)) {
                for (NshareDistriShop s : shops) {
                    if (s.getShopName().equalsIgnoreCase(shop.getShopName())) {
                        return Result.error("店铺已存在,不能重复创建");
                    }
                }
            }
            shop.setCreateTime(new Timestamp(System.currentTimeMillis()));
            shop.setUpdateTime(shop.getCreateTime());

            nshareDistriShopService.saveMain(shop, nshareDistriShopPage.getAdmins(), nshareDistriShopPage.getStations(), nshareDistriShopPage.getCats(),teams);
        } else {
            nshareDistriShopService.updateMain(shop, nshareDistriShopPage.getAdmins(), nshareDistriShopPage.getStations(), nshareDistriShopPage.getCats(),teams);
        }
        //查询返回已保存信息
        List<NshareDistriShop> nshops = nshareDistriShopService.findShopsByOwnerId(userId);
        if (CollectionUtils.isEmpty(nshops)) {
            return Result.error("店铺信息保存失败，请联系管理员！");
        }
        NshareDistriShopPage data = new NshareDistriShopPage();
        //返回数据店铺信息
        NshareDistriShop reShop = null;
        for (NshareDistriShop s : nshops) {
            if (s.getShopName().equalsIgnoreCase(shop.getShopName())) {
                reShop = s;
            }
        }
        BeanUtils.copyProperties(reShop, data);
        //保存图片记录
        if (reShop != null) {
            List<String> imgList = nshareDistriShopPage.getImgList();
            if (!CollectionUtils.isEmpty(imgList)) {
                //删除原有
                nshareCommAttachService.deleteByBuss("nshare_distri_shop", reShop.getId(), "LOGO");
                Collection atts = new ArrayList();
                for (String img : imgList) {
                    NshareCommAttach att = new NshareCommAttach();
                    att.setAttachCode("LOGO");
                    att.setBussTableName("nshare_distri_shop");
                    att.setBussId(reShop.getId());
                    att.setAttachPath(img);
                    att.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    atts.add(att);
                }
                nshareCommAttachService.saveBatch(atts);
            }
        }
        //返回管理员信息
        List<NshareDistriShopAdmin> admins = nshareDistriShopAdminService.selectByMainId(reShop.getId());
        data.setAdmins(admins);
        List<NshareDistriShopStation> stations = nshareDistriShopStationService.selectByMainId(reShop.getId());
        data.setStations(stations);
        List<Map> rteams=nshareDistriShopTeamService.selectShopTeams(reShop.getId());
        data.setTeams(rteams);
        //返回图片信息
        List<NshareCommAttach> imgs = nshareCommAttachService.selectByBuss("nshare_distri_shop", reShop.getId(), "LOGO");
        List<String> imgList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(imgs)) {
            for (NshareCommAttach img : imgs) {
                imgList.add(img.getAttachPath());
            }
        }
        data.setImgList(imgList);
        return Result.ok(data);
    }

    @ApiOperation(value = "添加、更新店铺品类", notes = "用户登录后添加或更新店铺品类")
    @PostMapping(value = "/auth/{shopid}/cats")
    public Result<?> doPostShopCats(@PathVariable(name = "shopid") String shopid,
                                    @RequestBody Collection<NshareDistriShopGoodsCat> goodsCats,
                                    HttpServletRequest req) {
        nshareDistriShopGoodsCatService.deleteByMainId(shopid);
        nshareDistriShopGoodsCatService.saveOrUpdateBatch(goodsCats);
        List catList = nshareDistriShopGoodsCatService.selectByMainId(shopid);
        return Result.ok(catList);
    }

    @ApiOperation(value = "获取店铺活动列表", notes = "店铺活动列表")
    @GetMapping(value = "/{pageIndex}/promos")
    public Result<?> doGetShopPromos(@PathVariable(name = "pageIndex") Integer pageIndex,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                     @RequestParam(name = "shopId", required = false) String shopId,
                                     @RequestParam(name = "searchKey", required = false) String searchKey,
                                     @RequestParam(name = "hisType", required = false) Integer hisType,
                                     @RequestParam(name = "auditType", required = false) Integer auditType,
                                     @RequestParam(name = "teamId", required = false) String teamId,
                                     HttpServletRequest req) {
        IPage<Map> pageList = promotionService.loadList4API(pageSize, pageIndex, shopId, hisType,auditType,teamId,searchKey);
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

//    @ApiOperation(value = "获取店铺活动信息", notes = "店铺活动信息")
//    @GetMapping(value = "/promo/{id}")
//    public Result<?> doGetShopPromoById(@PathVariable(name = "id") String id, HttpServletRequest req) {
//        NshareDistriShopPromotionPage promotionPage = new NshareDistriShopPromotionPage();
//        NshareDistriShopPromotion promotion = promotionService.getById(id);
//        BeanUtils.copyProperties(promotion, promotionPage);
//        List<NshareDistriShopPromotionGoods> goods = promotionService.loadPromosByGoodsId(id);
//        promotionPage.setGoods(goods);
//        List<NshareCommAttach> imgs = nshareCommAttachService.selectByBuss("nshare_distri_shop_promotion", id, "PICS");
//        List<String> imgList = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(imgs)) {
//            for (NshareCommAttach img : imgs) {
//                imgList.add(img.getAttachPath());
//            }
//        }
//        promotionPage.setImgList(imgList);
//        return Result.ok(promotionPage);
//    }
}
