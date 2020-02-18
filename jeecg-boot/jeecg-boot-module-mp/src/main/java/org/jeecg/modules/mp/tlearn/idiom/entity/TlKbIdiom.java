package org.jeecg.modules.mp.tlearn.idiom.entity;

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
 * @Description: 成语
 * @Author:
 * @Date:   2020-01-17
 * @Version: V1.0
 */
@Data
@TableName("tl_kb_idiom")
public class TlKbIdiom implements Serializable {
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
	/**拼音*/
	@Excel(name = "拼音", width = 15)
    private String pinyin;
	/**成语名称*/
	@Excel(name = "成语名称", width = 15)
    private String title;
	/**释义*/
	@Excel(name = "释义", width = 15)
    private String definition;
	/**出处*/
	@Excel(name = "出处", width = 15)
    private String source;
	/**例句*/
	@Excel(name = "例句", width = 15)
    private String example;
	/**近义词*/
	@Excel(name = "近义词", width = 15)
    private String synonym;
	/**反义词*/
	@Excel(name = "反义词", width = 15)
    private String antonym;
	private String tonePy;
	private String toneMp3;
}
