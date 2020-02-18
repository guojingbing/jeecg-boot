package org.jeecg.modules.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.entity.SysDictItem;
import org.jeecg.modules.system.service.ISysDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 数据字典API
 */
@Api(tags = "数据字典API")
@RestController
@RequestMapping("/mp/api/sys/dict")
@Slf4j
public class SysDictItemAPIController {

	@Autowired
	private ISysDictItemService sysDictItemService;


	@ApiOperation(value = "获取数据字典项目", notes = "")
	@GetMapping(value = "/items")
	public Result<?> queryDynList(HttpServletRequest req,@RequestParam(name = "itemId") String itemId) {
		List<SysDictItem> list = sysDictItemService.selectItemsByMainId(itemId);
		return Result.ok(list);
	}
	/**
	 * @功能：查询字典古诗词形式数据
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取古诗词形式", notes = "获取古诗词形式")
	@GetMapping(value = "/forms")
	public Result<?> queryPoetryFormList(HttpServletRequest req) {
		List<SysDictItem> list = sysDictItemService.selectItemsByMainId("1213299435442868225");
		return Result.ok(list);
	}

	@ApiOperation(value = "获取朝代", notes = "获取朝代信息")
	@GetMapping(value = "/dyns")
	public Result<?> queryDynList(HttpServletRequest req) {
		List<SysDictItem> list = sysDictItemService.selectItemsByMainId("1212644360118607874");
		return Result.ok(list);
	}

	@ApiOperation(value = "根据字典主键获取字典项目", notes = "根据字典主键获取字典项目")
	@GetMapping(value = "/{mid}/items")
	public Result<?> queryDictList(HttpServletRequest req, @PathVariable(name = "mid") String mid) {
		List<SysDictItem> list = sysDictItemService.selectItemsByMainId(mid);
		return Result.ok(list);
	}
}
