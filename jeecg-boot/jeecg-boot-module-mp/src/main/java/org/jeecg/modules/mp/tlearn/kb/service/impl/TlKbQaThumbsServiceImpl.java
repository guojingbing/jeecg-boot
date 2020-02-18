package org.jeecg.modules.mp.tlearn.kb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbQaThumbs;
import org.jeecg.modules.mp.tlearn.kb.mapper.TlKbQaThumbsMapper;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbQaThumbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 问答评价
 * @Author:
 * @Date:   2020-02-09
 * @Version: V1.0
 */
@Service
public class TlKbQaThumbsServiceImpl extends ServiceImpl<TlKbQaThumbsMapper, TlKbQaThumbs> implements ITlKbQaThumbsService {
	
	@Autowired
	private TlKbQaThumbsMapper tlKbQaThumbsMapper;
	
	@Override
	public List<TlKbQaThumbs> selectByMainId(String mainId) {
		return tlKbQaThumbsMapper.selectByMainId(mainId);
	}


	public TlKbQaThumbs selectQaThumbsByOpenid(String mainId,String openId){
		return tlKbQaThumbsMapper.selectQaThumbsByOpenid(mainId,openId);
	}
}
