package org.jeecg.modules.mp.nshare.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoods;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoodsDaily;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 社区分享配送店铺商品
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareDistriShopGoodsService extends IService<NshareDistriShopGoods> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(NshareDistriShopGoods nshareDistriShopGoods, List<NshareDistriShopGoodsDaily> nshareDistriShopGoodsDailyList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(NshareDistriShopGoods nshareDistriShopGoods, List<NshareDistriShopGoodsDaily> nshareDistriShopGoodsDailyList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
