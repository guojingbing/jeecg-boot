package org.jeecg.modules.mp.tlearn.sys.banner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.sys.banner.entity.TlBannerItem;

import java.util.List;

/**
 * @Description: 轮播条目
 * @Author:
 * @Date:   2020-01-30
 * @Version: V1.0
 */
public interface ITlBannerItemService extends IService<TlBannerItem> {
	List<TlBannerItem> selectByMainId(String mainId);

	/**
	 * 根据位置代码获取启用中的轮播图信息
	 * @param code
	 * @return
	 */
	List<TlBannerItem> loadBannerItemsByCode(String code);
}
