package org.jeecg.modules.mp.nshare.lottery.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottery;
import org.jeecg.modules.mp.nshare.lottery.service.INshareLotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
* @Description: 社区分享彩票信息
* @Author:
* @Date:   2020-02-16
* @Version: V1.0
*/
@RestController
@RequestMapping("/mp/api/ns/lott")
@Slf4j
public class NshareLotteryAPIController extends JeecgController<NshareLottery, INshareLotteryService> {
   @Autowired
   private INshareLotteryService nshareLotteryService;

   /**
    * 分页列表查询
    *
    * @param nshareLottery
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @GetMapping(value = "/list")
   public Result<?> queryPageList(NshareLottery nshareLottery,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<NshareLottery> queryWrapper = QueryGenerator.initQueryWrapper(nshareLottery, req.getParameterMap());
       Page<NshareLottery> page = new Page<NshareLottery>(pageNo, pageSize);
       IPage<NshareLottery> pageList = nshareLotteryService.page(page, queryWrapper);
       return Result.ok(pageList);
   }

   /**
    *   添加
    *
    * @param nshareLottery
    * @return
    */
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody NshareLottery nshareLottery) {
       nshareLotteryService.save(nshareLottery);
       return Result.ok("添加成功！");
   }

   /**
    *  编辑
    *
    * @param nshareLottery
    * @return
    */
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody NshareLottery nshareLottery) {
       nshareLotteryService.updateById(nshareLottery);
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
       nshareLotteryService.removeById(id);
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
       this.nshareLotteryService.removeByIds(Arrays.asList(ids.split(",")));
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
       NshareLottery nshareLottery = nshareLotteryService.getById(id);
       if(nshareLottery==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(nshareLottery);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param nshareLottery
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, NshareLottery nshareLottery) {
       return super.exportXls(request, nshareLottery, NshareLottery.class, "社区分享彩票信息");
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
       return super.importExcel(request, response, NshareLottery.class);
   }

}
