package org.jeecg.modules.mp.tlearn.poetry.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetry;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryComment;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 拾贝古诗词知识库
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface ITlKbPoetryService extends IService<TlKbPoetry> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(TlKbPoetry tlKbPoetry, List<TlKbPoetryComment> tlKbPoetryCommentList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TlKbPoetry tlKbPoetry, List<TlKbPoetryComment> tlKbPoetryCommentList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);

	/**
	 * 诗词获取接口
	 * @param poeFormId
	 * @param dynId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public IPage<Map> loadList4API(String poeFormId, String dynId, int pageSize, int pageNo, String key, String tagStr, String bookId);

	/**
	 * 从古诗文网接口爬取诗词并保存
	 */
	void crawlPoetryAndSave();

	/**
	 * 查询重复诗词数据
	 * @return
	 */
	List<TlKbPoetry> listPoetryRepeat();

	/**
	 * 查询没有搜索排名的诗词
	 * @return
	 */
	List<TlKbPoetry> listPoetryNoRank(String rank);

	/**
	 * 删除重复数据
	 */
	void delPoetrysRepeat();
}
