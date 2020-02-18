package org.jeecg.modules.mp.nshare.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoods;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoodsDaily;
import org.jeecg.modules.mp.nshare.goods.mapper.NshareDistriShopGoodsDailyMapper;
import org.jeecg.modules.mp.nshare.goods.mapper.NshareDistriShopGoodsMapper;
import org.jeecg.modules.mp.nshare.goods.service.INshareDistriShopGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 社区分享配送店铺商品
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareDistriShopGoodsServiceImpl extends ServiceImpl<NshareDistriShopGoodsMapper, NshareDistriShopGoods> implements INshareDistriShopGoodsService {

	@Autowired
	private NshareDistriShopGoodsMapper nshareDistriShopGoodsMapper;
	@Autowired
	private NshareDistriShopGoodsDailyMapper nshareDistriShopGoodsDailyMapper;
	
	@Override
	@Transactional
	public void saveMain(NshareDistriShopGoods nshareDistriShopGoods, List<NshareDistriShopGoodsDaily> nshareDistriShopGoodsDailyList) {
		nshareDistriShopGoodsMapper.insert(nshareDistriShopGoods);
		if(nshareDistriShopGoodsDailyList!=null && nshareDistriShopGoodsDailyList.size()>0) {
			for(NshareDistriShopGoodsDaily entity:nshareDistriShopGoodsDailyList) {
				//外键设置
				entity.setGoodsId(nshareDistriShopGoods.getId());
				nshareDistriShopGoodsDailyMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(NshareDistriShopGoods nshareDistriShopGoods,List<NshareDistriShopGoodsDaily> nshareDistriShopGoodsDailyList) {
		nshareDistriShopGoodsMapper.updateById(nshareDistriShopGoods);
		
		//1.先删除子表数据
		nshareDistriShopGoodsDailyMapper.deleteByMainId(nshareDistriShopGoods.getId());
		
		//2.子表数据重新插入
		if(nshareDistriShopGoodsDailyList!=null && nshareDistriShopGoodsDailyList.size()>0) {
			for(NshareDistriShopGoodsDaily entity:nshareDistriShopGoodsDailyList) {
				//外键设置
				entity.setGoodsId(nshareDistriShopGoods.getId());
				nshareDistriShopGoodsDailyMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		nshareDistriShopGoodsDailyMapper.deleteByMainId(id);
		nshareDistriShopGoodsMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			nshareDistriShopGoodsDailyMapper.deleteByMainId(id.toString());
			nshareDistriShopGoodsMapper.deleteById(id);
		}
	}
	
}
