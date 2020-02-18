package org.jeecg.modules.mp.tlearn.sys.hotkey.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.mp.tlearn.sys.hotkey.entity.TlSysHotkey;
import org.jeecg.modules.mp.tlearn.sys.hotkey.service.ITlSysHotkeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @Description: 搜索记录
* @Author:
* @Date:   2020-02-01
* @Version: V1.0
*/
@Api(tags = "小程序-拾贝-轮播设置")
@RestController
@RequestMapping("/mp/api/tl/sys")
@Slf4j
public class TlSysHotkeyAPIController extends JeecgController<TlSysHotkey, ITlSysHotkeyService> {
   @Autowired
   private ITlSysHotkeyService tlSysHotkeyService;
   /**
    * 获取热搜
    * @return
    */
   @GetMapping(value = "/hotkeys")
   public Result<?> doGetHotkeys() {
       List<TlSysHotkey> tlHotkeyList = tlSysHotkeyService.loadHotkeys();
       return Result.ok(tlHotkeyList);
   }

    /**
     *   添加
     * @param hotkey
     * @return
     */
    @PostMapping(value = "/hotkey")
    public Result<?> doPostHotkey(@RequestBody TlSysHotkey hotkey) {
        if(hotkey==null|| StringUtils.isEmpty(hotkey.getHotKey())){
            return Result.ok("缺少参数！");
        }
        TlSysHotkey tlSysHotkey=tlSysHotkeyService.findHotkeyByKey(hotkey.getHotKey(), hotkey.getKeyCat());
        if(tlSysHotkey==null){
            tlSysHotkey=new TlSysHotkey();
            tlSysHotkey.setHotKey(hotkey.getHotKey());
            tlSysHotkey.setKeyCat(hotkey.getKeyCat());
        }
        tlSysHotkey.setSearchNum(tlSysHotkey.getSearchNum()==null?1:tlSysHotkey.getSearchNum()+1);
        tlSysHotkeyService.saveOrUpdate(tlSysHotkey);
        return Result.ok("添加成功！");
    }
}
