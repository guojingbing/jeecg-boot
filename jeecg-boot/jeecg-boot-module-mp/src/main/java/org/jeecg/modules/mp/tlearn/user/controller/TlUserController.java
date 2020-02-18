package org.jeecg.modules.mp.tlearn.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mp.tlearn.user.entity.TlUser;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubIdiom;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubPoetry;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubTag;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserService;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserSubIdiomService;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserSubPoetryService;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserSubTagService;
import org.jeecg.modules.mp.tlearn.user.vo.TlUserPage;
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
 * @Description: 小程序用户
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
@RestController
@RequestMapping("/user/tlUser")
@Slf4j
public class TlUserController {
	@Autowired
	private ITlUserService tlUserService;
	@Autowired
	private ITlUserSubPoetryService tlUserSubPoetryService;
	@Autowired
	private ITlUserSubIdiomService tlUserSubIdiomService;
	@Autowired
	private ITlUserSubTagService tlUserSubTagService;
	
	/**
	 * 分页列表查询
	 *
	 * @param tlUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TlUser tlUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TlUser> queryWrapper = QueryGenerator.initQueryWrapper(tlUser, req.getParameterMap());
		Page<TlUser> page = new Page<TlUser>(pageNo, pageSize);
		IPage<TlUser> pageList = tlUserService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param tlUserPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TlUserPage tlUserPage) {
		TlUser tlUser = new TlUser();
		BeanUtils.copyProperties(tlUserPage, tlUser);
		tlUserService.saveMain(tlUser, tlUserPage.getTlUserSubPoetryList(),tlUserPage.getTlUserSubIdiomList(),tlUserPage.getTlUserSubTagList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param tlUserPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TlUserPage tlUserPage) {
		TlUser tlUser = new TlUser();
		BeanUtils.copyProperties(tlUserPage, tlUser);
		TlUser tlUserEntity = tlUserService.getById(tlUser.getId());
		if(tlUserEntity==null) {
			return Result.error("未找到对应数据");
		}
		tlUserService.updateMain(tlUser, tlUserPage.getTlUserSubPoetryList(),tlUserPage.getTlUserSubIdiomList(),tlUserPage.getTlUserSubTagList());
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
		tlUserService.delMain(id);
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
		this.tlUserService.delBatchMain(Arrays.asList(ids.split(",")));
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
		TlUser tlUser = tlUserService.getById(id);
		if(tlUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(tlUser);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryTlUserSubPoetryByMainId")
	public Result<?> queryTlUserSubPoetryListByMainId(@RequestParam(name="id",required=true) String id) {
		List<TlUserSubPoetry> tlUserSubPoetryList = tlUserSubPoetryService.selectByMainId(id);
		return Result.ok(tlUserSubPoetryList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryTlUserSubIdiomByMainId")
	public Result<?> queryTlUserSubIdiomListByMainId(@RequestParam(name="id",required=true) String id) {
		List<TlUserSubIdiom> tlUserSubIdiomList = tlUserSubIdiomService.selectByMainId(id);
		return Result.ok(tlUserSubIdiomList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryTlUserSubTagByMainId")
	public Result<?> queryTlUserSubTagListByMainId(@RequestParam(name="id",required=true) String id) {
		List<TlUserSubTag> tlUserSubTagList = tlUserSubTagService.selectByMainId(id);
		return Result.ok(tlUserSubTagList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tlUser
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TlUser tlUser) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<TlUser> queryWrapper = QueryGenerator.initQueryWrapper(tlUser, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<TlUser> queryList = tlUserService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<TlUser> tlUserList = new ArrayList<TlUser>();
      if(oConvertUtils.isEmpty(selections)) {
          tlUserList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          tlUserList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<TlUserPage> pageList = new ArrayList<TlUserPage>();
      for (TlUser main : tlUserList) {
          TlUserPage vo = new TlUserPage();
          BeanUtils.copyProperties(main, vo);
          List<TlUserSubPoetry> tlUserSubPoetryList = tlUserSubPoetryService.selectByMainId(main.getId());
          vo.setTlUserSubPoetryList(tlUserSubPoetryList);
          List<TlUserSubIdiom> tlUserSubIdiomList = tlUserSubIdiomService.selectByMainId(main.getId());
          vo.setTlUserSubIdiomList(tlUserSubIdiomList);
          List<TlUserSubTag> tlUserSubTagList = tlUserSubTagService.selectByMainId(main.getId());
          vo.setTlUserSubTagList(tlUserSubTagList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "小程序用户列表");
      mv.addObject(NormalExcelConstants.CLASS, TlUserPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("小程序用户数据", "导出人:"+sysUser.getRealname(), "小程序用户"));
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
              List<TlUserPage> list = ExcelImportUtil.importExcel(file.getInputStream(), TlUserPage.class, params);
              for (TlUserPage page : list) {
                  TlUser po = new TlUser();
                  BeanUtils.copyProperties(page, po);
                  tlUserService.saveMain(po, page.getTlUserSubPoetryList(),page.getTlUserSubIdiomList(),page.getTlUserSubTagList());
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
