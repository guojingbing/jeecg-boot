package org.jeecg.modules.mp.tlearn.kb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbCourseVolume;

import java.util.List;

/**
 * @Description: 知识库
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface TlKbCourseVolumeMapper extends BaseMapper<TlKbCourseVolume> {
    /**
     * 根据课程组卷网编码获取课程分册信息
     * @param cfk
     * @return
     */
    List<TlKbCourseVolume> selectCourseVolume(@Param("cfk") String cfk);
}
