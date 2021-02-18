package org.jeecg.modules.mp.nshare.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.user.entity.NshareUserCart;
import org.jeecg.modules.mp.nshare.user.mapper.NshareUserCartMapper;
import org.jeecg.modules.mp.nshare.user.service.INshareUserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享用户购物车
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareUserCartServiceImpl extends ServiceImpl<NshareUserCartMapper, NshareUserCart> implements INshareUserCartService {
    @Autowired
    private NshareUserCartMapper nshareUserCartMapper;
    @Override
    public IPage<Map> loadList4API(int pageSize, int pageNo, String userId, String stationId, String goodsId) {
        Page<Map> page = new Page<>(pageNo, pageSize);
        return nshareUserCartMapper.loadList4API(page,userId,stationId,goodsId);
    }

    @Override
    public List<NshareUserCart> selectByMainId(String userId,String stationId){
        return nshareUserCartMapper.selectByMainId(userId,stationId);
    }
}
