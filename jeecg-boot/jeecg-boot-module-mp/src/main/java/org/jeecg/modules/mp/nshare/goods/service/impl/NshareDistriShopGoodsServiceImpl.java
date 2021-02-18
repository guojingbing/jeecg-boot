package org.jeecg.modules.mp.nshare.goods.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoods;
import org.jeecg.modules.mp.nshare.goods.mapper.NshareDistriShopGoodsMapper;
import org.jeecg.modules.mp.nshare.goods.service.INshareDistriShopGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送店铺商品
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Service
public class NshareDistriShopGoodsServiceImpl extends ServiceImpl<NshareDistriShopGoodsMapper, NshareDistriShopGoods> implements INshareDistriShopGoodsService {
    @Autowired
    private NshareDistriShopGoodsMapper nshareDistriShopGoodsMapper;
    @Override
    public List<NshareDistriShopGoods> findShopGoods(String shopId, String catId){
        return nshareDistriShopGoodsMapper.selectByMainId(shopId, catId);
    }
    @Override
    public List<Map> findShopGoodsMap(String shopId, String catId){
        return nshareDistriShopGoodsMapper.selectMapByMainId(shopId, catId);
    }
    @Override
    public IPage<Map> loadList4API(String shopId,String teamId, String catId, int pageSize, int pageNo, String key) {
        Page<Map> page = new Page<>(pageNo, pageSize);
        return nshareDistriShopGoodsMapper.loadList4API(page,shopId,teamId,catId,key);
    }
}
