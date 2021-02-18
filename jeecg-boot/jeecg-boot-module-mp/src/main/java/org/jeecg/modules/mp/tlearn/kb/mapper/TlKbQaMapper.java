package org.jeecg.modules.mp.tlearn.kb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbQa;

import java.util.List;
import java.util.Map;

/**
 * @Description: 问答类知识
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface TlKbQaMapper extends BaseMapper<TlKbQa> {
    IPage<Map> loadList4API(Page<Map> page, String type, String openid, String searchKey, String shareId,String isColl);
    List<TlKbQa> selectTypeQas(@Param("type") String type);
}
