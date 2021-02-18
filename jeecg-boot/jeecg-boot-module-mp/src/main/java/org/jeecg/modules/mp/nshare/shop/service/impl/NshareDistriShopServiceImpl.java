package org.jeecg.modules.mp.nshare.shop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.mp.nshare.shop.entity.*;
import org.jeecg.modules.mp.nshare.shop.mapper.*;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
@Service
public class NshareDistriShopServiceImpl extends ServiceImpl<NshareDistriShopMapper, NshareDistriShop> implements INshareDistriShopService {

	@Autowired
	private NshareDistriShopMapper nshareDistriShopMapper;
	@Autowired
	private NshareDistriShopAdminMapper nshareDistriShopAdminMapper;
	@Autowired
	private NshareDistriShopStationMapper nshareDistriShopStationMapper;
	@Autowired
	private NshareDistriShopGoodsCatMapper nshareDistriShopGoodsCatMapper;
	@Autowired
	private NshareDistriShopTeamMapper nshareDistriShopTeamMapper;
	
	@Override
	@Transactional
	public void saveMain(NshareDistriShop nshareDistriShop, List<NshareDistriShopAdmin> nshareDistriShopAdminList, List<NshareDistriShopStation> nshareDistriShopStationList, List<NshareDistriShopGoodsCat> nshareDistriShopGoodsCatList, List<NshareDistriShopTeam> teams) {
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

		nshareDistriShopTeamMapper.deleteByMainId(nshareDistriShop.getId());
		if(!CollectionUtils.isEmpty(teams)) {
			for(NshareDistriShopTeam entity:teams) {
				//外键设置
				entity.setShopId(nshareDistriShop.getId());
				nshareDistriShopTeamMapper.insert(entity);
			}
		}
//		if(nshareDistriShopGoodsCatList!=null && nshareDistriShopGoodsCatList.size()>0) {
//			for(NshareDistriShopGoodsCat entity:nshareDistriShopGoodsCatList) {
//				//外键设置
//				entity.setShopId(nshareDistriShop.getId());
//				nshareDistriShopGoodsCatMapper.insert(entity);
//			}
//		}
	}

	@Override
	@Transactional
	public void updateMain(NshareDistriShop nshareDistriShop,List<NshareDistriShopAdmin> nshareDistriShopAdminList,List<NshareDistriShopStation> nshareDistriShopStationList,List<NshareDistriShopGoodsCat> nshareDistriShopGoodsCatList, List<NshareDistriShopTeam> teams) {
		nshareDistriShopMapper.updateById(nshareDistriShop);
		
		//1.先删除子表数据
		nshareDistriShopAdminMapper.deleteByMainId(nshareDistriShop.getId());
//		nshareDistriShopStationMapper.deleteByMainId(nshareDistriShop.getId());
//		nshareDistriShopGoodsCatMapper.deleteByMainId(nshareDistriShop.getId());
		
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
				if(StringUtils.isNotBlank(entity.getId())){
					nshareDistriShopStationMapper.updateById(entity);
				}else{
					nshareDistriShopStationMapper.insert(entity);
				}
			}
		}

		nshareDistriShopTeamMapper.deleteByMainId(nshareDistriShop.getId());
		if(!CollectionUtils.isEmpty(teams)) {
			for(NshareDistriShopTeam entity:teams) {
				//外键设置
				entity.setShopId(nshareDistriShop.getId());
				nshareDistriShopTeamMapper.insert(entity);
			}
		}
//		if(nshareDistriShopGoodsCatList!=null && nshareDistriShopGoodsCatList.size()>0) {
//			for(NshareDistriShopGoodsCat entity:nshareDistriShopGoodsCatList) {
//				//外键设置
//				entity.setShopId(nshareDistriShop.getId());
//				nshareDistriShopGoodsCatMapper.insert(entity);
//			}
//		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		nshareDistriShopAdminMapper.deleteByMainId(id);
		nshareDistriShopStationMapper.deleteByMainId(id);
		nshareDistriShopGoodsCatMapper.deleteByMainId(id);
		nshareDistriShopMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			nshareDistriShopAdminMapper.deleteByMainId(id.toString());
			nshareDistriShopStationMapper.deleteByMainId(id.toString());
			nshareDistriShopGoodsCatMapper.deleteByMainId(id.toString());
			nshareDistriShopMapper.deleteById(id);
		}
	}


	@Override
	public IPage<Map> loadList4API(int pageSize, int pageNo, BigDecimal lng, BigDecimal lat, String searchKey) {
		Page<Map> page = new Page<>(pageNo, pageSize);
		return nshareDistriShopMapper.loadList4API(page,lng,lat,searchKey);
	}

	@Override
	public List<NshareDistriShop> findShopsByOwnerId(String ownerId){
		return nshareDistriShopMapper.selectByOwnerId(ownerId);
	}
}
