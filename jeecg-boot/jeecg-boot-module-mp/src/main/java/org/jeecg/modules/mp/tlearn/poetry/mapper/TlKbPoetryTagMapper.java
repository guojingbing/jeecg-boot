package org.jeecg.modules.mp.tlearn.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryTag;

import java.util.List;

/**
 * @Description: 古诗词标签
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
public interface TlKbPoetryTagMapper extends BaseMapper<TlKbPoetryTag> {

	boolean deleteByMainId(@Param("mainId") String mainId);
    
	List<TlKbPoetryTag> selectByMainId(@Param("mainId") String mainId);
}