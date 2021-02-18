package org.jeecg.modules.mp.nshare.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoods;
import org.jeecg.modules.mp.nshare.goods.service.INshareDistriShopGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 社区分享配送店铺商品
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@RestController
@RequestMapping("/goods/nshareDistriShopGoods")
@Slf4j
public class NshareDistriShopGoodsController extends JeecgController<NshareDistriShopGoods, INshareDistriShopGoodsService> {
	@Autowired
	private INshareDistriShopGoodsService nshareDistriShopGoodsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param nshareDistriShopGoods
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(NshareDistriShopGoods nshareDistriShopGoods,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<NshareDistriShopGoods> queryWrapper = QueryGenerator.initQueryWrapper(nshareDistriShopGoods, req.getParameterMap());
		Page<NshareDistriShopGoods> page = new Page<NshareDistriShopGoods>(pageNo, pageSize);
		IPage<NshareDistriShopGoods> pageList = nshareDistriShopGoodsService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param nshareDistriShopGoods
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody NshareDistriShopGoods nshareDistriShopGoods) {
		nshareDistriShopGoodsService.save(nshareDistriShopGoods);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param nshareDistriShopGoods
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody NshareDistriShopGoods nshareDistriShopGoods) {
		nshareDistriShopGoodsService.updateById(nshareDistriShopGoods);
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
		nshareDistriShopGoodsService.removeById(id);
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
		this.nshareDistriShopGoodsService.removeByIds(Arrays.asList(ids.split(",")));
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
		NshareDistriShopGoods nshareDistriShopGoods = nshareDistriShopGoodsService.getById(id);
		if(nshareDistriShopGoods==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(nshareDistriShopGoods);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param nshareDistriShopGoods
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, NshareDistriShopGoods nshareDistriShopGoods) {
        return super.exportXls(request, nshareDistriShopGoods, NshareDistriShopGoods.class, "社区分享配送店铺商品");
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
        return super.importExcel(request, response, NshareDistriShopGoods.class);
    }

}
