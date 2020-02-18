package org.jeecg.modules.mp.tlearn.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubQa;

import java.util.List;

/**
 * @Description: 用户订阅的问答
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
public interface ITlUserSubQaService extends IService<TlUserSubQa> {
	List<TlUserSubQa> selectByQaId(String qaId);

	TlUserSubQa selectUserQaById(String qaId, String openid);
}
