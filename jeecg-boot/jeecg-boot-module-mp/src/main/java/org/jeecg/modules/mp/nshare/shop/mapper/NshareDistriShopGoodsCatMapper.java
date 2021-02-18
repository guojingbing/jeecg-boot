package org.jeecg.modules.mp.nshare.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopGoodsCat;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送店铺商品分类
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
public interface NshareDistriShopGoodsCatMapper extends BaseMapper<NshareDistriShopGoodsCat> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<NshareDistriShopGoodsCat> selectByMainId(@Param("mainId") String mainId);

	public List<Map> selectMapByMainId(@Param("mainId") String mainId);

	public List<Map> selectMapToAdd(@Param("mainId") String mainId);
}
