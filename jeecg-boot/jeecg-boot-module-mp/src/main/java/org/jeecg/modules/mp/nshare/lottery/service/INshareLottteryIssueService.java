package org.jeecg.modules.mp.nshare.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssue;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssuePrize;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 社区分享彩票期数
 * @Author:
 * @Date:   2020-02-16
 * @Version: V1.0
 */
public interface INshareLottteryIssueService extends IService<NshareLottteryIssue> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(NshareLottteryIssue nshareLottteryIssue, List<NshareLottteryIssuePrize> nshareLottteryIssuePrizeList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(NshareLottteryIssue nshareLottteryIssue, List<NshareLottteryIssuePrize> nshareLottteryIssuePrizeList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
