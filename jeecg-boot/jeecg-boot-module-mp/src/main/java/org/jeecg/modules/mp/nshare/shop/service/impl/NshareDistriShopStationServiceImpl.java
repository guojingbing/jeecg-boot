package org.jeecg.modules.mp.nshare.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopStation;
import org.jeecg.modules.mp.nshare.shop.mapper.NshareDistriShopStationMapper;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 社区分享店铺配送点
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareDistriShopStationServiceImpl extends ServiceImpl<NshareDistriShopStationMapper, NshareDistriShopStation> implements INshareDistriShopStationService {
	
	@Autowired
	private NshareDistriShopStationMapper nshareDistriShopStationMapper;
	
	@Override
	public List<NshareDistriShopStation> selectByMainId(String mainId) {
		return nshareDistriShopStationMapper.selectByMainId(mainId);
	}
}
