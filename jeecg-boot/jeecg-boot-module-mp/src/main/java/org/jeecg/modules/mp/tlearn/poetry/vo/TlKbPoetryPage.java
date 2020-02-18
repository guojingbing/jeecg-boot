package org.jeecg.modules.mp.tlearn.poetry.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryComment;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetryTag;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: 古诗词知识库
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Data
public class TlKbPoetryPage {
	
	/**主键*/
	private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
	private String sysOrgCode;
	/**删除标记*/
	@Excel(name = "删除标记", width = 15)
	private Integer delFlag;
	/**标题*/
	@Excel(name = "标题", width = 15)
	private String title;
	/**作者*/
	@Excel(name = "作者", width = 15)
	private String authorId;
	/**形式*/
	@Excel(name = "形式", width = 15)
	private String formId;
	/**内容*/
	@Excel(name = "内容", width = 15)
	private String content;
	
	@ExcelCollection(name="古诗词赏析评论")
	private List<TlKbPoetryComment> tlKbPoetryCommentList;
	@ExcelCollection(name="古诗词标签")
	private List<TlKbPoetryTag> tlKbPoetryTagList;
	
}
