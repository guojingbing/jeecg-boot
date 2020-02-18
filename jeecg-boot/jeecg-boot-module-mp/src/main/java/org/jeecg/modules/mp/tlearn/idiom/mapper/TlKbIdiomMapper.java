package org.jeecg.modules.mp.tlearn.idiom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.mp.tlearn.idiom.entity.TlKbIdiom;

import java.util.Map;

/**
 * @Description: 成语
 * @Author:
 * @Date:   2020-01-17
 * @Version: V1.0
 */
public interface TlKbIdiomMapper extends BaseMapper<TlKbIdiom> {
    /**
     * 列表获取接口
     * @param page
     * @param searchKey
     * @return
     */
    IPage<Map> loadList4API(Page<Map> page, String searchKey);
}
