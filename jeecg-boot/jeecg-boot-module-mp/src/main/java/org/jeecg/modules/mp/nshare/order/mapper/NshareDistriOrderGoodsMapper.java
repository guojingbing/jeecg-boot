package org.jeecg.modules.mp.nshare.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrderGoods;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送订单商品
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface NshareDistriOrderGoodsMapper extends BaseMapper<NshareDistriOrderGoods> {

	boolean deleteByMainId(@Param("mainId") String mainId);
    
	List<NshareDistriOrderGoods> selectByMainId(@Param("mainId") String mainId);

	List<Map> selectByOrderId(@Param("mainId") String mainId);

	IPage<Map> loadSoldList4API(Page<Map> page, String shopId, String pickDate, String searchKey);
}
