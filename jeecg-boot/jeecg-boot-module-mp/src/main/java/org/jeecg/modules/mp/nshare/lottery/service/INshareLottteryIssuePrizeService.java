package org.jeecg.modules.mp.nshare.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssuePrize;

import java.util.List;

/**
 * @Description: 社区分享彩票奖项
 * @Author:
 * @Date:   2020-02-16
 * @Version: V1.0
 */
public interface INshareLottteryIssuePrizeService extends IService<NshareLottteryIssuePrize> {

	public List<NshareLottteryIssuePrize> selectByMainId(String mainId);
}
