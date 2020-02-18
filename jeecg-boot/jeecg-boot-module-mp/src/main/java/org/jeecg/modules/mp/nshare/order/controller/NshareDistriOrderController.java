package org.jeecg.modules.mp.nshare.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrder;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrderGoods;
import org.jeecg.modules.mp.nshare.order.service.INshareDistriOrderGoodsService;
import org.jeecg.modules.mp.nshare.order.service.INshareDistriOrderService;
import org.jeecg.modules.mp.nshare.order.vo.NshareDistriOrderPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 /**
 * @Description: 社区分享配送订单
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@RestController
@RequestMapping("/nshare.order/nshareDistriOrder")
@Slf4j
public class NshareDistriOrderController {
	@Autowired
	private INshareDistriOrderService nshareDistriOrderService;
	@Autowired
	private INshareDistriOrderGoodsService nshareDistriOrderGoodsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param nshareDistriOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(NshareDistriOrder nshareDistriOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<NshareDistriOrder> queryWrapper = QueryGenerator.initQueryWrapper(nshareDistriOrder, req.getParameterMap());
		Page<NshareDistriOrder> page = new Page<NshareDistriOrder>(pageNo, pageSize);
		IPage<NshareDistriOrder> pageList = nshareDistriOrderService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param nshareDistriOrderPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody NshareDistriOrderPage nshareDistriOrderPage) {
		NshareDistriOrder nshareDistriOrder = new NshareDistriOrder();
		BeanUtils.copyProperties(nshareDistriOrderPage, nshareDistriOrder);
		nshareDistriOrderService.saveMain(nshareDistriOrder, nshareDistriOrderPage.getNshareDistriOrderGoodsList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param nshareDistriOrderPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody NshareDistriOrderPage nshareDistriOrderPage) {
		NshareDistriOrder nshareDistriOrder = new NshareDistriOrder();
		BeanUtils.copyProperties(nshareDistriOrderPage, nshareDistriOrder);
		NshareDistriOrder nshareDistriOrderEntity = nshareDistriOrderService.getById(nshareDistriOrder.getId());
		if(nshareDistriOrderEntity==null) {
			return Result.error("未找到对应数据");
		}
		nshareDistriOrderService.updateMain(nshareDistriOrder, nshareDistriOrderPage.getNshareDistriOrderGoodsList());
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
		nshareDistriOrderService.delMain(id);
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
		this.nshareDistriOrderService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		NshareDistriOrder nshareDistriOrder = nshareDistriOrderService.getById(id);
		if(nshareDistriOrder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(nshareDistriOrder);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryNshareDistriOrderGoodsByMainId")
	public Result<?> queryNshareDistriOrderGoodsListByMainId(@RequestParam(name="id",required=true) String id) {
		List<NshareDistriOrderGoods> nshareDistriOrderGoodsList = nshareDistriOrderGoodsService.selectByMainId(id);
		return Result.ok(nshareDistriOrderGoodsList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param nshareDistriOrder
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, NshareDistriOrder nshareDistriOrder) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<NshareDistriOrder> queryWrapper = QueryGenerator.initQueryWrapper(nshareDistriOrder, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<NshareDistriOrder> queryList = nshareDistriOrderService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<NshareDistriOrder> nshareDistriOrderList = new ArrayList<NshareDistriOrder>();
      if(oConvertUtils.isEmpty(selections)) {
          nshareDistriOrderList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          nshareDistriOrderList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<NshareDistriOrderPage> pageList = new ArrayList<NshareDistriOrderPage>();
      for (NshareDistriOrder main : nshareDistriOrderList) {
          NshareDistriOrderPage vo = new NshareDistriOrderPage();
          BeanUtils.copyProperties(main, vo);
          List<NshareDistriOrderGoods> nshareDistriOrderGoodsList = nshareDistriOrderGoodsService.selectByMainId(main.getId());
          vo.setNshareDistriOrderGoodsList(nshareDistriOrderGoodsList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "社区分享配送订单列表");
      mv.addObject(NormalExcelConstants.CLASS, NshareDistriOrderPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("社区分享配送订单数据", "导出人:"+sysUser.getRealname(), "社区分享配送订单"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<NshareDistriOrderPage> list = ExcelImportUtil.importExcel(file.getInputStream(), NshareDistriOrderPage.class, params);
              for (NshareDistriOrderPage page : list) {
                  NshareDistriOrder po = new NshareDistriOrder();
                  BeanUtils.copyProperties(page, po);
                  nshareDistriOrderService.saveMain(po, page.getNshareDistriOrderGoodsList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
    }

}
