package org.jeecg.modules.mp.nshare.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopAdmin;

import java.util.List;

/**
 * @Description: 社区分享配送店铺管理员
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface NshareDistriShopAdminMapper extends BaseMapper<NshareDistriShopAdmin> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<NshareDistriShopAdmin> selectByMainId(@Param("mainId") String mainId);
}
