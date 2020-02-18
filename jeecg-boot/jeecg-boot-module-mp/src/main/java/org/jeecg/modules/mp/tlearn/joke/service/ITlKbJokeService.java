package org.jeecg.modules.mp.tlearn.joke.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJoke;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJokeThumbs;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 笑话段子
 * @Author:
 * @Date:   2020-02-09
 * @Version: V1.0
 */
public interface ITlKbJokeService extends IService<TlKbJoke> {
	IPage<Map> loadList4API(int pageSize, int pageNo, String openid,String searchKey);
	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(TlKbJoke tlKbJoke, List<TlKbJokeThumbs> tlKbJokeThumbsList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TlKbJoke tlKbJoke, List<TlKbJokeThumbs> tlKbJokeThumbsList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
