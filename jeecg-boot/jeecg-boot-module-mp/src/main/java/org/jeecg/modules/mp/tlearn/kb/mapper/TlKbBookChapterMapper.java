package org.jeecg.modules.mp.tlearn.kb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbBookChapter;

import java.util.List;
import java.util.Map;

/**
 * @Description: 拾贝古诗词知识库
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface TlKbBookChapterMapper extends BaseMapper<TlKbBookChapter> {
    List<TlKbBookChapter> listBookChapters(@Param("bookId") String bookId);
    TlKbBookChapter selectBookChapterByOrderNum(@Param("bookId") String bookId,@Param("level") Integer level,@Param("orderNum") String orderNum);
}
