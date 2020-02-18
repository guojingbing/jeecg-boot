package org.jeecg.modules.mp.tlearn.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubQa;

import java.util.List;

/**
 * @Description: 用户订阅的问答
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
public interface TlUserSubQaMapper extends BaseMapper<TlUserSubQa> {

	boolean deleteByMainId(@Param("mainId") String mainId);
    
	List<TlUserSubQa> selectByQaId(@Param("qaId") String qaId);

	TlUserSubQa selectUserQaById(@Param("qaId") String qaId, @Param("openid") String openid);
}
