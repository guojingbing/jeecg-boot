package org.jeecg.modules.mp.tlearn.kb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbCourseQuestionBank;
import org.jeecg.modules.mp.tlearn.kb.mapper.TlKbCourseQuestionBankMapper;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbCourseQuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
@Service
public class TlKbCourseQuestionBankServiceImpl extends ServiceImpl<TlKbCourseQuestionBankMapper, TlKbCourseQuestionBank> implements ITlKbCourseQuestionBankService {
	@Autowired
	private TlKbCourseQuestionBankMapper mapper;
}
