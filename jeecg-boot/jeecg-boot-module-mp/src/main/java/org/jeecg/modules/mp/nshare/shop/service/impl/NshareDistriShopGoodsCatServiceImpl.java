package org.jeecg.modules.mp.nshare.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopGoodsCat;
import org.jeecg.modules.mp.nshare.shop.mapper.NshareDistriShopGoodsCatMapper;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopGoodsCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送店铺商品分类
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Service
public class NshareDistriShopGoodsCatServiceImpl extends ServiceImpl<NshareDistriShopGoodsCatMapper, NshareDistriShopGoodsCat> implements INshareDistriShopGoodsCatService {
	
	@Autowired
	private NshareDistriShopGoodsCatMapper nshareDistriShopGoodsCatMapper;
	
	@Override
	public List<NshareDistriShopGoodsCat> selectByMainId(String mainId) {
		return nshareDistriShopGoodsCatMapper.selectByMainId(mainId);
	}

	@Override
	public List<Map> selectMapByMainId(String mainId) {
		return nshareDistriShopGoodsCatMapper.selectMapByMainId(mainId);
	}

	@Override
	public List<Map> selectMapToAdd(String mainId) {
		return nshareDistriShopGoodsCatMapper.selectMapToAdd(mainId);
	}

	@Override
	public void deleteByMainId(String shopId){
		nshareDistriShopGoodsCatMapper.deleteByMainId(shopId);
	}
}
