package org.jeecg.modules.mp.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.stock.entity.StockProfit;

import java.util.List;
import java.util.Map;

/**
 * @Description: 证券季频成长信息
 * @Author:
 * @Date: 2020-02-17
 * @Version: V1.0
 */
public interface StockProfitMapper extends BaseMapper<StockProfit> {
    List<StockProfit> selectByType(@Param("code") String code,@Param("year") Integer year,@Param("quarter") Integer quarter);
    List<Map> selectMapByType(@Param("code") String code,@Param("year") Integer year,@Param("quarter") Integer quarter);
    IPage<Map> loadList4API(Page<Map> page,String code,Integer year,Integer quarter,String key);
}
