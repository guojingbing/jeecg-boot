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
 * 用户健康设置
 */
@Data
@TableName("healthy_user_setting")
public class HealthyUserSetting implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.AUTO)
    private Integer id;
	@Excel(name = "用户编号", width = 15)
    private String userId;
	/**创建日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
	@Excel(name = "生活服务呼叫号", width = 32)
    private String lifeServNumber;
	@Excel(name = "健康服务呼叫号", width = 32)
	private String healthyServNumber;
	@Excel(name = "目标步数", width = 15)
	private Integer destSteps;
	@Excel(name = "目标体重", width = 15)
	private Double destWeight;
	@Excel(name = "目标高压", width = 15)
	private Integer destPcp;
	@Excel(name = "目标低压", width = 15)
	private Integer destPdp;
	@Excel(name = "目标血糖", width = 15)
	private Double destGlu;
	@Excel(name = "目标BMI", width = 15)
	private Integer destBmi;
}
