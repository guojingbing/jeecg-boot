package org.jeecg.modules.mp.tlearn.sys.banner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.sys.banner.entity.TlBannerItem;

import java.util.List;

/**
 * @Description: 轮播条目
 * @Author:
 * @Date:   2020-01-30
 * @Version: V1.0
 */
public interface TlBannerItemMapper extends BaseMapper<TlBannerItem> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<TlBannerItem> selectByMainId(@Param("mainId") String mainId);

	List<TlBannerItem> loadBannerItemsByCode(@Param("code") String code);
}
