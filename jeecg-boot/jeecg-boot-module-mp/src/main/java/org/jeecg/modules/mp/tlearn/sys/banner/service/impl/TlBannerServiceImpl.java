package org.jeecg.modules.mp.tlearn.sys.banner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.sys.banner.mapper.TlBannerItemMapper;
import org.jeecg.modules.mp.tlearn.sys.banner.mapper.TlBannerMapper;
import org.jeecg.modules.mp.tlearn.sys.banner.entity.TlBanner;
import org.jeecg.modules.mp.tlearn.sys.banner.entity.TlBannerItem;
import org.jeecg.modules.mp.tlearn.sys.banner.service.ITlBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 轮播
 * @Author:
 * @Date:   2020-01-30
 * @Version: V1.0
 */
@Service
public class TlBannerServiceImpl extends ServiceImpl<TlBannerMapper, TlBanner> implements ITlBannerService {

	@Autowired
	private TlBannerMapper tlBannerMapper;
	@Autowired
	private TlBannerItemMapper tlBannerItemMapper;
	
	@Override
	@Transactional
	public void saveMain(TlBanner tlBanner, List<TlBannerItem> tlBannerItemList) {
		tlBannerMapper.insert(tlBanner);
		if(tlBannerItemList!=null && tlBannerItemList.size()>0) {
			for(TlBannerItem entity:tlBannerItemList) {
				//外键设置
				entity.setBannerId(tlBanner.getId());
				tlBannerItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(TlBanner tlBanner,List<TlBannerItem> tlBannerItemList) {
		tlBannerMapper.updateById(tlBanner);
		
		//1.先删除子表数据
		tlBannerItemMapper.deleteByMainId(tlBanner.getId());
		
		//2.子表数据重新插入
		if(tlBannerItemList!=null && tlBannerItemList.size()>0) {
			for(TlBannerItem entity:tlBannerItemList) {
				//外键设置
				entity.setBannerId(tlBanner.getId());
				tlBannerItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		tlBannerItemMapper.deleteByMainId(id);
		tlBannerMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			tlBannerItemMapper.deleteByMainId(id.toString());
			tlBannerMapper.deleteById(id);
		}
	}
	
}
