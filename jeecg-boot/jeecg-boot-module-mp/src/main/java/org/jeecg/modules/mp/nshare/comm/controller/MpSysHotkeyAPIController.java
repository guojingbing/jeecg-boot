package org.jeecg.modules.mp.nshare.comm.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.mp.nshare.comm.entity.MpSysHotkey;
import org.jeecg.modules.mp.nshare.comm.service.IMpSysHotkeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @Description: 搜索记录
* @Author:
* @Date:   2020-02-01
* @Version: V1.0
*/
@Api(tags = "小程序-搜索记录")
@RestController
@RequestMapping("/mp/api/sys")
@Slf4j
public class MpSysHotkeyAPIController extends JeecgController<MpSysHotkey, IMpSysHotkeyService> {
   @Autowired
   private IMpSysHotkeyService mpSysHotkeyService;
   /**
    * 获取热搜
    * @return
    */
   @GetMapping(value = "/hotkeys")
   public Result<?> doGetHotkeys() {
       List<MpSysHotkey> mpHotkeyList = mpSysHotkeyService.loadHotkeys();
       return Result.ok(mpHotkeyList);
   }

    /**
     *   添加
     * @param hotkey
     * @return
     */
    @PostMapping(value = "/hotkey")
    public Result<?> doPostHotkey(@RequestBody MpSysHotkey hotkey) {
        if(hotkey==null|| StringUtils.isEmpty(hotkey.getHotKey())){
            return Result.ok("缺少参数！");
        }
        MpSysHotkey mpSysHotkey=mpSysHotkeyService.findHotkeyByKey(hotkey.getHotKey(), hotkey.getKeyCat());
        if(mpSysHotkey==null){
            mpSysHotkey=new MpSysHotkey();
            mpSysHotkey.setHotKey(hotkey.getHotKey());
            mpSysHotkey.setKeyCat(hotkey.getKeyCat());
        }
        mpSysHotkey.setSearchNum(mpSysHotkey.getSearchNum()==null?1:mpSysHotkey.getSearchNum()+1);
        mpSysHotkeyService.saveOrUpdate(mpSysHotkey);
        return Result.ok("添加成功！");
    }
}
