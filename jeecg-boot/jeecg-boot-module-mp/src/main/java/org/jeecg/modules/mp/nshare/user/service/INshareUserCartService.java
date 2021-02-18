package org.jeecg.modules.mp.nshare.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.user.entity.NshareUserCart;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享用户购物车
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareUserCartService extends IService<NshareUserCart> {
    IPage<Map> loadList4API(int pageSize, int pageNo, String userId, String stationId, String goodsId);

    List<NshareUserCart> selectByMainId(String userId,String stationId);
}
