package org.jeecg.modules.mp.tlearn.kb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbCourseVersion;
import org.jeecg.modules.mp.tlearn.kb.mapper.TlKbCourseVersionMapper;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbCourseVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
@Service
public class TlKbCourseVersionServiceImpl extends ServiceImpl<TlKbCourseVersionMapper, TlKbCourseVersion> implements ITlKbCourseVersionService {
	@Autowired
	private TlKbCourseVersionMapper mapper;
}
