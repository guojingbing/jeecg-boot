package org.jeecg.modules.mp.tlearn.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.user.entity.TlUser;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubIdiom;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubPoetry;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubTag;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 小程序用户
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
public interface ITlUserService extends IService<TlUser> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(TlUser tlUser, List<TlUserSubPoetry> tlUserSubPoetryList, List<TlUserSubIdiom> tlUserSubIdiomList, List<TlUserSubTag> tlUserSubTagList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TlUser tlUser, List<TlUserSubPoetry> tlUserSubPoetryList, List<TlUserSubIdiom> tlUserSubIdiomList, List<TlUserSubTag> tlUserSubTagList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
