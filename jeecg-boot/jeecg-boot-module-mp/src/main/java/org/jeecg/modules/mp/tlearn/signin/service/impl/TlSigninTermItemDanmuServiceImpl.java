package org.jeecg.modules.mp.tlearn.signin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninTermItemDanmu;
import org.jeecg.modules.mp.tlearn.signin.mapper.TlSigninTermItemDanmuMapper;
import org.jeecg.modules.mp.tlearn.signin.service.ITlSigninTermItemDanmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 打卡明细视频弹幕
 * @Author: Kingpin
 * @Date: 2020-08-18 11:38:08
 **/
@Service
public class TlSigninTermItemDanmuServiceImpl extends ServiceImpl<TlSigninTermItemDanmuMapper, TlSigninTermItemDanmu> implements ITlSigninTermItemDanmuService {
    @Autowired
    private TlSigninTermItemDanmuMapper signinTermItemDanmuMapper;

    @Override
    public List<Map> selectMapList(String itemId) {
        return signinTermItemDanmuMapper.selectMapList(itemId);
    }
}
