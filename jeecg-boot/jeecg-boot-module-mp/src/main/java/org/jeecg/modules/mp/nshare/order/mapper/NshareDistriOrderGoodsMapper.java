package org.jeecg.modules.mp.nshare.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrderGoods;

import java.util.List;

/**
 * @Description: 社区分享配送订单商品
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface NshareDistriOrderGoodsMapper extends BaseMapper<NshareDistriOrderGoods> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<NshareDistriOrderGoods> selectByMainId(@Param("mainId") String mainId);
}
