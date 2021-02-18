package org.jeecg.modules.mp.nshare.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopPromotion;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享店铺营销活动
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareDistriShopPromotionService extends IService<NshareDistriShopPromotion> {
    /**
     * 添加
     */
    void saveMain(NshareDistriShopPromotion nshareDistriShopPromotion) ;

    IPage<Map> loadList4API(int pageSize, int pageNo, String shopId, Integer type, Integer auditType,String teamId,String searchKey);

    List<NshareDistriShopPromotion> loadPromosByGoodsId(String goodsId);

    boolean canAddPromos(String goodsId, String startTime, String endTime);
}
