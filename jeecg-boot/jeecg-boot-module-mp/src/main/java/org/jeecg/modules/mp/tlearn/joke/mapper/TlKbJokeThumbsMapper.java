package org.jeecg.modules.mp.tlearn.joke.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJokeThumbs;

import java.util.List;

/**
 * @Description: 笑话评价
 * @Author:
 * @Date:   2020-02-09
 * @Version: V1.0
 */
public interface TlKbJokeThumbsMapper extends BaseMapper<TlKbJokeThumbs> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<TlKbJokeThumbs> selectByMainId(@Param("mainId") String mainId);

	TlKbJokeThumbs selectJokeThumbsByOpenid(@Param("mainId") String mainId, @Param("openid") String openid);
}
