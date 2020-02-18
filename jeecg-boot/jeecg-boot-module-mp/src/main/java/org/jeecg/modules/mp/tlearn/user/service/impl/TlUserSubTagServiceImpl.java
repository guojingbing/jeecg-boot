package org.jeecg.modules.mp.tlearn.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubTag;
import org.jeecg.modules.mp.tlearn.user.mapper.TlUserSubTagMapper;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserSubTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 用户订阅的诗词分类
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
@Service
public class TlUserSubTagServiceImpl extends ServiceImpl<TlUserSubTagMapper, TlUserSubTag> implements ITlUserSubTagService {
	
	@Autowired
	private TlUserSubTagMapper tlUserSubTagMapper;
	
	@Override
	public List<TlUserSubTag> selectByMainId(String mainId) {
		return tlUserSubTagMapper.selectByMainId(mainId);
	}
}
