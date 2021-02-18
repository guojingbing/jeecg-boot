package org.jeecg.modules.mp.tlearn.signin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninTermItemDanmu;

import java.util.List;
import java.util.Map;

/**
 * @Description: 打卡参与详情记录视频弹幕
 * @Author:
 * @Date:
 * @Version: V1.0
 */
public interface TlSigninTermItemDanmuMapper extends BaseMapper<TlSigninTermItemDanmu> {
    List<Map> selectMapList(@Param("itemId") String itemId);
}
