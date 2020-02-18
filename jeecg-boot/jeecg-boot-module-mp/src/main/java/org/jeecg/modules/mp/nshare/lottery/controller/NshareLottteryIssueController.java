package org.jeecg.modules.mp.nshare.lottery.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssue;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssuePrize;
import org.jeecg.modules.mp.nshare.lottery.service.INshareLottteryIssuePrizeService;
import org.jeecg.modules.mp.nshare.lottery.service.INshareLottteryIssueService;
import org.jeecg.modules.mp.nshare.lottery.vo.NshareLottteryIssuePage;
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
 * @Description: 社区分享彩票期数
 * @Author:
 * @Date:   2020-02-16
 * @Version: V1.0
 */
@RestController
@RequestMapping("/nshare.lottery/nshareLottteryIssue")
@Slf4j
public class NshareLottteryIssueController {
	@Autowired
	private INshareLottteryIssueService nshareLottteryIssueService;
	@Autowired
	private INshareLottteryIssuePrizeService nshareLottteryIssuePrizeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param nshareLottteryIssue
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(NshareLottteryIssue nshareLottteryIssue,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<NshareLottteryIssue> queryWrapper = QueryGenerator.initQueryWrapper(nshareLottteryIssue, req.getParameterMap());
		Page<NshareLottteryIssue> page = new Page<NshareLottteryIssue>(pageNo, pageSize);
		IPage<NshareLottteryIssue> pageList = nshareLottteryIssueService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param nshareLottteryIssuePage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody NshareLottteryIssuePage nshareLottteryIssuePage) {
		NshareLottteryIssue nshareLottteryIssue = new NshareLottteryIssue();
		BeanUtils.copyProperties(nshareLottteryIssuePage, nshareLottteryIssue);
		nshareLottteryIssueService.saveMain(nshareLottteryIssue, nshareLottteryIssuePage.getNshareLottteryIssuePrizeList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param nshareLottteryIssuePage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody NshareLottteryIssuePage nshareLottteryIssuePage) {
		NshareLottteryIssue nshareLottteryIssue = new NshareLottteryIssue();
		BeanUtils.copyProperties(nshareLottteryIssuePage, nshareLottteryIssue);
		NshareLottteryIssue nshareLottteryIssueEntity = nshareLottteryIssueService.getById(nshareLottteryIssue.getId());
		if(nshareLottteryIssueEntity==null) {
			return Result.error("未找到对应数据");
		}
		nshareLottteryIssueService.updateMain(nshareLottteryIssue, nshareLottteryIssuePage.getNshareLottteryIssuePrizeList());
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
		nshareLottteryIssueService.delMain(id);
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
		this.nshareLottteryIssueService.delBatchMain(Arrays.asList(ids.split(",")));
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
		NshareLottteryIssue nshareLottteryIssue = nshareLottteryIssueService.getById(id);
		if(nshareLottteryIssue==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(nshareLottteryIssue);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryNshareLottteryIssuePrizeByMainId")
	public Result<?> queryNshareLottteryIssuePrizeListByMainId(@RequestParam(name="id",required=true) String id) {
		List<NshareLottteryIssuePrize> nshareLottteryIssuePrizeList = nshareLottteryIssuePrizeService.selectByMainId(id);
		return Result.ok(nshareLottteryIssuePrizeList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param nshareLottteryIssue
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, NshareLottteryIssue nshareLottteryIssue) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<NshareLottteryIssue> queryWrapper = QueryGenerator.initQueryWrapper(nshareLottteryIssue, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<NshareLottteryIssue> queryList = nshareLottteryIssueService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<NshareLottteryIssue> nshareLottteryIssueList = new ArrayList<NshareLottteryIssue>();
      if(oConvertUtils.isEmpty(selections)) {
          nshareLottteryIssueList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          nshareLottteryIssueList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<NshareLottteryIssuePage> pageList = new ArrayList<NshareLottteryIssuePage>();
      for (NshareLottteryIssue main : nshareLottteryIssueList) {
          NshareLottteryIssuePage vo = new NshareLottteryIssuePage();
          BeanUtils.copyProperties(main, vo);
          List<NshareLottteryIssuePrize> nshareLottteryIssuePrizeList = nshareLottteryIssuePrizeService.selectByMainId(main.getId());
          vo.setNshareLottteryIssuePrizeList(nshareLottteryIssuePrizeList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "社区分享彩票期数列表");
      mv.addObject(NormalExcelConstants.CLASS, NshareLottteryIssuePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("社区分享彩票期数数据", "导出人:"+sysUser.getRealname(), "社区分享彩票期数"));
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
              List<NshareLottteryIssuePage> list = ExcelImportUtil.importExcel(file.getInputStream(), NshareLottteryIssuePage.class, params);
              for (NshareLottteryIssuePage page : list) {
                  NshareLottteryIssue po = new NshareLottteryIssue();
                  BeanUtils.copyProperties(page, po);
                  nshareLottteryIssueService.saveMain(po, page.getNshareLottteryIssuePrizeList());
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
