package org.jeecg.modules.mp.nshare.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoodsDaily;

import java.util.List;

/**
 * @Description: 社区分享店铺配送商品每日信息
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface NshareDistriShopGoodsDailyMapper extends BaseMapper<NshareDistriShopGoodsDaily> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<NshareDistriShopGoodsDaily> selectByMainId(@Param("mainId") String mainId);
}
