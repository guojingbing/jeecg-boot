package org.jeecg.modules.mp.nshare.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopTeam;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享店铺配送点
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface NshareDistriShopTeamMapper extends BaseMapper<NshareDistriShopTeam> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<NshareDistriShopTeam> selectByMainId(@Param("mainId") String mainId);

	public List<Map> selectMapByMainId(@Param("mainId") String mainId);
}
