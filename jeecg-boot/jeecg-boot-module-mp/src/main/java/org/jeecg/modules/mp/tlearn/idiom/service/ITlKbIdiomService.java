package org.jeecg.modules.mp.tlearn.idiom.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.idiom.entity.TlKbIdiom;

import java.util.Map;

/**
 * @Description: 成语
 * @Author:
 * @Date:   2020-01-17
 * @Version: V1.0
 */
public interface ITlKbIdiomService extends IService<TlKbIdiom> {
    IPage<Map> loadList4API(int pageSize, int pageNo, String searchKey);
}
