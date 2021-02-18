package org.jeecg.modules.mp.nshare.comm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.comm.entity.MpSysHotkey;
import org.jeecg.modules.mp.nshare.comm.mapper.MpSysHotkeyMapper;
import org.jeecg.modules.mp.nshare.comm.service.IMpSysHotkeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 搜索记录
 * @Author:
 * @Date:   2020-02-01
 * @Version: V1.0
 */
@Service
public class MpSysHotkeyServiceImpl extends ServiceImpl<MpSysHotkeyMapper, MpSysHotkey> implements IMpSysHotkeyService {
    @Autowired
    private MpSysHotkeyMapper mpSysHotkeyMapper;

    @Override
    public List<MpSysHotkey> loadHotkeys(){
        return mpSysHotkeyMapper.loadHotkeys();
    }

    @Override
    public MpSysHotkey findHotkeyByKey(String key,String cat){
        return mpSysHotkeyMapper.findHotkeyByKey(key, cat);
    }
}
