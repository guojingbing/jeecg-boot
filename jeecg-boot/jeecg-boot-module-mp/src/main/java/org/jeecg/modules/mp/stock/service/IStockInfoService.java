package org.jeecg.modules.mp.stock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.stock.entity.StockInfo;

import java.util.List;
import java.util.Map;

/**
 * 证券业务
 */
public interface IStockInfoService extends IService<StockInfo> {
    /**
     * 按分类查询返回对象列表
     * @param type
     * @return
     */
    List<StockInfo> list(Integer type);
    /**
     * 按年份、季度获取没有相应成长信息的证券列表
     * 返回对象列表
     * @param year
     * @param quarter
     * @return
     */
    List<StockInfo> listNoProfit(Integer year,Integer quarter);
    /**
     * 按分类查询返回Map集合
     * @param type
     * @return
     */
    List<Map> listMap(Integer type);
    /**
     * 分页查询
     * @param type
     * @param pageSize
     * @param pageNo
     * @param key
     * @return
     */
    IPage<Map> loadList4API(Integer type, int pageSize, int pageNo, String key);

    /**
     * 从BaoStock更新证券基本信息
     */
    void upStockInfoFromBaoStock();
}
