package org.jeecg.modules.mp.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.stock.entity.StockKline;

import java.util.List;
import java.util.Map;

/**
 * @Description: 证券日频估值
 * @Author:
 * @Date: 2020-02-17
 * @Version: V1.0
 */
public interface StockKlineMapper extends BaseMapper<StockKline> {
    List<StockKline> selectByDateRange(@Param("code") String code, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("frequency") String frequency);
    List<Map> selectMapByDateRange(@Param("code") String code, @Param("startDate") String startDate, @Param("endDate") String endDate);
    IPage<Map> loadList4API(Page<Map> page, String code, String startDate, String endDate, String key);
}
