package org.jeecg.modules.mp.nshare.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrderGoods;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送订单商品
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareDistriOrderGoodsService extends IService<NshareDistriOrderGoods> {
	List<NshareDistriOrderGoods> selectByMainId(String mainId);
	List<Map> selectByOrderId(String mainId);
	IPage<Map> loadSoldList4API(int pageSize, int pageNo, String shopId, String pickDate, String searchKey);
}
