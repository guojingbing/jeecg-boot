package org.jeecg.modules.mp.nshare.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssue;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssuePrize;
import org.jeecg.modules.mp.nshare.lottery.mapper.NshareLottteryIssueMapper;
import org.jeecg.modules.mp.nshare.lottery.mapper.NshareLottteryIssuePrizeMapper;
import org.jeecg.modules.mp.nshare.lottery.service.INshareLottteryIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 社区分享彩票期数
 * @Author:
 * @Date:   2020-02-16
 * @Version: V1.0
 */
@Service
public class NshareLottteryIssueServiceImpl extends ServiceImpl<NshareLottteryIssueMapper, NshareLottteryIssue> implements INshareLottteryIssueService {

	@Autowired
	private NshareLottteryIssueMapper nshareLottteryIssueMapper;
	@Autowired
	private NshareLottteryIssuePrizeMapper nshareLottteryIssuePrizeMapper;
	
	@Override
	@Transactional
	public void saveMain(NshareLottteryIssue nshareLottteryIssue, List<NshareLottteryIssuePrize> nshareLottteryIssuePrizeList) {
		nshareLottteryIssueMapper.insert(nshareLottteryIssue);
		if(nshareLottteryIssuePrizeList!=null && nshareLottteryIssuePrizeList.size()>0) {
			for(NshareLottteryIssuePrize entity:nshareLottteryIssuePrizeList) {
				//外键设置
				entity.setIssueId(nshareLottteryIssue.getId());
				nshareLottteryIssuePrizeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(NshareLottteryIssue nshareLottteryIssue,List<NshareLottteryIssuePrize> nshareLottteryIssuePrizeList) {
		nshareLottteryIssueMapper.updateById(nshareLottteryIssue);
		
		//1.先删除子表数据
		nshareLottteryIssuePrizeMapper.deleteByMainId(nshareLottteryIssue.getId());
		
		//2.子表数据重新插入
		if(nshareLottteryIssuePrizeList!=null && nshareLottteryIssuePrizeList.size()>0) {
			for(NshareLottteryIssuePrize entity:nshareLottteryIssuePrizeList) {
				//外键设置
				entity.setIssueId(nshareLottteryIssue.getId());
				nshareLottteryIssuePrizeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		nshareLottteryIssuePrizeMapper.deleteByMainId(id);
		nshareLottteryIssueMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			nshareLottteryIssuePrizeMapper.deleteByMainId(id.toString());
			nshareLottteryIssueMapper.deleteById(id);
		}
	}
	
}
