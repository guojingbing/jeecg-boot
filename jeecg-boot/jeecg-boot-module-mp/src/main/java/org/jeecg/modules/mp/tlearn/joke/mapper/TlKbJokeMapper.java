package org.jeecg.modules.mp.tlearn.joke.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJoke;

import java.util.Map;

/**
 * @Description: 笑话段子
 * @Author:
 * @Date:   2020-02-09
 * @Version: V1.0
 */
public interface TlKbJokeMapper extends BaseMapper<TlKbJoke> {
    IPage<Map> loadList4API(Page<Map> page, String openid,String searchKey);
}
