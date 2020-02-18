package org.jeecg.modules.mp.tlearn.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubPoetry;
import org.jeecg.modules.mp.tlearn.user.mapper.TlUserSubPoetryMapper;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserSubPoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 用户收藏的诗词
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
@Service
public class TlUserSubPoetryServiceImpl extends ServiceImpl<TlUserSubPoetryMapper, TlUserSubPoetry> implements ITlUserSubPoetryService {
	
	@Autowired
	private TlUserSubPoetryMapper tlUserSubPoetryMapper;
	
	@Override
	public List<TlUserSubPoetry> selectByMainId(String mainId) {
		return tlUserSubPoetryMapper.selectByMainId(mainId);
	}
}
