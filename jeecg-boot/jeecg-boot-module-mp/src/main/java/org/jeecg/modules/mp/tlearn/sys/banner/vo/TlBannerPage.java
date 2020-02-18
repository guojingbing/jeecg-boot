package org.jeecg.modules.mp.tlearn.sys.banner.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.mp.tlearn.sys.banner.entity.TlBannerItem;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: 轮播
 * @Author:
 * @Date:   2020-01-30
 * @Version: V1.0
 */
@Data
public class TlBannerPage {
	
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
	/**标题*/
	@Excel(name = "标题", width = 15)
	private String title;
	/**描述*/
	@Excel(name = "描述", width = 15)
	private String bannerDesc;
	/**位置代码*/
	@Excel(name = "位置代码", width = 15)
	private String locCode;
	/**是否使用*/
	@Excel(name = "是否使用", width = 15)
	private String isUse;
	
	@ExcelCollection(name="轮播条目")
	private List<TlBannerItem> tlBannerItemList;
	
}
