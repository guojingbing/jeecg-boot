package org.jeecg.modules.mp.tlearn.sys.hotkey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.sys.hotkey.entity.TlSysHotkey;

import java.util.List;

/**
 * @Description: 搜索记录
 * @Author:
 * @Date:   2020-02-01
 * @Version: V1.0
 */
public interface ITlSysHotkeyService extends IService<TlSysHotkey> {
    /**
     * 获取热搜
     * @return
     */
    List<TlSysHotkey> loadHotkeys();

    /**
     * 根据key获取热搜
     * @param key
     * @param cat
     * @return
     */
    TlSysHotkey findHotkeyByKey(String key,String cat);
}
