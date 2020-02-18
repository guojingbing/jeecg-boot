package org.jeecg.modules.mp.tlearn.sys.hotkey.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.sys.hotkey.entity.TlSysHotkey;

import java.util.List;

/**
 * @Description: 搜索记录
 * @Author:
 * @Date:   2020-02-01
 * @Version: V1.0
 */
public interface TlSysHotkeyMapper extends BaseMapper<TlSysHotkey> {
    List<TlSysHotkey> loadHotkeys();

    TlSysHotkey findHotkeyByKey(@Param("key") String key, @Param("cat") String cat);
}
