package org.jeecg.modules.mp.tlearn.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubPoetry;

import java.util.List;

/**
 * @Description: 用户收藏的诗词
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
public interface ITlUserSubPoetryService extends IService<TlUserSubPoetry> {

	public List<TlUserSubPoetry> selectByMainId(String mainId);
}
