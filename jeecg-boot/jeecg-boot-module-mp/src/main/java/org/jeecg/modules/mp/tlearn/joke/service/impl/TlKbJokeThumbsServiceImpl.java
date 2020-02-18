package org.jeecg.modules.mp.tlearn.joke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJokeThumbs;
import org.jeecg.modules.mp.tlearn.joke.mapper.TlKbJokeThumbsMapper;
import org.jeecg.modules.mp.tlearn.joke.service.ITlKbJokeThumbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 笑话评价
 * @Author:
 * @Date:   2020-02-09
 * @Version: V1.0
 */
@Service
public class TlKbJokeThumbsServiceImpl extends ServiceImpl<TlKbJokeThumbsMapper, TlKbJokeThumbs> implements ITlKbJokeThumbsService {
	
	@Autowired
	private TlKbJokeThumbsMapper tlKbJokeThumbsMapper;
	
	@Override
	public List<TlKbJokeThumbs> selectByMainId(String mainId) {
		return tlKbJokeThumbsMapper.selectByMainId(mainId);
	}


	public TlKbJokeThumbs selectJokeThumbsByOpenid(String mainId,String openId){
		return tlKbJokeThumbsMapper.selectJokeThumbsByOpenid(mainId,openId);
	}
}
