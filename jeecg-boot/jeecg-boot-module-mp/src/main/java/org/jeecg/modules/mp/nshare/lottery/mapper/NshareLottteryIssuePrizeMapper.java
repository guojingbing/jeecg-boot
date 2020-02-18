package org.jeecg.modules.mp.nshare.lottery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssuePrize;

import java.util.List;

/**
 * @Description: 社区分享彩票奖项
 * @Author:
 * @Date:   2020-02-16
 * @Version: V1.0
 */
public interface NshareLottteryIssuePrizeMapper extends BaseMapper<NshareLottteryIssuePrize> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<NshareLottteryIssuePrize> selectByMainId(@Param("mainId") String mainId);
}
