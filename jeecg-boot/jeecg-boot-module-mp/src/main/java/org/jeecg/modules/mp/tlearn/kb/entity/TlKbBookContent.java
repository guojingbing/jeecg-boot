package org.jeecg.modules.mp.tlearn.kb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 书籍章节内容
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Data
@TableName("tl_kb_book_content")
public class TlKbBookContent implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**书籍编号*/
    private String bookId;
    /**章节详情编号*/
    private String detailId;
	/**标题*/
    private String title;
	/**内容*/
    private String content;
    /**翻译*/
    private String translation;
    /**注释*/
    private String commentary;
    /**赏析*/
    private String appreciation;
    /**解读*/
    private String interpretation;
}
