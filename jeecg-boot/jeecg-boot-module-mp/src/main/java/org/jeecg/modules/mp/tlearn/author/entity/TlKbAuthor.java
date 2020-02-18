package org.jeecg.modules.mp.tlearn.author.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 知识库-作者信息
 * @Author:
 * @Date:   2020-01-09
 * @Version: V1.0
 */
@Data
@TableName("tl_kb_author")
public class TlKbAuthor implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
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
	/**作者姓名*/
	@Excel(name = "作者姓名", width = 15)
    private String authorName;
	/**作者朝代*/
	@Excel(name = "作者朝代", width = 15)
    private String dynId;
	/**作者简介*/
	@Excel(name = "作者简介", width = 15)
    private String summary;
	/**头像*/
	@Excel(name = "头像", width = 15)
    private String avatar;
	/**来源主键*/
	private String sourceKey;
	/**推荐指数*/
	private Integer recIndex;
}
