package org.jeecg.modules.mp.tlearn.sys.banner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.sys.banner.entity.TlBanner;
import org.jeecg.modules.mp.tlearn.sys.banner.entity.TlBannerItem;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 轮播
 * @Author:
 * @Date:   2020-01-30
 * @Version: V1.0
 */
public interface ITlBannerService extends IService<TlBanner> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(TlBanner tlBanner, List<TlBannerItem> tlBannerItemList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TlBanner tlBanner, List<TlBannerItem> tlBannerItemList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
