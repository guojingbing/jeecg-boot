package org.jeecg.modules.mp.nshare.shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShop;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopAdmin;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopStation;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopAdminService;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopService;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopStationService;
import org.jeecg.modules.mp.nshare.shop.vo.NshareDistriShopPage;
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
 * @Description: 社区分享配送店铺
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@RestController
@RequestMapping("/nshare.shop/nshareDistriShop")
@Slf4j
public class NshareDistriShopController {
	@Autowired
	private INshareDistriShopService nshareDistriShopService;
	@Autowired
	private INshareDistriShopAdminService nshareDistriShopAdminService;
	@Autowired
	private INshareDistriShopStationService nshareDistriShopStationService;
	
	/**
	 * 分页列表查询
	 *
	 * @param nshareDistriShop
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(NshareDistriShop nshareDistriShop,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<NshareDistriShop> queryWrapper = QueryGenerator.initQueryWrapper(nshareDistriShop, req.getParameterMap());
		Page<NshareDistriShop> page = new Page<NshareDistriShop>(pageNo, pageSize);
		IPage<NshareDistriShop> pageList = nshareDistriShopService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param nshareDistriShopPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody NshareDistriShopPage nshareDistriShopPage) {
		NshareDistriShop nshareDistriShop = new NshareDistriShop();
		BeanUtils.copyProperties(nshareDistriShopPage, nshareDistriShop);
		nshareDistriShopService.saveMain(nshareDistriShop, nshareDistriShopPage.getNshareDistriShopAdminList(),nshareDistriShopPage.getNshareDistriShopStationList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param nshareDistriShopPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody NshareDistriShopPage nshareDistriShopPage) {
		NshareDistriShop nshareDistriShop = new NshareDistriShop();
		BeanUtils.copyProperties(nshareDistriShopPage, nshareDistriShop);
		NshareDistriShop nshareDistriShopEntity = nshareDistriShopService.getById(nshareDistriShop.getId());
		if(nshareDistriShopEntity==null) {
			return Result.error("未找到对应数据");
		}
		nshareDistriShopService.updateMain(nshareDistriShop, nshareDistriShopPage.getNshareDistriShopAdminList(),nshareDistriShopPage.getNshareDistriShopStationList());
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
		nshareDistriShopService.delMain(id);
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
		this.nshareDistriShopService.delBatchMain(Arrays.asList(ids.split(",")));
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
		NshareDistriShop nshareDistriShop = nshareDistriShopService.getById(id);
		if(nshareDistriShop==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(nshareDistriShop);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryNshareDistriShopAdminByMainId")
	public Result<?> queryNshareDistriShopAdminListByMainId(@RequestParam(name="id",required=true) String id) {
		List<NshareDistriShopAdmin> nshareDistriShopAdminList = nshareDistriShopAdminService.selectByMainId(id);
		return Result.ok(nshareDistriShopAdminList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryNshareDistriShopStationByMainId")
	public Result<?> queryNshareDistriShopStationListByMainId(@RequestParam(name="id",required=true) String id) {
		List<NshareDistriShopStation> nshareDistriShopStationList = nshareDistriShopStationService.selectByMainId(id);
		return Result.ok(nshareDistriShopStationList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param nshareDistriShop
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, NshareDistriShop nshareDistriShop) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<NshareDistriShop> queryWrapper = QueryGenerator.initQueryWrapper(nshareDistriShop, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<NshareDistriShop> queryList = nshareDistriShopService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<NshareDistriShop> nshareDistriShopList = new ArrayList<NshareDistriShop>();
      if(oConvertUtils.isEmpty(selections)) {
          nshareDistriShopList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          nshareDistriShopList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<NshareDistriShopPage> pageList = new ArrayList<NshareDistriShopPage>();
      for (NshareDistriShop main : nshareDistriShopList) {
          NshareDistriShopPage vo = new NshareDistriShopPage();
          BeanUtils.copyProperties(main, vo);
          List<NshareDistriShopAdmin> nshareDistriShopAdminList = nshareDistriShopAdminService.selectByMainId(main.getId());
          vo.setNshareDistriShopAdminList(nshareDistriShopAdminList);
          List<NshareDistriShopStation> nshareDistriShopStationList = nshareDistriShopStationService.selectByMainId(main.getId());
          vo.setNshareDistriShopStationList(nshareDistriShopStationList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "社区分享配送店铺列表");
      mv.addObject(NormalExcelConstants.CLASS, NshareDistriShopPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("社区分享配送店铺数据", "导出人:"+sysUser.getRealname(), "社区分享配送店铺"));
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
              List<NshareDistriShopPage> list = ExcelImportUtil.importExcel(file.getInputStream(), NshareDistriShopPage.class, params);
              for (NshareDistriShopPage page : list) {
                  NshareDistriShop po = new NshareDistriShop();
                  BeanUtils.copyProperties(page, po);
                  nshareDistriShopService.saveMain(po, page.getNshareDistriShopAdminList(),page.getNshareDistriShopStationList());
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
