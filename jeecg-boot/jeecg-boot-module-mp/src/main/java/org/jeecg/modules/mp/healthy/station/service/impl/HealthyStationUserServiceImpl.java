package org.jeecg.modules.mp.healthy.station.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.healthy.station.entity.HealthyStationUser;
import org.jeecg.modules.mp.healthy.station.mapper.HealthyStationUserMapper;
import org.jeecg.modules.mp.healthy.station.service.IHealthyStationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HealthyStationUserServiceImpl extends ServiceImpl<HealthyStationUserMapper, HealthyStationUser> implements IHealthyStationUserService {
    @Autowired
    private HealthyStationUserMapper healthyStationUserMapper;

    @Override
    public List<Map> getStationUsers(Integer stationId) {
        return healthyStationUserMapper.selectStationUsers(stationId);
    }

    @Override
    public List<Map> getUserStations(String userId) {
        return healthyStationUserMapper.selectUserStations(userId);
    }
}
