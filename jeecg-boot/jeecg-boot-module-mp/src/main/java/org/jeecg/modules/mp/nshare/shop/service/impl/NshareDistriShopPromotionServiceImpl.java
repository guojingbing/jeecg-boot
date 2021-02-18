package org.jeecg.modules.mp.nshare.shop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopPromotion;
import org.jeecg.modules.mp.nshare.shop.mapper.NshareDistriShopPromotionGoodsMapper;
import org.jeecg.modules.mp.nshare.shop.mapper.NshareDistriShopPromotionMapper;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享店铺营销活动
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareDistriShopPromotionServiceImpl extends ServiceImpl<NshareDistriShopPromotionMapper, NshareDistriShopPromotion> implements INshareDistriShopPromotionService {
	
	@Autowired
	private NshareDistriShopPromotionMapper nshareDistriShopPromotionMapper;

	@Autowired
	private NshareDistriShopPromotionGoodsMapper nshareDistriShopPromotionGoodsMapper;
	/**
	 * 添加一对多
	 */
	public void saveMain(NshareDistriShopPromotion nshareDistriShopPromotion){
		nshareDistriShopPromotionMapper.insert(nshareDistriShopPromotion);
	}

	@Override
	public IPage<Map> loadList4API(int pageSize, int pageNo, String shopId, Integer hisType, Integer auditType,String teamId, String searchKey) {
		Page<Map> page = new Page<>(pageNo, pageSize);
		return nshareDistriShopPromotionMapper.loadList4API(page, shopId,hisType, auditType,teamId,searchKey);
	}

	@Override
	public List<NshareDistriShopPromotion> loadPromosByGoodsId(String goodsId){
		return nshareDistriShopPromotionMapper.selectByGoodsId(goodsId);
	}

	@Override
	public boolean canAddPromos(String goodsId, String startTime, String endTime){
		List list=nshareDistriShopPromotionMapper.selectByGoodsIdRange(goodsId,startTime,endTime);
		return CollectionUtils.isEmpty(list);
	}
}
