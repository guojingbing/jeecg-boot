package org.jeecg.modules.mp.tlearn.sys.banner.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mp.tlearn.sys.banner.entity.TlBanner;
import org.jeecg.modules.mp.tlearn.sys.banner.entity.TlBannerItem;
import org.jeecg.modules.mp.tlearn.sys.banner.service.ITlBannerItemService;
import org.jeecg.modules.mp.tlearn.sys.banner.service.ITlBannerService;
import org.jeecg.modules.mp.tlearn.sys.banner.vo.TlBannerPage;
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
 * @Description: 轮播
 * @Author:
 * @Date:   2020-01-30
 * @Version: V1.0
 */
@RestController
@RequestMapping("/banner/tlBanner")
@Slf4j
public class TlBannerController {
	@Autowired
	private ITlBannerService tlBannerService;
	@Autowired
	private ITlBannerItemService tlBannerItemService;
	
	/**
	 * 分页列表查询
	 *
	 * @param tlBanner
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TlBanner tlBanner,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TlBanner> queryWrapper = QueryGenerator.initQueryWrapper(tlBanner, req.getParameterMap());
		Page<TlBanner> page = new Page<TlBanner>(pageNo, pageSize);
		IPage<TlBanner> pageList = tlBannerService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param tlBannerPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TlBannerPage tlBannerPage) {
		TlBanner tlBanner = new TlBanner();
		BeanUtils.copyProperties(tlBannerPage, tlBanner);
		tlBannerService.saveMain(tlBanner, tlBannerPage.getTlBannerItemList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param tlBannerPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TlBannerPage tlBannerPage) {
		TlBanner tlBanner = new TlBanner();
		BeanUtils.copyProperties(tlBannerPage, tlBanner);
		TlBanner tlBannerEntity = tlBannerService.getById(tlBanner.getId());
		if(tlBannerEntity==null) {
			return Result.error("未找到对应数据");
		}
		tlBannerService.updateMain(tlBanner, tlBannerPage.getTlBannerItemList());
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
		tlBannerService.delMain(id);
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
		this.tlBannerService.delBatchMain(Arrays.asList(ids.split(",")));
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
		TlBanner tlBanner = tlBannerService.getById(id);
		if(tlBanner==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(tlBanner);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryTlBannerItemByMainId")
	public Result<?> queryTlBannerItemListByMainId(@RequestParam(name="id",required=true) String id) {
		List<TlBannerItem> tlBannerItemList = tlBannerItemService.selectByMainId(id);
		return Result.ok(tlBannerItemList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tlBanner
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TlBanner tlBanner) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<TlBanner> queryWrapper = QueryGenerator.initQueryWrapper(tlBanner, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<TlBanner> queryList = tlBannerService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<TlBanner> tlBannerList = new ArrayList<TlBanner>();
      if(oConvertUtils.isEmpty(selections)) {
          tlBannerList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          tlBannerList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<TlBannerPage> pageList = new ArrayList<TlBannerPage>();
      for (TlBanner main : tlBannerList) {
          TlBannerPage vo = new TlBannerPage();
          BeanUtils.copyProperties(main, vo);
          List<TlBannerItem> tlBannerItemList = tlBannerItemService.selectByMainId(main.getId());
          vo.setTlBannerItemList(tlBannerItemList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "轮播列表");
      mv.addObject(NormalExcelConstants.CLASS, TlBannerPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("轮播数据", "导出人:"+sysUser.getRealname(), "轮播"));
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
              List<TlBannerPage> list = ExcelImportUtil.importExcel(file.getInputStream(), TlBannerPage.class, params);
              for (TlBannerPage page : list) {
                  TlBanner po = new TlBanner();
                  BeanUtils.copyProperties(page, po);
                  tlBannerService.saveMain(po, page.getTlBannerItemList());
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
