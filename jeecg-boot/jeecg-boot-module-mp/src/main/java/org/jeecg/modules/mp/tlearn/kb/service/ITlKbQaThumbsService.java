package org.jeecg.modules.mp.tlearn.kb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbQaThumbs;

import java.util.List;

/**
 * @Description: 笑话评价
 * @Author:
 * @Date:   2020-02-09
 * @Version: V1.0
 */
public interface ITlKbQaThumbsService extends IService<TlKbQaThumbs> {

	List<TlKbQaThumbs> selectByMainId(String mainId);

	TlKbQaThumbs selectQaThumbsByOpenid(String mainId, String openId);
}
