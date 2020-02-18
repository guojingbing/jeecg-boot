package org.jeecg.modules.mp.tlearn.kb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbBookContent;
import org.jeecg.modules.mp.tlearn.kb.mapper.TlKbBookContentMapper;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbBookContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description: 书籍章节内容
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
@Service
public class TlKbBookContentServiceImpl extends ServiceImpl<TlKbBookContentMapper, TlKbBookContent> implements ITlKbBookContentService {
	@Autowired
	private TlKbBookContentMapper tlKbBookContentMapper;

	@Override
	public TlKbBookContent selectBookContentByDetailId(String bookId, String detailId) {
		return tlKbBookContentMapper.selectBookContentByDetailId(bookId,detailId);
	}

	@Override
	public Map selectBookContentMoreInfo(String contentId) {
		return tlKbBookContentMapper.selectBookContentMoreInfo(contentId);
	}
}
