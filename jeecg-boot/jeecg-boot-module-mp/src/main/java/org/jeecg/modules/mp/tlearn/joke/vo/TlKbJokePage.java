package org.jeecg.modules.mp.tlearn.joke.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJokeThumbs;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: 笑话段子
 * @Author:
 * @Date:   2020-02-09
 * @Version: V1.0
 */
@Data
public class TlKbJokePage {
	
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
	/**内容*/
	@Excel(name = "内容", width = 15)
	private String content;
	/**分类*/
	@Excel(name = "分类", width = 15)
	private Integer type;
	/**点赞人数*/
	@Excel(name = "点赞人数", width = 15)
	private Integer thumbsUp;
	/**分享次数*/
	@Excel(name = "分享次数", width = 15)
	private Integer share;
	/**收藏次数*/
	@Excel(name = "收藏次数", width = 15)
	private Integer collectionNum;
	/**来源主键*/
	@Excel(name = "来源主键", width = 15)
	private String sourceKey;
	
	@ExcelCollection(name="笑话评价")
	private List<TlKbJokeThumbs> tlKbJokeThumbsList;
	
}
