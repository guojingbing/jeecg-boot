package org.jeecg.modules.mp.nshare.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.shop.entity.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送店铺
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
public interface INshareDistriShopService extends IService<NshareDistriShop> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(NshareDistriShop nshareDistriShop, List<NshareDistriShopAdmin> nshareDistriShopAdminList, List<NshareDistriShopStation> nshareDistriShopStationList, List<NshareDistriShopGoodsCat> nshareDistriShopGoodsCatList, List<NshareDistriShopTeam> teams) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(NshareDistriShop nshareDistriShop, List<NshareDistriShopAdmin> nshareDistriShopAdminList, List<NshareDistriShopStation> nshareDistriShopStationList, List<NshareDistriShopGoodsCat> nshareDistriShopGoodsCatList, List<NshareDistriShopTeam> teams);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);

	IPage<Map> loadList4API(int pageSize, int pageNo, BigDecimal lng, BigDecimal lat, String searchKey);

	List<NshareDistriShop> findShopsByOwnerId(String ownerId);
}
