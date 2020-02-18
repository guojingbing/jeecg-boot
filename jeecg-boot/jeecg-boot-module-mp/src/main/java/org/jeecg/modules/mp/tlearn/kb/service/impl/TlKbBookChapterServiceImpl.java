package org.jeecg.modules.mp.tlearn.kb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbBookChapter;
import org.jeecg.modules.mp.tlearn.kb.mapper.TlKbBookChapterMapper;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbBookChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 书籍章节
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
@Service
public class TlKbBookChapterServiceImpl extends ServiceImpl<TlKbBookChapterMapper, TlKbBookChapter> implements ITlKbBookChapterService {
	@Autowired
	private TlKbBookChapterMapper tlKbBookChapterMapper;
	@Override
	public List<TlKbBookChapter> listBookChapters(String bookId){
		return tlKbBookChapterMapper.listBookChapters(bookId);
	}

	@Override
	public TlKbBookChapter findBookChapterByOrderNum(String bookId,Integer level,String orderNum){
		return tlKbBookChapterMapper.selectBookChapterByOrderNum(bookId,level,orderNum);
	}
}
