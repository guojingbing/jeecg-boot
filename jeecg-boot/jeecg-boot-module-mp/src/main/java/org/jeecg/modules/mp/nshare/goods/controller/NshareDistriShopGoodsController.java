package org.jeecg.modules.mp.nshare.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoods;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoodsDaily;
import org.jeecg.modules.mp.nshare.goods.service.INshareDistriShopGoodsDailyService;
import org.jeecg.modules.mp.nshare.goods.service.INshareDistriShopGoodsService;
import org.jeecg.modules.mp.nshare.goods.vo.NshareDistriShopGoodsPage;
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
 * @Description: 社区分享配送店铺商品
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@RestController
@RequestMapping("/nshare.goods/nshareDistriShopGoods")
@Slf4j
public class NshareDistriShopGoodsController {
	@Autowired
	private INshareDistriShopGoodsService nshareDistriShopGoodsService;
	@Autowired
	private INshareDistriShopGoodsDailyService nshareDistriShopGoodsDailyService;
	
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
	 * @param nshareDistriShopGoodsPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody NshareDistriShopGoodsPage nshareDistriShopGoodsPage) {
		NshareDistriShopGoods nshareDistriShopGoods = new NshareDistriShopGoods();
		BeanUtils.copyProperties(nshareDistriShopGoodsPage, nshareDistriShopGoods);
		nshareDistriShopGoodsService.saveMain(nshareDistriShopGoods, nshareDistriShopGoodsPage.getNshareDistriShopGoodsDailyList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param nshareDistriShopGoodsPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody NshareDistriShopGoodsPage nshareDistriShopGoodsPage) {
		NshareDistriShopGoods nshareDistriShopGoods = new NshareDistriShopGoods();
		BeanUtils.copyProperties(nshareDistriShopGoodsPage, nshareDistriShopGoods);
		NshareDistriShopGoods nshareDistriShopGoodsEntity = nshareDistriShopGoodsService.getById(nshareDistriShopGoods.getId());
		if(nshareDistriShopGoodsEntity==null) {
			return Result.error("未找到对应数据");
		}
		nshareDistriShopGoodsService.updateMain(nshareDistriShopGoods, nshareDistriShopGoodsPage.getNshareDistriShopGoodsDailyList());
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
		nshareDistriShopGoodsService.delMain(id);
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
		this.nshareDistriShopGoodsService.delBatchMain(Arrays.asList(ids.split(",")));
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
		NshareDistriShopGoods nshareDistriShopGoods = nshareDistriShopGoodsService.getById(id);
		if(nshareDistriShopGoods==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(nshareDistriShopGoods);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryNshareDistriShopGoodsDailyByMainId")
	public Result<?> queryNshareDistriShopGoodsDailyListByMainId(@RequestParam(name="id",required=true) String id) {
		List<NshareDistriShopGoodsDaily> nshareDistriShopGoodsDailyList = nshareDistriShopGoodsDailyService.selectByMainId(id);
		return Result.ok(nshareDistriShopGoodsDailyList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param nshareDistriShopGoods
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, NshareDistriShopGoods nshareDistriShopGoods) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<NshareDistriShopGoods> queryWrapper = QueryGenerator.initQueryWrapper(nshareDistriShopGoods, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<NshareDistriShopGoods> queryList = nshareDistriShopGoodsService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<NshareDistriShopGoods> nshareDistriShopGoodsList = new ArrayList<NshareDistriShopGoods>();
      if(oConvertUtils.isEmpty(selections)) {
          nshareDistriShopGoodsList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          nshareDistriShopGoodsList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<NshareDistriShopGoodsPage> pageList = new ArrayList<NshareDistriShopGoodsPage>();
      for (NshareDistriShopGoods main : nshareDistriShopGoodsList) {
          NshareDistriShopGoodsPage vo = new NshareDistriShopGoodsPage();
          BeanUtils.copyProperties(main, vo);
          List<NshareDistriShopGoodsDaily> nshareDistriShopGoodsDailyList = nshareDistriShopGoodsDailyService.selectByMainId(main.getId());
          vo.setNshareDistriShopGoodsDailyList(nshareDistriShopGoodsDailyList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "社区分享配送店铺商品列表");
      mv.addObject(NormalExcelConstants.CLASS, NshareDistriShopGoodsPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("社区分享配送店铺商品数据", "导出人:"+sysUser.getRealname(), "社区分享配送店铺商品"));
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
              List<NshareDistriShopGoodsPage> list = ExcelImportUtil.importExcel(file.getInputStream(), NshareDistriShopGoodsPage.class, params);
              for (NshareDistriShopGoodsPage page : list) {
                  NshareDistriShopGoods po = new NshareDistriShopGoods();
                  BeanUtils.copyProperties(page, po);
                  nshareDistriShopGoodsService.saveMain(po, page.getNshareDistriShopGoodsDailyList());
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
