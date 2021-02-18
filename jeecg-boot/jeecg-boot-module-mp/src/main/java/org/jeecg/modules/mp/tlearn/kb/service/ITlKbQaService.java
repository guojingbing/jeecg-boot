package org.jeecg.modules.mp.tlearn.kb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbQa;

import java.util.List;
import java.util.Map;

/**
 * @Description:问答类知识
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface ITlKbQaService extends IService<TlKbQa> {
	IPage<Map> loadList4API(int pageSize, int pageNo, String type, String openid, String searchKey, String shareId, String isColl);
	List<TlKbQa> listQaByType(String type);
}
