package org.jeecg.modules.mp.tlearn.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryTag;
import org.jeecg.modules.mp.tlearn.poetry.mapper.TlKbPoetryTagMapper;
import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 古诗词标签
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Service
public class TlKbPoetryTagServiceImpl extends ServiceImpl<TlKbPoetryTagMapper, TlKbPoetryTag> implements ITlKbPoetryTagService {
	
	@Autowired
	private TlKbPoetryTagMapper tlKbPoetryTagMapper;
	
	@Override
	public List<TlKbPoetryTag> selectByMainId(String mainId) {
		return tlKbPoetryTagMapper.selectByMainId(mainId);
	}
}
