package org.jeecg.modules.mp.healthy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.healthy.user.entity.HealthyUserFriend;

import java.util.List;
import java.util.Map;

/**
 * 用户亲友
 */
public interface IHealthyUserFriendService extends IService<HealthyUserFriend> {
    /**
     * 获取用户亲友
     * @param userId
     * @return
     */
    List<Map> getUserFriends(String userId);

    /**
     * 获取亲友授权请求
     * @param userId
     * @return
     */
    List<Map> getUserFriendReqs(String userId);
}
