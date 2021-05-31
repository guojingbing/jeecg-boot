package org.jeecg.modules.mp.healthy.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.healthy.user.entity.HealthyUserFriend;

import java.util.List;
import java.util.Map;

/**
 * 用户亲友业务
 */
public interface HealthyUserFriendMapper extends BaseMapper<HealthyUserFriend> {
    List<Map> selectUserFriends(@Param("userId") String userId);
    List<Map> selectUserFriendReqs(@Param("userId") String userId);
}
