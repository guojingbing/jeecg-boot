package org.jeecg.modules.mp.tlearn.kb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbCourse;
import org.jeecg.modules.mp.tlearn.kb.mapper.TlKbCourseMapper;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 知识库课程
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
@Service
public class TlKbCourseServiceImpl extends ServiceImpl<TlKbCourseMapper, TlKbCourse> implements ITlKbCourseService {
	@Autowired
	private TlKbCourseMapper tlKbCourseMapper;
}
