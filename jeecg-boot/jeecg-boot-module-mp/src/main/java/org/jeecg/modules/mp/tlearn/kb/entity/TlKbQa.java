package org.jeecg.modules.mp.tlearn.kb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 问答类知识库：谜语、急转弯、歇后语、绕口令
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Data
@TableName("tl_kb_qa")
public class TlKbQa implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**标题*/
    private String title;
	/**内容*/
    private String content;
    /**答案*/
    private String answer;
    /**问答分类*/
    private Integer qaType;
    /**类型*/
    private Integer classId;
    /**点赞次数*/
    private Integer thumbsNum;
    /**分享次数*/
    private Integer shareNum;
    /**收藏次数*/
    private Integer collectionNum;
}
