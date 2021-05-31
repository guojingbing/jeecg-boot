package org.jeecg.modules.mp.healthy.user.entity;

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
 * 用户健康信息采集记录
 */
@Data
@TableName("healthy_record")
public class HealthyRecord implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.AUTO)
    private Integer id;
	@Excel(name = "用户编号", width = 15)
    private String userId;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**1、血压；2、血糖；3、血氧；4：体温；5：心脏；*/
	@Excel(name = "记录类型", width = 15)
    private Short htype;
	@Excel(name = "参数", width = 15)
    private Double param1;
	@Excel(name = "参数", width = 15)
	private Double param2;
	@Excel(name = "参数", width = 15)
	private Double param3;
	@Excel(name = "参数", width = 32)
    private String paramExtra;
	/**1、设备采集；2、人工填写*/
	@Excel(name = "数据来源", width = 15)
	private Short src;
	@Excel(name = "采集日期", width = 20, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date recDate;
}
