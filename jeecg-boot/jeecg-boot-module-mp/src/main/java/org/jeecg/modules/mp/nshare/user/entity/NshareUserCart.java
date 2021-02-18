package org.jeecg.modules.mp.nshare.user.entity;

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
 * @Description: 社区分享终端用户购物车
 * @Author:
 * @Date:   2020-02-18
 * @Version: V1.0
 */
@Data
@TableName("nshare_user_cart")
public class NshareUserCart implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**用户编号*/
	@Excel(name = "用户编号", width = 15)
	private String userId;
	/**提货点编号*/
	@Excel(name = "提货点编号", width = 15)
	private String stationId;
	/**商品编号*/
	@Excel(name = "商品编号", width = 15)
	private String goodsId;
	/**数量*/
	@Excel(name = "数量", width = 15)
	private Integer num;
	/**添加价格*/
	@Excel(name = "添加价格", width = 15)
	private Double addPrice;
	/**活动编号*/
	@Excel(name = "活动编号", width = 15)
	private String promoId;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
}
