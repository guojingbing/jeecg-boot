package org.jeecg.modules.mp.tlearn.kb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbBookContent;

import java.util.Map;

/**
 * @Description:书籍章节
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface ITlKbBookContentService extends IService<TlKbBookContent> {
	TlKbBookContent selectBookContentByDetailId(String bookId,String detailId);
	Map selectBookContentMoreInfo(String contentId);
}
