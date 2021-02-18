package org.jeecg.modules.mp.nshare.comm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.comm.entity.MpSysHotkey;

import java.util.List;

/**
 * @Description: 搜索记录
 * @Author:
 * @Date:   2020-02-01
 * @Version: V1.0
 */
public interface MpSysHotkeyMapper extends BaseMapper<MpSysHotkey> {
    List<MpSysHotkey> loadHotkeys();

    MpSysHotkey findHotkeyByKey(@Param("key") String key, @Param("cat") String cat);
}
