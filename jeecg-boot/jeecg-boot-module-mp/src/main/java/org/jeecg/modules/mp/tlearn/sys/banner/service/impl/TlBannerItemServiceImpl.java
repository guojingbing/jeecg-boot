package org.jeecg.modules.mp.tlearn.sys.banner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.sys.banner.mapper.TlBannerItemMapper;
import org.jeecg.modules.mp.tlearn.sys.banner.service.ITlBannerItemService;
import org.jeecg.modules.mp.tlearn.sys.banner.entity.TlBannerItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 轮播条目
 * @Author:
 * @Date:   2020-01-30
 * @Version: V1.0
 */
@Service
public class TlBannerItemServiceImpl extends ServiceImpl<TlBannerItemMapper, TlBannerItem> implements ITlBannerItemService {
	
	@Autowired
	private TlBannerItemMapper tlBannerItemMapper;
	
	@Override
	public List<TlBannerItem> selectByMainId(String mainId) {
		return tlBannerItemMapper.selectByMainId(mainId);
	}

	@Override
	public List<TlBannerItem> loadBannerItemsByCode(String code){
		return tlBannerItemMapper.loadBannerItemsByCode(code);
	}
}
