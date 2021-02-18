package org.jeecg.modules.mp.nshare.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopPromotion;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享店铺营销活动
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface NshareDistriShopPromotionMapper extends BaseMapper<NshareDistriShopPromotion> {
    IPage<Map> loadList4API(Page<Map> page, String shopId, Integer hisType, Integer auditType,String teamId, String searchKey);
    List<NshareDistriShopPromotion> selectByGoodsId(String goodsId);
    List<NshareDistriShopPromotion> selectByGoodsIdRange(String goodsId, String startTime, String endTime);
}
