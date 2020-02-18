package org.jeecg.modules.mp.tlearn.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubJoke;
import org.jeecg.modules.mp.tlearn.user.mapper.TlUserSubJokeMapper;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserSubJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 用户订阅的段子
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
@Service
public class TlUserSubJokeServiceImpl extends ServiceImpl<TlUserSubJokeMapper, TlUserSubJoke> implements ITlUserSubJokeService {
	
	@Autowired
	private TlUserSubJokeMapper tlUserSubJokeMapper;
	
	@Override
	public List<TlUserSubJoke> selectByJokeId(String jokeId) {
		return tlUserSubJokeMapper.selectByJokeId(jokeId);
	}

	@Override
	public TlUserSubJoke selectUserJokeByJokeId(String jokeId, String openid){
		return tlUserSubJokeMapper.selectUserJokeByJokeId(jokeId,openid);
	}
}
