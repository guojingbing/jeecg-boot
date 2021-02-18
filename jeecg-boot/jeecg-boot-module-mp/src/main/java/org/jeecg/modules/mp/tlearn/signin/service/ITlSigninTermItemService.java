package org.jeecg.modules.mp.tlearn.signin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSigninTermItem;

import java.util.List;
import java.util.Map;

/**
 * @Description: 打卡明细
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface ITlSigninTermItemService extends IService<TlSigninTermItem> {
    /**
     * 分页获取打卡明细信息
     * @param pageSize
     * @param pageNo
     * @param signinId
     * @param uid
     * @param searchKey
     * @return
     */
    IPage<Map> loadList4API(int pageSize, int pageNo, String signinId, String uid, String searchKey);
    List<Map> selectSigninTermItems(String termId);
    List<Map> selectSigninTermItemById(String id);
    /**
     * 获取知识库里标记的诗词
     * @return
     */
    List<Map> selectSigninPoetries();

    /**
     * 加载诗词外的其他打卡知识库资源
     * @return
     */
    List<Map> selectSigninKbs(Integer type);
}
