package org.jeecg.modules.mp.tlearn.kb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbCourseChapter;
import org.jeecg.modules.mp.tlearn.kb.mapper.TlKbCourseChapterMapper;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbCourseChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
@Service
public class TlKbCourseChapterServiceImpl extends ServiceImpl<TlKbCourseChapterMapper, TlKbCourseChapter> implements ITlKbCourseChapterService {
	@Autowired
	private TlKbCourseChapterMapper mapper;
}
