package org.jeecg.modules.mp.nshare.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrder;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrderGoods;
import org.jeecg.modules.mp.nshare.order.mapper.NshareDistriOrderGoodsMapper;
import org.jeecg.modules.mp.nshare.order.mapper.NshareDistriOrderMapper;
import org.jeecg.modules.mp.nshare.order.service.INshareDistriOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送订单
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareDistriOrderServiceImpl extends ServiceImpl<NshareDistriOrderMapper, NshareDistriOrder> implements INshareDistriOrderService {

	@Autowired
	private NshareDistriOrderMapper nshareDistriOrderMapper;
	@Autowired
	private NshareDistriOrderGoodsMapper nshareDistriOrderGoodsMapper;
	
	@Override
	@Transactional
	public void saveMain(NshareDistriOrder nshareDistriOrder, List<NshareDistriOrderGoods> nshareDistriOrderGoodsList) {
		nshareDistriOrderMapper.insert(nshareDistriOrder);
		if(nshareDistriOrderGoodsList!=null && nshareDistriOrderGoodsList.size()>0) {
			for(NshareDistriOrderGoods entity:nshareDistriOrderGoodsList) {
				//外键设置
				entity.setOrderId(nshareDistriOrder.getId());
				nshareDistriOrderGoodsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(NshareDistriOrder nshareDistriOrder,List<NshareDistriOrderGoods> nshareDistriOrderGoodsList) {
		nshareDistriOrderMapper.updateById(nshareDistriOrder);
		
		//1.先删除子表数据
		nshareDistriOrderGoodsMapper.deleteByMainId(nshareDistriOrder.getId());
		
		//2.子表数据重新插入
		if(nshareDistriOrderGoodsList!=null && nshareDistriOrderGoodsList.size()>0) {
			for(NshareDistriOrderGoods entity:nshareDistriOrderGoodsList) {
				//外键设置
				entity.setOrderId(nshareDistriOrder.getId());
				nshareDistriOrderGoodsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		nshareDistriOrderGoodsMapper.deleteByMainId(id);
		nshareDistriOrderMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			nshareDistriOrderGoodsMapper.deleteByMainId(id.toString());
			nshareDistriOrderMapper.deleteById(id);
		}
	}

	@Override
	public IPage<Map> loadList4API(int pageSize, int pageNo, String shopId, String userId, String teamId, String status, String searchKey) {
		Page<Map> page = new Page<>(pageNo, pageSize);
		return nshareDistriOrderMapper.loadList4API(page,shopId,userId,teamId,status,searchKey);
	}

	@Override
	public List<NshareDistriOrder> selectOrderByDate(String pickDate){
		return nshareDistriOrderMapper.selectOrderByDate(pickDate);
	}
}
