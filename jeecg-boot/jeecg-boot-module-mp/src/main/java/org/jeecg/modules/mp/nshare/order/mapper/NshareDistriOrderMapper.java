package org.jeecg.modules.mp.nshare.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrder;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送订单
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface NshareDistriOrderMapper extends BaseMapper<NshareDistriOrder> {
    IPage<Map> loadList4API(Page<Map> page, String shopId, String userId, String teamId, String status, String searchKey);
    List<NshareDistriOrder> selectOrderByDate(@Param("pickDate") String pickDate);
}
