package org.jeecg.modules.mp.tlearn.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubTag;

import java.util.List;

/**
 * @Description: 用户订阅的诗词分类
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
public interface TlUserSubTagMapper extends BaseMapper<TlUserSubTag> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<TlUserSubTag> selectByMainId(@Param("mainId") String mainId);
}
