package org.jeecg.modules.mp.nshare.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopStation;

import java.util.List;

/**
 * @Description: 社区分享店铺配送点
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareDistriShopStationService extends IService<NshareDistriShopStation> {

	public List<NshareDistriShopStation> selectByMainId(String mainId);
}
