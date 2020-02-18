package org.jeecg.modules.mp.tlearn.author.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.author.entity.TlKbAuthor;

import java.util.List;

/**
 * @Description: 知识库-作者信息
 * @Author:
 * @Date:   2020-01-09
 * @Version: V1.0
 */
public interface ITlKbAuthorService extends IService<TlKbAuthor> {
    /**
     * 查询推荐指数大于0的作者
     * @return
     */
    List<TlKbAuthor> listRecAuthors();
}
