package org.jeecg.modules.mp.tlearn.kb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbQa;
import org.jeecg.modules.mp.tlearn.kb.mapper.TlKbQaMapper;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbQaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 问答类知识
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
@Service
public class TlKbQaServiceImpl extends ServiceImpl<TlKbQaMapper, TlKbQa> implements ITlKbQaService {
	@Autowired
	private TlKbQaMapper tlKbQaMapper;

	@Override
	public IPage<Map> loadList4API(int pageSize, int pageNo, String type, String openid, String searchKey, String shareId) {
		Page<Map> page = new Page<>(pageNo, pageSize);
		return tlKbQaMapper.loadList4API(page,type,openid,searchKey,shareId);
	}

	@Override
	public List<TlKbQa> listQaByType(String type) {
		return tlKbQaMapper.selectTypeQas(type);
	}
}
