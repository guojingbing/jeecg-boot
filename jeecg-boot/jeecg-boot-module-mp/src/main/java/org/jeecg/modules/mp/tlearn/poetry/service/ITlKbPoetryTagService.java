package org.jeecg.modules.mp.tlearn.poetry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryTag;

import java.util.List;

/**
 * @Description: 古诗词标签
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
public interface ITlKbPoetryTagService extends IService<TlKbPoetryTag> {

	public List<TlKbPoetryTag> selectByMainId(String mainId);
}
