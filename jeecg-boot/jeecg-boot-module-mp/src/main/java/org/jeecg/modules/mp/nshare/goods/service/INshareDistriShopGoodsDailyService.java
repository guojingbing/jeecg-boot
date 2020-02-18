package org.jeecg.modules.mp.nshare.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoodsDaily;

import java.util.List;

/**
 * @Description: 社区分享店铺配送商品每日信息
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareDistriShopGoodsDailyService extends IService<NshareDistriShopGoodsDaily> {

	public List<NshareDistriShopGoodsDaily> selectByMainId(String mainId);
}
