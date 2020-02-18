package org.jeecg.modules.mp.tlearn.kb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbBookContent;

import java.util.Map;

/**
 * @Description: 拾贝古诗词知识库
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface TlKbBookContentMapper extends BaseMapper<TlKbBookContent> {
    TlKbBookContent selectBookContentByDetailId(@Param("bookId") String bookId, @Param("detailId") String detailId);
    Map selectBookContentMoreInfo(@Param("contentId") String contentId);
}
