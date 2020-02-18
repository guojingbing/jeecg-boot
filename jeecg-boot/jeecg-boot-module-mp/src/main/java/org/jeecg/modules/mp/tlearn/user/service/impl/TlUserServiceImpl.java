package org.jeecg.modules.mp.tlearn.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.user.entity.TlUser;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubIdiom;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubPoetry;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubTag;
import org.jeecg.modules.mp.tlearn.user.mapper.TlUserMapper;
import org.jeecg.modules.mp.tlearn.user.mapper.TlUserSubIdiomMapper;
import org.jeecg.modules.mp.tlearn.user.mapper.TlUserSubPoetryMapper;
import org.jeecg.modules.mp.tlearn.user.mapper.TlUserSubTagMapper;
import org.jeecg.modules.mp.tlearn.user.service.ITlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 小程序用户
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
@Service
public class TlUserServiceImpl extends ServiceImpl<TlUserMapper, TlUser> implements ITlUserService {

	@Autowired
	private TlUserMapper tlUserMapper;
	@Autowired
	private TlUserSubPoetryMapper tlUserSubPoetryMapper;
	@Autowired
	private TlUserSubIdiomMapper tlUserSubIdiomMapper;
	@Autowired
	private TlUserSubTagMapper tlUserSubTagMapper;
	
	@Override
	@Transactional
	public void saveMain(TlUser tlUser, List<TlUserSubPoetry> tlUserSubPoetryList, List<TlUserSubIdiom> tlUserSubIdiomList, List<TlUserSubTag> tlUserSubTagList) {
		tlUserMapper.insert(tlUser);
		if(tlUserSubPoetryList!=null && tlUserSubPoetryList.size()>0) {
			for(TlUserSubPoetry entity:tlUserSubPoetryList) {
				//外键设置
				entity.setUserId(tlUser.getId());
				tlUserSubPoetryMapper.insert(entity);
			}
		}
		if(tlUserSubIdiomList!=null && tlUserSubIdiomList.size()>0) {
			for(TlUserSubIdiom entity:tlUserSubIdiomList) {
				//外键设置
				entity.setUserId(tlUser.getId());
				tlUserSubIdiomMapper.insert(entity);
			}
		}
		if(tlUserSubTagList!=null && tlUserSubTagList.size()>0) {
			for(TlUserSubTag entity:tlUserSubTagList) {
				//外键设置
				entity.setUserId(tlUser.getId());
				tlUserSubTagMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(TlUser tlUser,List<TlUserSubPoetry> tlUserSubPoetryList,List<TlUserSubIdiom> tlUserSubIdiomList,List<TlUserSubTag> tlUserSubTagList) {
		tlUserMapper.updateById(tlUser);
		
		//1.先删除子表数据
		tlUserSubPoetryMapper.deleteByMainId(tlUser.getId());
		tlUserSubIdiomMapper.deleteByMainId(tlUser.getId());
		tlUserSubTagMapper.deleteByMainId(tlUser.getId());
		
		//2.子表数据重新插入
		if(tlUserSubPoetryList!=null && tlUserSubPoetryList.size()>0) {
			for(TlUserSubPoetry entity:tlUserSubPoetryList) {
				//外键设置
				entity.setUserId(tlUser.getId());
				tlUserSubPoetryMapper.insert(entity);
			}
		}
		if(tlUserSubIdiomList!=null && tlUserSubIdiomList.size()>0) {
			for(TlUserSubIdiom entity:tlUserSubIdiomList) {
				//外键设置
				entity.setUserId(tlUser.getId());
				tlUserSubIdiomMapper.insert(entity);
			}
		}
		if(tlUserSubTagList!=null && tlUserSubTagList.size()>0) {
			for(TlUserSubTag entity:tlUserSubTagList) {
				//外键设置
				entity.setUserId(tlUser.getId());
				tlUserSubTagMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		tlUserSubPoetryMapper.deleteByMainId(id);
		tlUserSubIdiomMapper.deleteByMainId(id);
		tlUserSubTagMapper.deleteByMainId(id);
		tlUserMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			tlUserSubPoetryMapper.deleteByMainId(id.toString());
			tlUserSubIdiomMapper.deleteByMainId(id.toString());
			tlUserSubTagMapper.deleteByMainId(id.toString());
			tlUserMapper.deleteById(id);
		}
	}
	
}
