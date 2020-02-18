package org.jeecg.modules.mp.tlearn.idiom.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.idiom.entity.TlKbIdiom;
import org.jeecg.modules.mp.tlearn.idiom.mapper.TlKbIdiomMapper;
import org.jeecg.modules.mp.tlearn.idiom.service.ITlKbIdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description: 成语
 * @Author:
 * @Date:   2020-01-17
 * @Version: V1.0
 */
@Service
public class TlKbIdiomServiceImpl extends ServiceImpl<TlKbIdiomMapper, TlKbIdiom> implements ITlKbIdiomService {
    @Autowired
    private TlKbIdiomMapper tlKbIdiomMapper;
    @Override
    public IPage<Map> loadList4API(int pageSize, int pageNo, String searchKey) {
        Page<Map> page = new Page<>(pageNo, pageSize);
        return tlKbIdiomMapper.loadList4API(page,searchKey);
    }
}
