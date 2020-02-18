package org.jeecg.modules.mp.tlearn.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 古诗词知识库
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Data
@TableName("tl_kb_poetry")
public class TlKbPoetry implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**创建人*/
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**更新人*/
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
	/**所属部门*/
    private String sysOrgCode;
	/**删除标记*/
    private Integer delFlag;
	/**标题*/
    private String title;
	/**作者*/
    private String authorId;
	/**形式*/
    private String formId;
	/**内容*/
    private String content;
    /**内容*/
    private String contentOrig;
    /**来源主键*/
    private String sourceKey;
    /**标签字符*/
    private String tagStr;
    /**参考文献*/
    private String reference;
    /**搜索排行*/
    private Integer rank;
    /**搜索排行*/
    private Integer rankBaidu;
    /**搜索排行*/
    private Integer rankGoogle;
    /**搜索排行*/
    private Integer rankBing;
    /**搜索排行*/
    private Integer rankSou360;
    /**音频路径*/
    private String audioPath;
    /**搜索关键字*/
    private String searchKey;
}
