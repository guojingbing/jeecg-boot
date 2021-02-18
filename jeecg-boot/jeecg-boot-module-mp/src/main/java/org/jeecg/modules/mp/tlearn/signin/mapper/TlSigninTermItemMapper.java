package org.jeecg.modules.mp.tlearn.signin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninTermItem;

import java.util.List;
import java.util.Map;

/**
 * @Description: 打卡参与详情记录
 * @Author:
 * @Date:
 * @Version: V1.0
 */
public interface TlSigninTermItemMapper extends BaseMapper<TlSigninTermItem> {
    IPage<Map> loadList4API(Page<Map> page, String signinId, String uid, String searchKey);
    List<Map> selectSigninTermItems(@Param("termId") String termId);
    List<Map> selectSigninTermItemById(@Param("id") String id);
    List<Map> selectSigninPoetries();
    List<Map> selectSigninKbs(@Param("type") Integer type);
}
