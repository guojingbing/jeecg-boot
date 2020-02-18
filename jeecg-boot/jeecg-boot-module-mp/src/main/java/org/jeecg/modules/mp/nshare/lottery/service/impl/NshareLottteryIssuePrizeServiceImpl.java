package org.jeecg.modules.mp.nshare.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssuePrize;
import org.jeecg.modules.mp.nshare.lottery.mapper.NshareLottteryIssuePrizeMapper;
import org.jeecg.modules.mp.nshare.lottery.service.INshareLottteryIssuePrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 社区分享彩票奖项
 * @Author:
 * @Date:   2020-02-16
 * @Version: V1.0
 */
@Service
public class NshareLottteryIssuePrizeServiceImpl extends ServiceImpl<NshareLottteryIssuePrizeMapper, NshareLottteryIssuePrize> implements INshareLottteryIssuePrizeService {
	
	@Autowired
	private NshareLottteryIssuePrizeMapper nshareLottteryIssuePrizeMapper;
	
	@Override
	public List<NshareLottteryIssuePrize> selectByMainId(String mainId) {
		return nshareLottteryIssuePrizeMapper.selectByMainId(mainId);
	}
}
