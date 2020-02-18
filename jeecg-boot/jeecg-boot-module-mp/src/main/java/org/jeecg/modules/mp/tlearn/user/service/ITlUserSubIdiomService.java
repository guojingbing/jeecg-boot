package org.jeecg.modules.mp.tlearn.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubIdiom;

import java.util.List;

/**
 * @Description: 用户收藏的成语
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
public interface ITlUserSubIdiomService extends IService<TlUserSubIdiom> {

	public List<TlUserSubIdiom> selectByMainId(String mainId);
}
