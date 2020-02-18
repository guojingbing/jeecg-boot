package org.jeecg.modules.mp.tlearn.sys.banner.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.mp.tlearn.sys.banner.service.ITlBannerItemService;
import org.jeecg.modules.mp.tlearn.sys.banner.entity.TlBannerItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @Description: 轮播
* @Author:
* @Date:   2020-01-30
* @Version: V1.0
*/
@Api(tags = "小程序-拾贝-轮播设置")
@RestController
@RequestMapping("/mp/api/tl/sys")
@Slf4j
public class TlBannerAPIController {
   @Autowired
   private ITlBannerItemService tlBannerItemService;

    /**
     * 根据位置代码获取轮播信息
     * @param code
     * @return
     */
    @ApiOperation(value = "根据位置代码获取轮播信息", notes = "根据位置代码获取轮播信息")
    @GetMapping(value = "/banner/{code}")
    public Result<?> queryByLocCode(@PathVariable(name = "code") String code) {
        List<TlBannerItem> tlBannerItemList = tlBannerItemService.loadBannerItemsByCode(code);
        return Result.ok(tlBannerItemList);
    }
}
