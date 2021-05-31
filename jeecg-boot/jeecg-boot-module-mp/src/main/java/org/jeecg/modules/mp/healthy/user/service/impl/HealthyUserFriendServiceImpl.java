package org.jeecg.modules.mp.healthy.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.healthy.user.entity.HealthyUserFriend;
import org.jeecg.modules.mp.healthy.user.mapper.HealthyUserFriendMapper;
import org.jeecg.modules.mp.healthy.user.service.IHealthyUserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class HealthyUserFriendServiceImpl extends ServiceImpl<HealthyUserFriendMapper, HealthyUserFriend> implements IHealthyUserFriendService {
    @Autowired
    private HealthyUserFriendMapper healthyUserFriendMapper;

    @Override
    public List<Map> getUserFriends(String userId) {
        return healthyUserFriendMapper.selectUserFriends(userId);
    }

    @Override
    public List<Map> getUserFriendReqs(String userId) {
        return healthyUserFriendMapper.selectUserFriendReqs(userId);
    }
}
