package org.jeecg.modules.mp.tlearn.kb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbCourseVolume;
import org.jeecg.modules.mp.tlearn.kb.mapper.TlKbCourseVolumeMapper;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbCourseVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
@Service
public class TlKbCourseVolumeServiceImpl extends ServiceImpl<TlKbCourseVolumeMapper, TlKbCourseVolume> implements ITlKbCourseVolumeService {
	@Autowired
	private TlKbCourseVolumeMapper mapper;

	@Override
	public List<TlKbCourseVolume> listCourseVolume(String fkCode){
		return mapper.selectCourseVolume(fkCode);
	}
}
