package org.jeecg.modules.mp.tlearn.kb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbQaThumbs;

import java.util.List;

/**
 * @Description: 问答评价
 * @Author:
 * @Date:   2020-02-09
 * @Version: V1.0
 */
public interface TlKbQaThumbsMapper extends BaseMapper<TlKbQaThumbs> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<TlKbQaThumbs> selectByMainId(@Param("mainId") String mainId);

	TlKbQaThumbs selectQaThumbsByOpenid(@Param("mainId") String mainId, @Param("openid") String openid);
}
