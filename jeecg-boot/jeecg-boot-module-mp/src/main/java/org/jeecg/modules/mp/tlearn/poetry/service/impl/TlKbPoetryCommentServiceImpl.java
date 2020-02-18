package org.jeecg.modules.mp.tlearn.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryComment;
import org.jeecg.modules.mp.tlearn.poetry.mapper.TlKbPoetryCommentMapper;
import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 古诗词赏析评论
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Service
public class TlKbPoetryCommentServiceImpl extends ServiceImpl<TlKbPoetryCommentMapper, TlKbPoetryComment> implements ITlKbPoetryCommentService {
	
	@Autowired
	private TlKbPoetryCommentMapper tlKbPoetryCommentMapper;
	
	@Override
	public List<TlKbPoetryComment> selectByMainId(String mainId) {
		return tlKbPoetryCommentMapper.selectByMainId(mainId);
	}
}
