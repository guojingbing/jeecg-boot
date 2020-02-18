package org.jeecg.modules.mp.nshare.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrder;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrderGoods;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 社区分享配送订单
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareDistriOrderService extends IService<NshareDistriOrder> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(NshareDistriOrder nshareDistriOrder, List<NshareDistriOrderGoods> nshareDistriOrderGoodsList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(NshareDistriOrder nshareDistriOrder, List<NshareDistriOrderGoods> nshareDistriOrderGoodsList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
