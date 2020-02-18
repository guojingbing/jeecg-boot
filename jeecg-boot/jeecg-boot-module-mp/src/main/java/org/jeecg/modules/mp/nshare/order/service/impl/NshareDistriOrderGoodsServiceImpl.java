package org.jeecg.modules.mp.nshare.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrderGoods;
import org.jeecg.modules.mp.nshare.order.mapper.NshareDistriOrderGoodsMapper;
import org.jeecg.modules.mp.nshare.order.service.INshareDistriOrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 社区分享配送订单商品
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareDistriOrderGoodsServiceImpl extends ServiceImpl<NshareDistriOrderGoodsMapper, NshareDistriOrderGoods> implements INshareDistriOrderGoodsService {
	
	@Autowired
	private NshareDistriOrderGoodsMapper nshareDistriOrderGoodsMapper;
	
	@Override
	public List<NshareDistriOrderGoods> selectByMainId(String mainId) {
		return nshareDistriOrderGoodsMapper.selectByMainId(mainId);
	}
}
