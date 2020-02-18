package org.jeecg.modules.mp.nshare.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrderGoods;

import java.util.List;

/**
 * @Description: 社区分享配送订单商品
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareDistriOrderGoodsService extends IService<NshareDistriOrderGoods> {

	public List<NshareDistriOrderGoods> selectByMainId(String mainId);
}
