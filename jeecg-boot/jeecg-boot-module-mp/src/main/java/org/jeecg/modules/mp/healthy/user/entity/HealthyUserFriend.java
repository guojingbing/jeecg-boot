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
 * 用户亲友
 */
@Data
@TableName("healthy_user_friend")
public class HealthyUserFriend implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.AUTO)
    private Integer id;
	@Excel(name = "用户编号", width = 32)
    private String userId;
	@Excel(name = "亲友编号", width = 32)
    private String friendId;
	@Excel(name = "亲友称呼", width = 20)
	private String friendName;
	@Excel(name = "确认状态", width = 15)
	private Short confirmStatus;
	@Excel(name = "请求时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date reqTime;
	@Excel(name = "确认时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date confirmTime;
}
