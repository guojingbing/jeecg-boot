package org.jeecg.modules.mp.tlearn.kb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbCourseKnowledge;
import org.jeecg.modules.mp.tlearn.kb.mapper.TlKbCourseKnowledgeMapper;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbCourseKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
@Service
public class TlKbCourseKnowledgeServiceImpl extends ServiceImpl<TlKbCourseKnowledgeMapper, TlKbCourseKnowledge> implements ITlKbCourseKnowledgeService {
	@Autowired
	private TlKbCourseKnowledgeMapper mapper;
}
