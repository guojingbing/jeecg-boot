package org.jeecg.modules.mp.tlearn.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubJoke;

import java.util.List;

/**
 * @Description: 用户订阅的段子
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
public interface ITlUserSubJokeService extends IService<TlUserSubJoke> {
	List<TlUserSubJoke> selectByJokeId(String jokeId);

	TlUserSubJoke selectUserJokeByJokeId(String jokeId, String openid);
}
