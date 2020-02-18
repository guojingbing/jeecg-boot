package org.jeecg.modules.mp.tlearn.joke.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJoke;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJokeThumbs;
import org.jeecg.modules.mp.tlearn.joke.mapper.TlKbJokeMapper;
import org.jeecg.modules.mp.tlearn.joke.mapper.TlKbJokeThumbsMapper;
import org.jeecg.modules.mp.tlearn.joke.service.ITlKbJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 笑话段子
 * @Author:
 * @Date:   2020-02-09
 * @Version: V1.0
 */
@Service
public class TlKbJokeServiceImpl extends ServiceImpl<TlKbJokeMapper, TlKbJoke> implements ITlKbJokeService {

	@Autowired
	private TlKbJokeMapper tlKbJokeMapper;
	@Autowired
	private TlKbJokeThumbsMapper tlKbJokeThumbsMapper;
	
	@Override
	@Transactional
	public void saveMain(TlKbJoke tlKbJoke, List<TlKbJokeThumbs> tlKbJokeThumbsList) {
		tlKbJokeMapper.insert(tlKbJoke);
		if(tlKbJokeThumbsList!=null && tlKbJokeThumbsList.size()>0) {
			for(TlKbJokeThumbs entity:tlKbJokeThumbsList) {
				//外键设置
				entity.setJokeId(tlKbJoke.getId());
				tlKbJokeThumbsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(TlKbJoke tlKbJoke,List<TlKbJokeThumbs> tlKbJokeThumbsList) {
		tlKbJokeMapper.updateById(tlKbJoke);
		
		//1.先删除子表数据
		tlKbJokeThumbsMapper.deleteByMainId(tlKbJoke.getId());
		
		//2.子表数据重新插入
		if(tlKbJokeThumbsList!=null && tlKbJokeThumbsList.size()>0) {
			for(TlKbJokeThumbs entity:tlKbJokeThumbsList) {
				//外键设置
				entity.setJokeId(tlKbJoke.getId());
				tlKbJokeThumbsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		tlKbJokeThumbsMapper.deleteByMainId(id);
		tlKbJokeMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			tlKbJokeThumbsMapper.deleteByMainId(id.toString());
			tlKbJokeMapper.deleteById(id);
		}
	}

	@Override
	public IPage<Map> loadList4API(int pageSize, int pageNo, String openid,String searchKey) {
		Page<Map> page = new Page<>(pageNo, pageSize);
		return tlKbJokeMapper.loadList4API(page,openid,searchKey);
	}
	
}
