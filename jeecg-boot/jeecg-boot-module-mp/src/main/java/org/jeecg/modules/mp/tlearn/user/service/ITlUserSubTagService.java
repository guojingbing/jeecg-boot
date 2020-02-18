package org.jeecg.modules.mp.tlearn.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubTag;

import java.util.List;

/**
 * @Description: 用户订阅的诗词分类
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
public interface ITlUserSubTagService extends IService<TlUserSubTag> {

	public List<TlUserSubTag> selectByMainId(String mainId);
}
