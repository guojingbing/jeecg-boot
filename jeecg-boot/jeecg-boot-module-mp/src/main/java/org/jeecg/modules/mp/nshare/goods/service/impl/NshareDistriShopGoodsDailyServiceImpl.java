package org.jeecg.modules.mp.nshare.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoodsDaily;
import org.jeecg.modules.mp.nshare.goods.mapper.NshareDistriShopGoodsDailyMapper;
import org.jeecg.modules.mp.nshare.goods.service.INshareDistriShopGoodsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 社区分享店铺配送商品每日信息
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareDistriShopGoodsDailyServiceImpl extends ServiceImpl<NshareDistriShopGoodsDailyMapper, NshareDistriShopGoodsDaily> implements INshareDistriShopGoodsDailyService {
	
	@Autowired
	private NshareDistriShopGoodsDailyMapper nshareDistriShopGoodsDailyMapper;
	
	@Override
	public List<NshareDistriShopGoodsDaily> selectByMainId(String mainId) {
		return nshareDistriShopGoodsDailyMapper.selectByMainId(mainId);
	}
}
