package org.jeecg.modules.mp.nshare.comm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.comm.entity.MpSysHotkey;

import java.util.List;

/**
 * @Description: 搜索记录
 * @Author:
 * @Date:   2020-02-01
 * @Version: V1.0
 */
public interface IMpSysHotkeyService extends IService<MpSysHotkey> {
    /**
     * 获取热搜
     * @return
     */
    List<MpSysHotkey> loadHotkeys();

    /**
     * 根据key获取热搜
     * @param key
     * @param cat
     * @return
     */
    MpSysHotkey findHotkeyByKey(String key, String cat);
}
