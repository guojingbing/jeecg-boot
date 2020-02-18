package org.jeecg.modules.mp.tlearn.author.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.mp.tlearn.author.entity.TlKbAuthor;
import org.jeecg.modules.mp.tlearn.author.service.ITlKbAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 知识库-作者信息
 * @Author:
 * @Date:   2020-01-09
 * @Version: V1.0
 */
@RestController
@RequestMapping("/author/tlKbAuthor")
@Slf4j
public class TlKbAuthorController extends JeecgController<TlKbAuthor, ITlKbAuthorService> {
	@Autowired
	private ITlKbAuthorService tlKbAuthorService;
	
	/**
	 * 分页列表查询
	 *
	 * @param tlKbAuthor
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TlKbAuthor tlKbAuthor,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TlKbAuthor> queryWrapper = QueryGenerator.initQueryWrapper(tlKbAuthor, req.getParameterMap());
		Page<TlKbAuthor> page = new Page<TlKbAuthor>(pageNo, pageSize);
		IPage<TlKbAuthor> pageList = tlKbAuthorService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param tlKbAuthor
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TlKbAuthor tlKbAuthor) {
		tlKbAuthorService.save(tlKbAuthor);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param tlKbAuthor
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TlKbAuthor tlKbAuthor) {
		tlKbAuthorService.updateById(tlKbAuthor);
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
		tlKbAuthorService.removeById(id);
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
		this.tlKbAuthorService.removeByIds(Arrays.asList(ids.split(",")));
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
		TlKbAuthor tlKbAuthor = tlKbAuthorService.getById(id);
		if(tlKbAuthor==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(tlKbAuthor);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tlKbAuthor
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TlKbAuthor tlKbAuthor) {
        return super.exportXls(request, tlKbAuthor, TlKbAuthor.class, "知识库-作者信息");
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
        return super.importExcel(request, response, TlKbAuthor.class);
    }

}
