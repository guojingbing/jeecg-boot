package org.jeecg.modules.mp.tlearn.joke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJokeThumbs;

import java.util.List;

/**
 * @Description: 笑话评价
 * @Author:
 * @Date:   2020-02-09
 * @Version: V1.0
 */
public interface ITlKbJokeThumbsService extends IService<TlKbJokeThumbs> {

	List<TlKbJokeThumbs> selectByMainId(String mainId);

	TlKbJokeThumbs selectJokeThumbsByOpenid(String mainId,String openId);
}
