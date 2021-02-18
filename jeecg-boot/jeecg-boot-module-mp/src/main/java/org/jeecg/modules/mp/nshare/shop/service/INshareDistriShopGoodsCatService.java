package org.jeecg.modules.mp.nshare.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopGoodsCat;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送店铺商品分类
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
public interface INshareDistriShopGoodsCatService extends IService<NshareDistriShopGoodsCat> {
	List<NshareDistriShopGoodsCat> selectByMainId(String mainId);
	List<Map> selectMapByMainId(String mainId);
	List<Map> selectMapToAdd(String mainId);

	void deleteByMainId(String shopId);
}
