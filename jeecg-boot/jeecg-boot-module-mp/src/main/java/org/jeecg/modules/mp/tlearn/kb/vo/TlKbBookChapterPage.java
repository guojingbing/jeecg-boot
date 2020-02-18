package org.jeecg.modules.mp.tlearn.kb.vo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 古诗词知识库
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Data
public class TlKbBookChapterPage {
	/**主键*/
	private String id;
	/**书籍编号*/
	@Excel(name = "书籍编号", width = 15)
	private String bookId;
	/**标题*/
	@Excel(name = "标题", width = 15)
	private String title;
	/**父级编号*/
	@Excel(name = "父级编号", width = 15)
	private String parentId;
	/**内容编号*/
	@Excel(name = "内容", width = 15)
	private String contentId;
	/**层级*/
	@Excel(name = "层级", width = 15)
	private Integer level;
	/**是否叶子节点*/
	@Excel(name = "叶子节点", width = 15)
	private Boolean isLeaf;
	/**排序号*/
	@Excel(name = "排序号", width = 15)
	private String orderNum;
}
