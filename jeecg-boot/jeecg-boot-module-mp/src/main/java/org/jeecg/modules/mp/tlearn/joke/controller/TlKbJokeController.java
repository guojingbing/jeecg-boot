package org.jeecg.modules.mp.tlearn.joke.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJoke;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJokeThumbs;
import org.jeecg.modules.mp.tlearn.joke.service.ITlKbJokeService;
import org.jeecg.modules.mp.tlearn.joke.service.ITlKbJokeThumbsService;
import org.jeecg.modules.mp.tlearn.joke.vo.TlKbJokePage;
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
 * @Description: 笑话段子
 * @Author:
 * @Date:   2020-02-09
 * @Version: V1.0
 */
@RestController
@RequestMapping("/joke/tlKbJoke")
@Slf4j
public class TlKbJokeController {
	@Autowired
	private ITlKbJokeService tlKbJokeService;
	@Autowired
	private ITlKbJokeThumbsService tlKbJokeThumbsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param tlKbJoke
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TlKbJoke tlKbJoke,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TlKbJoke> queryWrapper = QueryGenerator.initQueryWrapper(tlKbJoke, req.getParameterMap());
		Page<TlKbJoke> page = new Page<TlKbJoke>(pageNo, pageSize);
		IPage<TlKbJoke> pageList = tlKbJokeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param tlKbJokePage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TlKbJokePage tlKbJokePage) {
		TlKbJoke tlKbJoke = new TlKbJoke();
		BeanUtils.copyProperties(tlKbJokePage, tlKbJoke);
		tlKbJokeService.saveMain(tlKbJoke, tlKbJokePage.getTlKbJokeThumbsList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param tlKbJokePage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TlKbJokePage tlKbJokePage) {
		TlKbJoke tlKbJoke = new TlKbJoke();
		BeanUtils.copyProperties(tlKbJokePage, tlKbJoke);
		TlKbJoke tlKbJokeEntity = tlKbJokeService.getById(tlKbJoke.getId());
		if(tlKbJokeEntity==null) {
			return Result.error("未找到对应数据");
		}
		tlKbJokeService.updateMain(tlKbJoke, tlKbJokePage.getTlKbJokeThumbsList());
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
		tlKbJokeService.delMain(id);
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
		this.tlKbJokeService.delBatchMain(Arrays.asList(ids.split(",")));
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
		TlKbJoke tlKbJoke = tlKbJokeService.getById(id);
		if(tlKbJoke==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(tlKbJoke);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryTlKbJokeThumbsByMainId")
	public Result<?> queryTlKbJokeThumbsListByMainId(@RequestParam(name="id",required=true) String id) {
		List<TlKbJokeThumbs> tlKbJokeThumbsList = tlKbJokeThumbsService.selectByMainId(id);
		return Result.ok(tlKbJokeThumbsList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tlKbJoke
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TlKbJoke tlKbJoke) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<TlKbJoke> queryWrapper = QueryGenerator.initQueryWrapper(tlKbJoke, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<TlKbJoke> queryList = tlKbJokeService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<TlKbJoke> tlKbJokeList = new ArrayList<TlKbJoke>();
      if(oConvertUtils.isEmpty(selections)) {
          tlKbJokeList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          tlKbJokeList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<TlKbJokePage> pageList = new ArrayList<TlKbJokePage>();
      for (TlKbJoke main : tlKbJokeList) {
          TlKbJokePage vo = new TlKbJokePage();
          BeanUtils.copyProperties(main, vo);
          List<TlKbJokeThumbs> tlKbJokeThumbsList = tlKbJokeThumbsService.selectByMainId(main.getId());
          vo.setTlKbJokeThumbsList(tlKbJokeThumbsList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "笑话段子列表");
      mv.addObject(NormalExcelConstants.CLASS, TlKbJokePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("笑话段子数据", "导出人:"+sysUser.getRealname(), "笑话段子"));
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
              List<TlKbJokePage> list = ExcelImportUtil.importExcel(file.getInputStream(), TlKbJokePage.class, params);
              for (TlKbJokePage page : list) {
                  TlKbJoke po = new TlKbJoke();
                  BeanUtils.copyProperties(page, po);
                  tlKbJokeService.saveMain(po, page.getTlKbJokeThumbsList());
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
