package org.jeecg.modules.mp.tlearn.signin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninComment;

import java.util.Map;

/**
 * @Description: 打卡评论
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface ITlSigninCommentService extends IService<TlSigninComment> {
    /**
     * 分页获取打卡评论信息
     * @param pageSize
     * @param pageNo
     * @param signinId
     * @return
     */
    IPage<Map> loadPagerMapList(int pageSize, int pageNo, String signinId);
}
