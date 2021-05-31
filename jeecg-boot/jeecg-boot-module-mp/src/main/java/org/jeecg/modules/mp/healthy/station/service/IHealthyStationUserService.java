package org.jeecg.modules.mp.healthy.station.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.healthy.station.entity.HealthyStationUser;

import java.util.List;
import java.util.Map;

/**
 * 服务站用户
 */
public interface IHealthyStationUserService extends IService<HealthyStationUser> {
    /**
     * 获取服务站用户
     * @param stationId
     * @return
     */
    List<Map> getStationUsers(Integer stationId);

    /**
     * 获取用户服务站
     * @param userId
     * @return
     */
    List<Map> getUserStations(String userId);
}
