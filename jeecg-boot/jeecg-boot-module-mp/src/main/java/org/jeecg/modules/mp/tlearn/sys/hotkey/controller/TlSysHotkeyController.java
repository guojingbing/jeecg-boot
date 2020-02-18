package org.jeecg.modules.mp.tlearn.sys.hotkey.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.mp.tlearn.sys.hotkey.entity.TlSysHotkey;
import org.jeecg.modules.mp.tlearn.sys.hotkey.service.ITlSysHotkeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 搜索记录
 * @Author:
 * @Date:   2020-02-01
 * @Version: V1.0
 */
@RestController
@RequestMapping("/org.jeecg.modules.mp.tlearn.sys.hotkey/tlSysHotkey")
@Slf4j
public class TlSysHotkeyController extends JeecgController<TlSysHotkey, ITlSysHotkeyService> {
	@Autowired
	private ITlSysHotkeyService tlSysHotkeyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param tlSysHotkey
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TlSysHotkey tlSysHotkey,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TlSysHotkey> queryWrapper = QueryGenerator.initQueryWrapper(tlSysHotkey, req.getParameterMap());
		Page<TlSysHotkey> page = new Page<TlSysHotkey>(pageNo, pageSize);
		IPage<TlSysHotkey> pageList = tlSysHotkeyService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param tlSysHotkey
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TlSysHotkey tlSysHotkey) {
		tlSysHotkeyService.save(tlSysHotkey);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param tlSysHotkey
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TlSysHotkey tlSysHotkey) {
		tlSysHotkeyService.updateById(tlSysHotkey);
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
		tlSysHotkeyService.removeById(id);
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
		this.tlSysHotkeyService.removeByIds(Arrays.asList(ids.split(",")));
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
		TlSysHotkey tlSysHotkey = tlSysHotkeyService.getById(id);
		if(tlSysHotkey==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(tlSysHotkey);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tlSysHotkey
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TlSysHotkey tlSysHotkey) {
        return super.exportXls(request, tlSysHotkey, TlSysHotkey.class, "搜索记录");
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
        return super.importExcel(request, response, TlSysHotkey.class);
    }

}
