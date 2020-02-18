package org.jeecg.modules.mp.tlearn.idiom.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.mp.tlearn.idiom.entity.TlKbIdiom;
import org.jeecg.modules.mp.tlearn.idiom.service.ITlKbIdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 成语
 * @Author:
 * @Date:   2020-01-17
 * @Version: V1.0
 */
@RestController
@RequestMapping("/idiom/tlKbIdiom")
@Slf4j
public class TlKbIdiomController extends JeecgController<TlKbIdiom, ITlKbIdiomService> {
	@Autowired
	private ITlKbIdiomService tlKbIdiomService;
	
	/**
	 * 分页列表查询
	 *
	 * @param tlKbIdiom
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TlKbIdiom tlKbIdiom,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TlKbIdiom> queryWrapper = QueryGenerator.initQueryWrapper(tlKbIdiom, req.getParameterMap());
		Page<TlKbIdiom> page = new Page<TlKbIdiom>(pageNo, pageSize);
		IPage<TlKbIdiom> pageList = tlKbIdiomService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param tlKbIdiom
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TlKbIdiom tlKbIdiom) {
		tlKbIdiomService.save(tlKbIdiom);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param tlKbIdiom
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TlKbIdiom tlKbIdiom) {
		tlKbIdiomService.updateById(tlKbIdiom);
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
		tlKbIdiomService.removeById(id);
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
		this.tlKbIdiomService.removeByIds(Arrays.asList(ids.split(",")));
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
		TlKbIdiom tlKbIdiom = tlKbIdiomService.getById(id);
		if(tlKbIdiom==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(tlKbIdiom);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tlKbIdiom
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TlKbIdiom tlKbIdiom) {
        return super.exportXls(request, tlKbIdiom, TlKbIdiom.class, "成语");
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
        return super.importExcel(request, response, TlKbIdiom.class);
    }

}
