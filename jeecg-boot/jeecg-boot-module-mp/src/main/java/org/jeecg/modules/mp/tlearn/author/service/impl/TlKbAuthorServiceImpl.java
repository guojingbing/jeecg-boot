package org.jeecg.modules.mp.tlearn.author.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.author.entity.TlKbAuthor;
import org.jeecg.modules.mp.tlearn.author.mapper.TlKbAuthorMapper;
import org.jeecg.modules.mp.tlearn.author.service.ITlKbAuthorService;
import org.jeecg.modules.mp.tlearn.poetry.mapper.TlKbPoetryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 知识库-作者信息
 * @Author:
 * @Date:   2020-01-09
 * @Version: V1.0
 */
@Service
public class TlKbAuthorServiceImpl extends ServiceImpl<TlKbAuthorMapper, TlKbAuthor> implements ITlKbAuthorService {
    @Autowired
    private TlKbAuthorMapper tlKbAuthorMapper;
    @Override
    public List<TlKbAuthor> listRecAuthors(){
        return tlKbAuthorMapper.listRecAuthors();
    }
}
