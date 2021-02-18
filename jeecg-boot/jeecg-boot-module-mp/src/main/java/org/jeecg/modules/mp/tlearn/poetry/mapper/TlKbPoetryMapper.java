package org.jeecg.modules.mp.tlearn.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetry;

import java.util.List;
import java.util.Map;

/**
 * @Description: 拾贝古诗词知识库
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface TlKbPoetryMapper extends BaseMapper<TlKbPoetry> {
    IPage<Map> loadList4API(Page<Map> page, String poeFormId, String dynId, String key, String tagStr, String bookId);

    @Select("SELECT * FROM tl_kb_poetry WHERE source_key in(SELECT source_key FROM (SELECT count(*) num,source_key FROM `tl_kb_poetry` GROUP BY source_key) tmp where tmp.num>1)")
    List<TlKbPoetry> listPoetryRepeat();

    @Select("DELETE FROM tl_kb_poetry WHERE source_key in(SELECT source_key FROM (SELECT count(*) num,source_key FROM `tl_kb_poetry` GROUP BY source_key) tmp where tmp.num>1) AND id not in(#{inCase})")
    void delPoetrysRepeat(String inCase);

    List<TlKbPoetry> listPoetryNoRank(@Param("rank") String rank);

    Map loadTlPoetry(@Param("id") String id);

    Map loadTlKb(@Param("kbId") String kbId);
}
