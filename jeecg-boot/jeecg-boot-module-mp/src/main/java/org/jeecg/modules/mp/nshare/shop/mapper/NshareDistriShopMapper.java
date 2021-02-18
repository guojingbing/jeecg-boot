package org.jeecg.modules.mp.nshare.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShop;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送店铺
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface NshareDistriShopMapper extends BaseMapper<NshareDistriShop> {
    IPage<Map> loadList4API(Page<Map> page, BigDecimal lng, BigDecimal lat, String searchKey);
    List<NshareDistriShop> selectByOwnerId(@Param("ownerId") String ownerId);
}
