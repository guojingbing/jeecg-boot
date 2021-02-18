package org.jeecg.modules.mp.tlearn.kb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKb;
import org.jeecg.modules.mp.tlearn.kb.mapper.TlKbMapper;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 知识库
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
@Service
public class TlKbServiceImpl extends ServiceImpl<TlKbMapper, TlKb> implements ITlKbService {
	@Autowired
	private TlKbMapper tlKbMapper;
}
