package org.jeecg.modules.mp.nshare.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.controller.CommonController;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.QRcodeUtil;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrder;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrderGoods;
import org.jeecg.modules.mp.nshare.order.service.INshareDistriOrderGoodsService;
import org.jeecg.modules.mp.nshare.order.service.INshareDistriOrderService;
import org.jeecg.modules.mp.nshare.order.vo.NshareDistriOrderPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = "小程序-近邻分享-社区分享配送订单")
@RestController
@RequestMapping("/mp/api/ns/o")
@Slf4j
public class NshareDistriOrderAPIController extends CommonController {
    @Autowired
    private INshareDistriOrderService nshareDistriOrderService;
    @Autowired
    private INshareDistriOrderGoodsService nshareDistriOrderGoodsService;

    @ApiOperation(value = "获取订单列表", notes = "用户登录查看订单,店铺用户可查看店铺订单")
    @GetMapping(value = "/auth/s/{pageIndex}/orders")
    public Result<?> doGetShopOrders(
            @PathVariable(name = "pageIndex") Integer pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "shopid", required = false) String shopid,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "searchKey", required = false) String searchKey,
            HttpServletRequest req) {
        if ("-1".equalsIgnoreCase(status)) {
            status = null;
        }
        IPage<Map> pageList = nshareDistriOrderService.loadList4API(pageSize, pageIndex, shopid, null, null, status, searchKey);
        if (pageList != null) {
            for (Map m : pageList.getRecords()) {
                String oid = m.get("id").toString();
                List<NshareDistriOrderGoods> orderGoods = nshareDistriOrderGoodsService.selectByMainId(oid);
                m.put("goods", orderGoods);
            }
        }
        return Result.ok(pageList);
    }

    @ApiOperation(value = "获取用户订单列表", notes = "用户登录查看订单,用户可查看自己的订单")
    @GetMapping(value = "/auth/u/{pageIndex}/orders")
    public Result<?> doGetShopOrders4User(
            @PathVariable(name = "pageIndex") Integer pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "searchKey", required = false) String searchKey,
            HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        if ("-1".equalsIgnoreCase(status)) {
            status = null;
        }
        IPage<Map> pageList = nshareDistriOrderService.loadList4API(pageSize, pageIndex, null, userId, null, status, searchKey);
        if (pageList != null) {
            for (Map m : pageList.getRecords()) {
                if (m.get("imgs") == null) {
                    continue;
                }
                String imgStr = m.get("imgs").toString();
                String[] imgList = imgStr.split(",");
                m.remove("imgs");
                m.put("imgList", imgList);

                String oid = m.get("id").toString();
                List<Map> orderGoods = nshareDistriOrderGoodsService.selectByOrderId(oid);
                for (Map om : orderGoods) {
                    if (om.get("imgs") == null) {
                        continue;
                    }
                    String imgStr1 = om.get("imgs").toString();
                    String[] imgList1 = imgStr1.split(",");
                    om.remove("imgs");
                    om.put("imgList", imgList1);
                }
                m.put("goods", orderGoods);
            }
        }
        return Result.ok(pageList);
    }

    @ApiOperation(value = "获取社群推广订单列表", notes = "用户登录查看社群推广订单")
    @GetMapping(value = "/auth/t/{pageIndex}/orders")
    public Result<?> doGetTeamOrders(
            @PathVariable(name = "pageIndex") Integer pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "teamId") String teamId,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "searchKey", required = false) String searchKey,
            HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        if ("-1".equalsIgnoreCase(status)) {
            status = null;
        }
        IPage<Map> pageList = nshareDistriOrderService.loadList4API(pageSize, pageIndex, null, null, teamId, status, searchKey);
        if (pageList != null) {
            for (Map m : pageList.getRecords()) {
                if (m.get("imgs") == null) {
                    continue;
                }
                String imgStr = m.get("imgs").toString();
                String[] imgList = imgStr.split(",");
                m.remove("imgs");
                m.put("imgList", imgList);

                String oid = m.get("id").toString();
                List<Map> orderGoods = nshareDistriOrderGoodsService.selectByOrderId(oid);
                for (Map om : orderGoods) {
                    if (om.get("imgs") == null) {
                        continue;
                    }
                    String imgStr1 = om.get("imgs").toString();
                    String[] imgList1 = imgStr1.split(",");
                    om.remove("imgs");
                    om.put("imgList", imgList1);
                }
                m.put("goods", orderGoods);
            }
        }
        return Result.ok(pageList);
    }

    @ApiOperation(value = "获取订单详情", notes = "用户获取订单详情")
    @GetMapping(value = "/auth/{id}")
    public Result<?> doGetShopOrders(@PathVariable(name = "id") String id, HttpServletRequest req) {
        NshareDistriOrder order = nshareDistriOrderService.getById(id);
        return Result.ok(order);
    }

    public Date getPickTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar cld = Calendar.getInstance(Locale.CHINA);
        cld.setFirstDayOfWeek(Calendar.MONDAY);//以周一为首日
        cld.setTimeInMillis(System.currentTimeMillis());//当前时间

        cld.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);//周三
        if(cld.getTime().getTime()>System.currentTimeMillis()){
            return cld.getTime();
        }

        cld.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);//周六
        return cld.getTime();
    }

    @ApiOperation(value = "提交订单", notes = "用户提交订单")
    @PostMapping(value = "/auth/order")
    public Result<?> doPostOrder(@RequestBody NshareDistriOrderPage nshareDistriOrderPage, HttpServletRequest req) {
        String token = req.getHeader(X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        nshareDistriOrderPage.setPickTime(getPickTime());
        nshareDistriOrderPage.setCreateBy(userId);
        nshareDistriOrderPage.setCreateTime(new Timestamp(System.currentTimeMillis()));
        nshareDistriOrderPage.setOrderDate(new Date(System.currentTimeMillis()));
        //TODO 生成取货码
        //查询出当天所有取货码，去重
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());
        List<NshareDistriOrder> orders = nshareDistriOrderService.selectOrderByDate(dateStr);
        Map codeMap = new HashMap();
        if (!CollectionUtils.isEmpty(orders)) {
            for (NshareDistriOrder order : orders) {
                codeMap.put(order.getPickCode(), order.getId());
            }
        }
        String pickCode = String.format("%04d", new Random().nextInt(9999));
        while (!codeMap.isEmpty() && codeMap.containsKey(pickCode)) {
            pickCode = String.format("%04d", new Random().nextInt(9999));
        }
        nshareDistriOrderPage.setPickCode(pickCode);

        NshareDistriOrder nshareDistriOrder = new NshareDistriOrder();
        BeanUtils.copyProperties(nshareDistriOrderPage, nshareDistriOrder);
        nshareDistriOrderService.saveMain(nshareDistriOrder, nshareDistriOrderPage.getGoodsList());

        //生成提货码二维码
        String ctxPath = super.uploadpath;
        String fileName = null;
        String bizPath = "images/qrcode";
        String nowday = (new SimpleDateFormat("yyyyMMdd")).format(nshareDistriOrder.getPickTime());
        File file = new File(ctxPath + File.separator + bizPath + File.separator + nowday);
        if (!file.exists()) {
            file.mkdirs();
        }
        QRcodeUtil.encode(pickCode, 300, 300, ctxPath+"/images/sys/logo.jpg", file.getAbsolutePath()+"/"+pickCode+".png");
        return Result.ok("操作成功");
    }

    @ApiOperation(value = "变更订单状态", notes = "变更订单状态")
    @PostMapping(value = "/auth/{id}/status")
    public Result<?> doPostUpOrderStatus(@PathVariable(name = "id") String id, @RequestBody Map params) {
        if (params == null || params.isEmpty()) {
            return Result.error("缺少必要参数");
        }
        NshareDistriOrder order = nshareDistriOrderService.getById(id);
        order.setOrderStatus(params.get("status").toString());
        order.setConfirmTime(new Timestamp(System.currentTimeMillis()));
        nshareDistriOrderService.updateById(order);
        return Result.ok("操作成功");
    }

    @ApiOperation(value = "批量删除订单", notes = "批量删除订单")
    @DeleteMapping(value = "/auth/orders")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.nshareDistriOrderService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.ok("订单删除成功");
    }

    @ApiOperation(value = "获取商品订购信息", notes = "店铺用户可查看店铺每日商品订购信息,可按取货日期筛选")
    @GetMapping(value = "/auth/goods")
    public Result<?> doGetSoldGoods(
            @RequestParam(name = "shopid", required = false) String shopid,
            @RequestParam(name = "pickDate", required = false) String pickDate,
            @RequestParam(name = "searchKey", required = false) String searchKey,
            HttpServletRequest req) {
        IPage<Map> pageList = nshareDistriOrderGoodsService.loadSoldList4API(Integer.MAX_VALUE, 1, shopid, pickDate, searchKey);
        if (pageList != null) {
            for (Map m : pageList.getRecords()) {
                if (m.get("imgs") == null) {
                    continue;
                }
                String imgStr = m.get("imgs").toString();
                String[] imgList = imgStr.split(",");
                m.remove("imgs");
                m.put("imgList", imgList);
            }
        }
        return Result.ok(pageList);
    }
}
