package org.jeecg.modules.mp.tlearn.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubJoke;

import java.util.List;

/**
 * @Description: 用户订阅的段子
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
public interface TlUserSubJokeMapper extends BaseMapper<TlUserSubJoke> {

	boolean deleteByMainId(@Param("mainId") String mainId);
    
	List<TlUserSubJoke> selectByJokeId(@Param("jokeId") String jokeId);

	TlUserSubJoke selectUserJokeByJokeId(@Param("jokeId") String jokeId, @Param("openid") String openid);
}
