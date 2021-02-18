package org.jeecg.modules.mp.nshare.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.user.entity.NshareUserCart;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享终端用户购物车
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface NshareUserCartMapper extends BaseMapper<NshareUserCart> {
    IPage<Map> loadList4API(Page<Map> page, String userId, String stationId, String goodsId);
    List<NshareUserCart> selectByMainId(@Param("userId") String userId,@Param("stationId") String stationId);
}
