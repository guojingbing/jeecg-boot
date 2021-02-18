package org.jeecg.modules.mp.tlearn.kb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbCourseVolume;

import java.util.List;

/**
 * @Description:
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface ITlKbCourseVolumeService extends IService<TlKbCourseVolume> {
    List<TlKbCourseVolume> listCourseVolume(String fkCode);
}
