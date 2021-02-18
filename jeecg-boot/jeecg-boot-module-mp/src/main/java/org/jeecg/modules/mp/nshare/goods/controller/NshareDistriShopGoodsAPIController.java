package org.jeecg.modules.mp.nshare.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.controller.CommonController;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.mp.nshare.comm.entity.NshareCommAttach;
import org.jeecg.modules.mp.nshare.comm.service.INshareCommAttachService;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoods;
import org.jeecg.modules.mp.nshare.goods.service.INshareDistriShopGoodsService;
import org.jeecg.modules.mp.nshare.goods.vo.NshareDistriShopGoodsPage;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopPromotion;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopGoodsCatService;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopPromotionService;
import org.jeecg.modules.mp.nshare.shop.vo.NshareDistriShopPromotionPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Api(tags = "小程序-近邻分享-社区分享配送店铺商品")
@RestController
@RequestMapping("/mp/api/ns/s/g")
@Slf4j
public class NshareDistriShopGoodsAPIController extends CommonController {
    @Autowired
    private INshareDistriShopGoodsService nshareDistriShopGoodsService;
    @Autowired
    private INshareDistriShopGoodsCatService nshareDistriShopGoodsCatService;
    @Autowired
    private INshareCommAttachService nshareCommAttachService;
    @Autowired
    private INshareDistriShopPromotionService promotionService;

    @ApiOperation(value = "获取店铺商品列表", notes = "可按品类筛选")
    @GetMapping(value = "/{shopid}/goods")
    public Result<?> doGetShopGoods(@PathVariable(name = "shopid") String shopid,
                                    @RequestParam(name = "catid", required = false) String catid,
                                    HttpServletRequest req) {
        List<Map> cats = nshareDistriShopGoodsCatService.selectMapByMainId(shopid);
        if (CollectionUtils.isEmpty(cats)) {
            return Result.ok(null);
        }
        for (Map cat : cats) {
            String cid = cat.get("catId").toString();
            List<Map> list = nshareDistriShopGoodsService.findShopGoodsMap(shopid, cid);
            if (!CollectionUtils.isEmpty(list)) {
                for (Map map : list) {
                    if (map.get("imgs") == null) {
                        continue;
                    }
                    String imgStr = map.get("imgs").toString();
                    String[] imgList = imgStr.split(",");
                    map.put("imgList", imgList);
                }
            }
            cat.put("goods", list);
        }
        return Result.ok(cats);
    }

    @ApiOperation(value = "获取店铺品类信息", notes = "用户登录后，获取店铺商品分类信息用于添加商品")
    @GetMapping(value = "/{shopid}/cats")
    public Result<?> doGetShopGoodsCats(@PathVariable(name = "shopid") String shopid,
                                        HttpServletRequest req) {
        List catList = nshareDistriShopGoodsCatService.selectMapByMainId(shopid);
        return Result.ok(catList);
    }

    @ApiOperation(value = "分页获取店铺商品列表", notes = "可按品类筛选")
    @GetMapping(value = "/p/{pageIndex}/goods")
    public Result<?> doGetShopGoodsPage(@PathVariable(name = "pageIndex") Integer pageIndex,
                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                        @RequestParam(name = "shopId", required = false) String shopId,
                                        @RequestParam(name = "catId", required = false) String catId,
                                        @RequestParam(name = "teamId", required = false) String teamId,
                                        @RequestParam(name = "searchKey", required = false) String searchKey,
                                        @RequestParam(name = "lng", required = false) BigDecimal lng,
                                        @RequestParam(name = "lat", required = false) BigDecimal lat) {
        if ("0".equalsIgnoreCase(catId)) {
            catId = null;
        }
        IPage<Map> pageList = nshareDistriShopGoodsService.loadList4API(shopId,teamId,catId,pageSize,pageIndex,searchKey);
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

    @ApiOperation(value = "获取店铺商品信息", notes = "通过id查询指定商品信息")
    @GetMapping(value = "/{id}")
    public Result<?> doGetShopGoodsById(@PathVariable(name = "id") String id) {
        NshareDistriShopGoods nshareDistriShopGoods = nshareDistriShopGoodsService.getById(id);
        if (nshareDistriShopGoods == null) {
            return Result.error("未找到对应数据");
        }
        NshareDistriShopGoodsPage vo = new NshareDistriShopGoodsPage();
        BeanUtils.copyProperties(nshareDistriShopGoods, vo);

        List<NshareCommAttach> imgs = nshareCommAttachService.selectByBuss("nshare_distri_shop_goods", id, "LOGO");
        List<String> imgList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(imgs)) {
            for (NshareCommAttach img : imgs) {
                imgList.add(img.getAttachPath());
            }
        }
        vo.setImgList(imgList);

        List<NshareCommAttach> dimgs = nshareCommAttachService.selectByBuss("nshare_distri_shop_goods", id, "PIC");
        List<String> dimgList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dimgs)) {
            for (NshareCommAttach img : dimgs) {
                dimgList.add(img.getAttachPath());
            }
        }
        vo.setDimgList(dimgList);

        List<NshareDistriShopPromotion> promos = promotionService.loadPromosByGoodsId(id);
        vo.setPromoList(promos);
        return Result.ok(vo);
    }

    @ApiOperation(value = "添加、更新店铺商品", notes = "用户登录后添加或更新店铺商品信息")
    @PostMapping(value = "/auth/goods")
    public Result<?> doPostShopGoods(@RequestBody NshareDistriShopGoodsPage vo, HttpServletRequest req) {
        NshareDistriShopGoods entity = new NshareDistriShopGoods();

        BeanUtils.copyProperties(vo, entity);
        if (StringUtils.isEmpty(entity.getId())) {
            //同一店铺不允许添加同名商品
            QueryWrapper<NshareDistriShopGoods> queryWrapper = new QueryWrapper<NshareDistriShopGoods>();
            queryWrapper.eq("goods_name", entity.getGoodsName().trim()).eq("shop_id", entity.getShopId());
            NshareDistriShopGoods oldGoods = nshareDistriShopGoodsService.getOne(queryWrapper);
            if (oldGoods != null) {
                return Result.error("商品已存在,不能重复添加");
            }
            //TODO 若是添加商品，自动生成商品编码
            String gCode = UUID.randomUUID().toString().replace("-", "");
            entity.setGoodsCode(gCode);
            entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        nshareDistriShopGoodsService.saveOrUpdate(entity);

        QueryWrapper<NshareDistriShopGoods> queryWrapper = new QueryWrapper<NshareDistriShopGoods>();
        queryWrapper.eq("goods_code", entity.getGoodsCode());
        NshareDistriShopGoods newGoods = nshareDistriShopGoodsService.getOne(queryWrapper);

        NshareDistriShopGoodsPage reObj = new NshareDistriShopGoodsPage();
        //保存图片记录
        if (newGoods != null) {
            List<String> imgList = vo.getImgList();
            //删除原有
            String attachBussTable = "nshare_distri_shop_goods";
            String attachCode = "LOGO";
            if (!CollectionUtils.isEmpty(imgList)) {
                nshareCommAttachService.deleteByBuss(attachBussTable, newGoods.getId(), attachCode);
                Collection atts = new ArrayList();
                for (String img : imgList) {
                    NshareCommAttach att = new NshareCommAttach();
                    att.setAttachCode(attachCode);
                    att.setBussTableName(attachBussTable);
                    att.setBussId(newGoods.getId());
                    att.setAttachPath(img);
                    att.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    atts.add(att);
                }
                nshareCommAttachService.saveBatch(atts);
            }
            BeanUtils.copyProperties(newGoods, reObj);
            reObj.setImgList(imgList);

            List<String> dimgList = vo.getDimgList();
            //删除原有
            attachBussTable = "nshare_distri_shop_goods";
            attachCode = "PIC";
            nshareCommAttachService.deleteByBuss(attachBussTable, newGoods.getId(), attachCode);
            if (!CollectionUtils.isEmpty(dimgList)) {
                Collection atts = new ArrayList();
                for (String img : dimgList) {
                    NshareCommAttach att = new NshareCommAttach();
                    att.setAttachCode(attachCode);
                    att.setBussTableName(attachBussTable);
                    att.setBussId(newGoods.getId());
                    att.setAttachPath(img);
                    att.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    atts.add(att);
                }
                nshareCommAttachService.saveBatch(atts);
            }
            reObj.setDimgList(dimgList);
        }
        return Result.ok(reObj);
    }

    @ApiOperation(value = "删除店铺商品", notes = "用户登录后删除店铺商品信息")
    @DeleteMapping(value = "/auth/goods")
    public Result<?> doDeleteShopGoods(@RequestParam(name = "ids", required = true) String ids) {
        this.nshareDistriShopGoodsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    @ApiOperation(value = "店铺商品上下架", notes = "用户登录后更新店铺商品上下架信息")
    @PostMapping(value = "/auth/{id}")
    public Result<?> doPostUpShopGoodsStatus(@PathVariable(name = "id") String id, @RequestBody Map<String, Object> paramsMap,
                                             HttpServletRequest req) {
        if (paramsMap == null || paramsMap.isEmpty()) {
            return Result.error("缺少必要参数");
        }
        NshareDistriShopGoods goods = nshareDistriShopGoodsService.getById(id);
        goods.setOnSale(Integer.parseInt(paramsMap.get("onSale").toString()));
        nshareDistriShopGoodsService.updateById(goods);
        return Result.ok("操作成功");
    }

    @ApiOperation(value = "添加、更新商品营销活动", notes = "用户登录后添加或更新店铺商品营销活动信息")
    @PostMapping(value = "/auth/promo")
    public Result<?> doPostShopGoodsPromotion(@RequestBody NshareDistriShopPromotionPage promotionPage,
                                              HttpServletRequest req) {
        NshareDistriShopPromotion promo = new NshareDistriShopPromotion();
        BeanUtils.copyProperties(promotionPage, promo);
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        //计算结束时间
        Date endTime = new Date();
        endTime.setTime(promo.getStartTime().getTime()+promo.getProHours() * 60 * 60 * 1000);
        promo.setEndTime(endTime);

        //新增
        if (StringUtils.isEmpty(promo.getId())) {
            //同一店铺不允许添加相同活动
            String st = DateUtils.formatDate(promo.getStartTime(), "yyyy-MM-dd HH:mm:ss");
            String et = DateUtils.formatDate(promo.getEndTime(), "yyyy-MM-dd HH:mm:ss");
            boolean canAdd = promotionService.canAddPromos(promo.getGoodsId(), st, et);

            if (!canAdd) {
                return Result.error("所选时间范围内已有同商品活动");
            }
            promo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            promo.setCreateBy(userId);
        }else{
            if(promo.getAuditStatus()==2){
                return Result.error("已审核通过不能修改");
            }
        }
        NshareDistriShopGoods goods=nshareDistriShopGoodsService.getById(promo.getGoodsId());
        if(goods!=null){
            promo.setNormPrice(goods.getNormPrice());
            promo.setGoodsName(goods.getGoodsName());
            promo.setPriceUnit(goods.getPriceUnit());
            promo.setShopId(goods.getShopId());
        }

        promo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        promo.setUpdateBy(userId);
        promotionService.saveOrUpdate(promo);
        List<NshareDistriShopPromotion> promos=promotionService.loadPromosByGoodsId(promo.getGoodsId().trim());
        return Result.ok(promos);
    }

    @ApiOperation(value = "商品营销活动审核提交", notes = "用户登录后商品营销活动审核提交")
    @PostMapping(value = "/auth/promo/{id}")
    public Result<?> doPostShopGoodsPromotion(@PathVariable(name = "id") String id,
                                              @RequestBody NshareDistriShopPromotionPage promotionPage,
                                              HttpServletRequest req) {
        NshareDistriShopPromotion promo=promotionService.getById(id);
        if(promo==null){
            return Result.error("活动信息不存在");
        }
        promo.setAuditStatus(promotionPage.getAuditStatus());
        promotionService.saveOrUpdate(promo);
        List<NshareDistriShopPromotion> promos=promotionService.loadPromosByGoodsId(promo.getGoodsId());
        return Result.ok(promos);
    }

    @ApiOperation(value = "删除商品营销活动", notes = "用户登录后删除商品营销活动信息")
    @DeleteMapping(value = "/auth/promo/{id}")
    public Result<?> doDeleteShopGoodsPromotion(@PathVariable(name = "id") String id,
                                              HttpServletRequest req) {
        NshareDistriShopPromotion promo=promotionService.getById(id);
        if(promo==null){
            return Result.error("活动信息不存在");
        }
        if(promo.getAuditStatus()!=null&&promo.getAuditStatus().intValue()==2){
            return Result.error("活动已审核通过不能删除");
        }
        promotionService.removeById(id);
        List<NshareDistriShopPromotion> promos=promotionService.loadPromosByGoodsId(promo.getGoodsId());
        return Result.ok(promos);
    }
}