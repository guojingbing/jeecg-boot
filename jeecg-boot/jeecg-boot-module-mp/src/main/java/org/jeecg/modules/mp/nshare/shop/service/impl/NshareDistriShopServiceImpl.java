package org.jeecg.modules.mp.nshare.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShop;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopAdmin;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopStation;
import org.jeecg.modules.mp.nshare.shop.mapper.NshareDistriShopAdminMapper;
import org.jeecg.modules.mp.nshare.shop.mapper.NshareDistriShopMapper;
import org.jeecg.modules.mp.nshare.shop.mapper.NshareDistriShopStationMapper;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 社区分享配送店铺
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareDistriShopServiceImpl extends ServiceImpl<NshareDistriShopMapper, NshareDistriShop> implements INshareDistriShopService {

	@Autowired
	private NshareDistriShopMapper nshareDistriShopMapper;
	@Autowired
	private NshareDistriShopAdminMapper nshareDistriShopAdminMapper;
	@Autowired
	private NshareDistriShopStationMapper nshareDistriShopStationMapper;
	
	@Override
	@Transactional
	public void saveMain(NshareDistriShop nshareDistriShop, List<NshareDistriShopAdmin> nshareDistriShopAdminList, List<NshareDistriShopStation> nshareDistriShopStationList) {
		nshareDistriShopMapper.insert(nshareDistriShop);
		if(nshareDistriShopAdminList!=null && nshareDistriShopAdminList.size()>0) {
			for(NshareDistriShopAdmin entity:nshareDistriShopAdminList) {
				//外键设置
				entity.setShopId(nshareDistriShop.getId());
				nshareDistriShopAdminMapper.insert(entity);
			}
		}
		if(nshareDistriShopStationList!=null && nshareDistriShopStationList.size()>0) {
			for(NshareDistriShopStation entity:nshareDistriShopStationList) {
				//外键设置
				entity.setShopId(nshareDistriShop.getId());
				nshareDistriShopStationMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(NshareDistriShop nshareDistriShop,List<NshareDistriShopAdmin> nshareDistriShopAdminList,List<NshareDistriShopStation> nshareDistriShopStationList) {
		nshareDistriShopMapper.updateById(nshareDistriShop);
		
		//1.先删除子表数据
		nshareDistriShopAdminMapper.deleteByMainId(nshareDistriShop.getId());
		nshareDistriShopStationMapper.deleteByMainId(nshareDistriShop.getId());
		
		//2.子表数据重新插入
		if(nshareDistriShopAdminList!=null && nshareDistriShopAdminList.size()>0) {
			for(NshareDistriShopAdmin entity:nshareDistriShopAdminList) {
				//外键设置
				entity.setShopId(nshareDistriShop.getId());
				nshareDistriShopAdminMapper.insert(entity);
			}
		}
		if(nshareDistriShopStationList!=null && nshareDistriShopStationList.size()>0) {
			for(NshareDistriShopStation entity:nshareDistriShopStationList) {
				//外键设置
				entity.setShopId(nshareDistriShop.getId());
				nshareDistriShopStationMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		nshareDistriShopAdminMapper.deleteByMainId(id);
		nshareDistriShopStationMapper.deleteByMainId(id);
		nshareDistriShopMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			nshareDistriShopAdminMapper.deleteByMainId(id.toString());
			nshareDistriShopStationMapper.deleteByMainId(id.toString());
			nshareDistriShopMapper.deleteById(id);
		}
	}
	
}
