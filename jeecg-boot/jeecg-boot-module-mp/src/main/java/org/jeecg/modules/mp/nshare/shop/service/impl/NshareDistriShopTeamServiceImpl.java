package org.jeecg.modules.mp.nshare.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopTeam;
import org.jeecg.modules.mp.nshare.shop.mapper.NshareDistriShopTeamMapper;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享店铺配送社群
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareDistriShopTeamServiceImpl extends ServiceImpl<NshareDistriShopTeamMapper, NshareDistriShopTeam> implements INshareDistriShopTeamService {
	
	@Autowired
	private NshareDistriShopTeamMapper nshareDistriShopTeamMapper;
	
	@Override
	public List<NshareDistriShopTeam> selectByMainId(String mainId) {
		return nshareDistriShopTeamMapper.selectByMainId(mainId);
	}
	@Override
	public List<Map> selectShopTeams(String mainId){
		return nshareDistriShopTeamMapper.selectMapByMainId(mainId);
	}
}
