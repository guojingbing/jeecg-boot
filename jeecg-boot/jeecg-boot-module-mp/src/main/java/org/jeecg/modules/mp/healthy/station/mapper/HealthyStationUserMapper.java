package org.jeecg.modules.mp.healthy.station.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.healthy.station.entity.HealthyStationUser;

import java.util.List;
import java.util.Map;

/**
 * 服务站用户
 */
public interface HealthyStationUserMapper extends BaseMapper<HealthyStationUser> {
    List<Map> selectStationUsers(@Param("stationId") Integer stationId);
    List<Map> selectUserStations(@Param("userId") String userId);
}
