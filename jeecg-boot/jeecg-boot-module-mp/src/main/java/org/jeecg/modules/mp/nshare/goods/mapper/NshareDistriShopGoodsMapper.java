package org.jeecg.modules.mp.nshare.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoods;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送店铺商品
 * @Author:
 * @Date: 2020-02-17
 * @Version: V1.0
 */
public interface NshareDistriShopGoodsMapper extends BaseMapper<NshareDistriShopGoods> {
    List<NshareDistriShopGoods> selectByMainId(@Param("mainId") String mainId, @Param("catId") String catId);
    List<Map> selectMapByMainId(@Param("mainId") String mainId, @Param("catId") String catId);
    IPage<Map> loadList4API(Page<Map> page, String shopId,String teamId, String catId, String key);
}
