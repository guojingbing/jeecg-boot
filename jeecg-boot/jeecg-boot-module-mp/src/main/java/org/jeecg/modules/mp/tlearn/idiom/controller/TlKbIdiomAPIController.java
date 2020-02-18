package org.jeecg.modules.mp.tlearn.idiom.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.mp.tlearn.idiom.entity.TlKbIdiom;
import org.jeecg.modules.mp.tlearn.idiom.service.ITlKbIdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
* @Description: 成语
* @Author:
* @Date:   2020-01-17
* @Version: V1.0
*/
@Api(tags = "小程序-拾贝-古诗词")
@RestController
@RequestMapping("/mp/api/tl/kb/idiom")
@Slf4j
public class TlKbIdiomAPIController extends JeecgController<TlKbIdiom, ITlKbIdiomService> {
   @Autowired
   private ITlKbIdiomService tlKbIdiomService;

   /**
    * 分页列表查询
    * @param pageIndex
    * @param pageSize
    * @param searchKey
    * @param req
    * @return
    */
   @ApiOperation(value = "获取成语列表", notes = "分页获取成语")
   @GetMapping(value = "/{pageIndex}/articles")
   public Result<?> queryPageList(@PathVariable(name="pageIndex") Integer pageIndex,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  @RequestParam(name="searchKey", required=false) String searchKey,
                                  HttpServletRequest req) {
       IPage<Map> pageList = tlKbIdiomService.loadList4API(pageSize, pageIndex, searchKey);
       return Result.ok(pageList);
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @GetMapping(value = "/article/{id}")
   public Result<?> queryById(@PathVariable(name="id") String id) {
       TlKbIdiom tlKbIdiom = tlKbIdiomService.getById(id);
       if(tlKbIdiom==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(tlKbIdiom);
   }
}
