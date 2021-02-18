package org.jeecg.modules.mp.tlearn.kb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 知识库课程章节
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Data
@TableName("tl_kb_course_chapter")
public class TlKbCourseChapter implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.AUTO)
    private Integer id;
	/**标题*/
    private String title;
    private Integer parentId;
    private Integer isLeaf;
    private Integer level;
    private String nodePath;
    private Integer impLevel;
    private Integer diffLevel;
    private Integer volumeId;
    private String fkCode;
}
