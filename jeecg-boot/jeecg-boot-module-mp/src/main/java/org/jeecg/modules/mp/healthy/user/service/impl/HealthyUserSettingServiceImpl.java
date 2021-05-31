package org.jeecg.modules.mp.healthy.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.healthy.user.entity.HealthyUserSetting;
import org.jeecg.modules.mp.healthy.user.mapper.HealthyUserSettingMapper;
import org.jeecg.modules.mp.healthy.user.service.IHealthyUserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class HealthyUserSettingServiceImpl extends ServiceImpl<HealthyUserSettingMapper, HealthyUserSetting> implements IHealthyUserSettingService {
    @Autowired
    private HealthyUserSettingMapper healthyUserSettingMapper;
}
