package org.jeecg.modules.mp.tlearn.sys.hotkey.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.sys.hotkey.entity.TlSysHotkey;
import org.jeecg.modules.mp.tlearn.sys.hotkey.mapper.TlSysHotkeyMapper;
import org.jeecg.modules.mp.tlearn.sys.hotkey.service.ITlSysHotkeyService;
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
public class TlSysHotkeyServiceImpl extends ServiceImpl<TlSysHotkeyMapper, TlSysHotkey> implements ITlSysHotkeyService {
    @Autowired
    private TlSysHotkeyMapper tlSysHotkeyMapper;

    @Override
    public List<TlSysHotkey> loadHotkeys(){
        return tlSysHotkeyMapper.loadHotkeys();
    }

    @Override
    public TlSysHotkey findHotkeyByKey(String key,String cat){
        return tlSysHotkeyMapper.findHotkeyByKey(key, cat);
    }
}
