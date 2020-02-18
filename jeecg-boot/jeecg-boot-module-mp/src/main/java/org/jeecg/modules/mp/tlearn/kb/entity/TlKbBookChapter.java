package org.jeecg.modules.mp.tlearn.kb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 书籍章节
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Data
@TableName("tl_kb_book_chapter")
public class TlKbBookChapter implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**书籍编号*/
    private String bookId;
	/**标题*/
    private String title;
	/**父级编号*/
    private String parentId;
	/**内容编号*/
    private String contentId;
    /**层级*/
    private Integer level;
    /**是否叶子节点*/
    private Boolean isLeaf;
    /**排序号*/
    private String orderNum;
}
