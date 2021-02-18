package org.jeecg.modules.mp.tlearn.signin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.lettuce.core.dynamic.annotation.Param;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninTermItemDanmu;

import java.util.List;
import java.util.Map;

/**
 * @Description: 打卡明细视频弹幕
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface ITlSigninTermItemDanmuService extends IService<TlSigninTermItemDanmu> {
    List<Map> selectMapList(@Param("itemId") String itemId);
}
