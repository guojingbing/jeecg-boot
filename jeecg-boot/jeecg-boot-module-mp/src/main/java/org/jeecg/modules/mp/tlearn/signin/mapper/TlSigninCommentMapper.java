package org.jeecg.modules.mp.tlearn.signin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninComment;

import java.util.Map;

/**
 * @Description: 打卡评论
 * @Author:
 * @Date:
 * @Version: V1.0
 */
public interface TlSigninCommentMapper extends BaseMapper<TlSigninComment> {
    IPage<Map> loadPagerMapList(Page<Map> page, String signinId);
}
