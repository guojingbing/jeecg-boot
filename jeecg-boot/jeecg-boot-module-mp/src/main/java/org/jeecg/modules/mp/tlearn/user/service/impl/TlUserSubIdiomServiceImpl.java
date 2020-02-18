package org.jeecg.modules.mp.tlearn.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubIdiom;
import org.jeecg.modules.mp.tlearn.user.mapper.TlUserSubIdiomMapper;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserSubIdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 用户收藏的成语
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
@Service
public class TlUserSubIdiomServiceImpl extends ServiceImpl<TlUserSubIdiomMapper, TlUserSubIdiom> implements ITlUserSubIdiomService {
	
	@Autowired
	private TlUserSubIdiomMapper tlUserSubIdiomMapper;
	
	@Override
	public List<TlUserSubIdiom> selectByMainId(String mainId) {
		return tlUserSubIdiomMapper.selectByMainId(mainId);
	}
}
