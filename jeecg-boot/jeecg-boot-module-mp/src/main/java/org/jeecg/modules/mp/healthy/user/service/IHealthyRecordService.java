package org.jeecg.modules.mp.healthy.user.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.healthy.user.entity.HealthyRecord;

import java.util.Map;

/**
 * 用户健康信息采集记录
 */
public interface IHealthyRecordService extends IService<HealthyRecord> {
    void saveRecBatch(JSONArray objArr);
    IPage<Map> loadList4API(int pageSize, int pageNo, String userId, Integer htype, String startDate, String endDate);
}
