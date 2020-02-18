package org.jeecg.modules.mp.tlearn.kb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbBookChapter;

import java.util.List;

/**
 * @Description:书籍章节
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface ITlKbBookChapterService extends IService<TlKbBookChapter> {
	/**
	 * 查询书籍章节列表
	 * @param bookId
	 * @return
	 */
	List<TlKbBookChapter> listBookChapters(String bookId);
	/**
	 * 按排序编号获取章节信息
	 * @param bookId
	 * @param level
	 * @param orderNum
	 * @return
	 */
	TlKbBookChapter findBookChapterByOrderNum(String bookId,Integer level,String orderNum);
}
