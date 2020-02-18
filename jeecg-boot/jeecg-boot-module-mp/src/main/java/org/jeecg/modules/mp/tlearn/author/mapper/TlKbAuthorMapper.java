package org.jeecg.modules.mp.tlearn.author.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.mp.tlearn.author.entity.TlKbAuthor;

import java.util.List;

/**
 * @Description: 知识库-作者信息
 * @Author:
 * @Date:   2020-01-09
 * @Version: V1.0
 */
public interface TlKbAuthorMapper extends BaseMapper<TlKbAuthor> {
    void batchInsert(List<TlKbAuthor> list);
    @Select("SELECT * FROM tl_kb_author WHERE rec_index>0")
    List<TlKbAuthor> listRecAuthors();
}
