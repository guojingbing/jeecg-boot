package org.jeecg.modules.mp.tlearn.signin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninTerm;

import java.util.Map;

/**
 * @Description: 打卡参与记录
 * @Author:
 * @Date:
 * @Version: V1.0
 */
public interface TlSigninTermMapper extends BaseMapper<TlSigninTerm> {
    IPage<Map> loadList4API(Page<Map> page, String signId, String openid, String searchKey);
}
