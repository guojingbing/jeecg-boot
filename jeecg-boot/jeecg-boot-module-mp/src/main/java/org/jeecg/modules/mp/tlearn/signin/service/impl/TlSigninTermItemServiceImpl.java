package org.jeecg.modules.mp.tlearn.signin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninTermItem;
import org.jeecg.modules.mp.tlearn.signin.mapper.TlSigninTermItemMapper;
import org.jeecg.modules.mp.tlearn.signin.service.ITlSigninTermItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 打卡明细
 * @Author: Kingpin
 * @Date: 2020-08-18 11:38:08
 **/
@Service
public class TlSigninTermItemServiceImpl extends ServiceImpl<TlSigninTermItemMapper, TlSigninTermItem> implements ITlSigninTermItemService {
    @Autowired
    private TlSigninTermItemMapper signinTermItemMapper;
    @Override
    public IPage<Map> loadList4API(int pageSize, int pageNo, String signinId, String uid, String searchKey) {
        Page<Map> page = new Page<>(pageNo, pageSize);
        return signinTermItemMapper.loadList4API(page,signinId,uid,searchKey);
    }

    @Override
    public List<Map> selectSigninTermItems(String termId) {
        return signinTermItemMapper.selectSigninTermItems(termId);
    }

    @Override
    public List<Map> selectSigninTermItemById(String id) {
        return signinTermItemMapper.selectSigninTermItemById(id);
    }

    @Override
    public List<Map> selectSigninPoetries() {
        return signinTermItemMapper.selectSigninPoetries();
    }

    @Override
    public List<Map> selectSigninKbs(Integer type) {
        return signinTermItemMapper.selectSigninKbs(type);
    }
}
