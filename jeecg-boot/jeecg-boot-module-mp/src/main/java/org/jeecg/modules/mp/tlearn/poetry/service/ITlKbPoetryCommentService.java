package org.jeecg.modules.mp.tlearn.poetry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryComment;

import java.util.List;

/**
 * @Description: 古诗词赏析评论
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
public interface ITlKbPoetryCommentService extends IService<TlKbPoetryComment> {

	public List<TlKbPoetryComment> selectByMainId(String mainId);
}
