package org.jeecg.modules.mp.tlearn.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubQa;
import org.jeecg.modules.mp.tlearn.user.mapper.TlUserSubQaMapper;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserSubQaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 用户订阅的问答
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
@Service
public class TlUserSubQaServiceImpl extends ServiceImpl<TlUserSubQaMapper, TlUserSubQa> implements ITlUserSubQaService {
	
	@Autowired
	private TlUserSubQaMapper tlUserSubQaMapper;
	
	@Override
	public List<TlUserSubQa> selectByQaId(String jokeId) {
		return tlUserSubQaMapper.selectByQaId(jokeId);
	}

	@Override
	public TlUserSubQa selectUserQaById(String qaId, String openid){
		return tlUserSubQaMapper.selectUserQaById(qaId,openid);
	}
}
