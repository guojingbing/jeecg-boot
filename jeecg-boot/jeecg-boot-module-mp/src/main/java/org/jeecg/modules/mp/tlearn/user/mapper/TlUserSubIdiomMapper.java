package org.jeecg.modules.mp.tlearn.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubIdiom;

import java.util.List;

/**
 * @Description: 用户收藏的成语
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
public interface TlUserSubIdiomMapper extends BaseMapper<TlUserSubIdiom> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<TlUserSubIdiom> selectByMainId(@Param("mainId") String mainId);
}
