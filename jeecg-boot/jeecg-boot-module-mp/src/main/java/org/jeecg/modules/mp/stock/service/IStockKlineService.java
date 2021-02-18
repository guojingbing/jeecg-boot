package org.jeecg.modules.mp.stock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.stock.entity.StockKline;

import java.util.List;
import java.util.Map;

/**
 * 证券日频估值业务
 */
public interface IStockKlineService extends IService<StockKline> {
    /**
     * 查询指定证券的日频估值信息列表
     * @param code
     * @param startDate
     * @param endDate
     * @param frequency
     * @return
     */
    List<StockKline> list(String code, String startDate, String endDate,String frequency);
    /**
     * 查询指定证券的日频估值信息返回Map集合
     * @param code
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map> listMap(String code, String startDate, String endDate);
    /**
     * 分页查询
     * @param code
     * @param pageSize
     * @param pageNo
     * @param key
     * @return
     */
    IPage<Map> loadList4API(String code, String startDate, String endDate, int pageSize, int pageNo, String key);

    /**
     * 从BaoStock更新证券日频估值
     * @param code
     * @param startDate
     * @param endDate
     * @param frequency 数据类型，默认为d，日k线；d=日k线、w=周、m=月、5=5分钟、15=15分钟、30=30分钟、60=60分钟k线数据，不区分大小写；指数没有分钟线数据；周线每周最后一个交易日才可以获取，月线每月最后一个交易日才可以获取。
     * @param adjustflag 复权类型，默认不复权：3；1：后复权；2：前复权。已支持分钟线、日线、周线、月线前后复权。
     */
    void syncStockValuationFromBaoStock(String code, String startDate, String endDate,String frequency,String adjustflag);
}
