package org.jeecg.modules.mp.nshare.goods.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoods;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送店铺商品
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
public interface INshareDistriShopGoodsService extends IService<NshareDistriShopGoods> {
    List<NshareDistriShopGoods> findShopGoods(String shopId, String catId);
    List<Map> findShopGoodsMap(String shopId, String catId);
    IPage<Map> loadList4API(String shopId,String teamId,String catId, int pageSize, int pageNo, String key);
}
