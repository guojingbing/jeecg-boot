package org.jeecg.modules.mp.tlearn.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryComment;

import java.util.List;

/**
 * @Description: 古诗词赏析评论
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
public interface TlKbPoetryCommentMapper extends BaseMapper<TlKbPoetryComment> {

	boolean deleteByMainId(@Param("mainId") String mainId);
    
	List<TlKbPoetryComment> selectByMainId(@Param("mainId") String mainId);
}
