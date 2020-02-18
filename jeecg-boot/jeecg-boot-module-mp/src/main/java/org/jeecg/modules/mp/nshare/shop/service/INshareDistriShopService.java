package org.jeecg.modules.mp.nshare.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShop;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopAdmin;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopStation;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 社区分享配送店铺
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareDistriShopService extends IService<NshareDistriShop> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(NshareDistriShop nshareDistriShop, List<NshareDistriShopAdmin> nshareDistriShopAdminList, List<NshareDistriShopStation> nshareDistriShopStationList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(NshareDistriShop nshareDistriShop, List<NshareDistriShopAdmin> nshareDistriShopAdminList, List<NshareDistriShopStation> nshareDistriShopStationList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
