package org.jeecg.modules.mp.healthy.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.mp.healthy.user.entity.HealthyUserSetting;

import java.util.Map;

/**
 * 用户健康设置
 */
public interface HealthyUserSettingMapper extends BaseMapper<HealthyUserSetting> {
    IPage<Map> loadList4API(Page<Map> page, String userId, Integer htype, String startDate, String endDate);
}
