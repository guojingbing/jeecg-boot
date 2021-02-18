package org.jeecg.modules.mp.tlearn.signin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninComment;
import org.jeecg.modules.mp.tlearn.signin.mapper.TlSigninCommentMapper;
import org.jeecg.modules.mp.tlearn.signin.service.ITlSigninCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2020-08-18 11:33:54
 **/
@Service
public class TlSigninCommentServiceImpl extends ServiceImpl<TlSigninCommentMapper, TlSigninComment> implements ITlSigninCommentService {
    @Autowired
    TlSigninCommentMapper mapper;
    @Override
    public IPage<Map> loadPagerMapList(int pageSize, int pageNo, String signinId){
        Page<Map> page = new Page<>(pageNo, pageSize);
        return mapper.loadPagerMapList(page,signinId);
    }
}
