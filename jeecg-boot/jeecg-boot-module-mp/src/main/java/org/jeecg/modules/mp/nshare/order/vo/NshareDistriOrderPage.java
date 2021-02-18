package org.jeecg.modules.mp.nshare.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.mp.nshare.order.entity.NshareDistriOrderGoods;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: 社区分享配送订单
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Data
public class NshareDistriOrderPage {
	
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
	/**店铺*/
	@Excel(name = "店铺", width = 15)
	private String shopId;
	/**订单号*/
	@Excel(name = "订单号", width = 15)
	private String orderNo;
	/**订单时间*/
	@Excel(name = "订单时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date orderDate;
	/**订单金额*/
	@Excel(name = "订单金额", width = 15)
	private String orderAmount;
	/**取货点*/
	@Excel(name = "取货点", width = 15)
	private String stationId;
	/**收货人*/
	@Excel(name = "收货人", width = 15)
	private String pickUser;
	/**收货人电话*/
	@Excel(name = "收货人电话", width = 15)
	private String pickPhone;
	/**取货码*/
	@Excel(name = "取货码", width = 15)
	private String pickCode;
	/**订单状态*/
	@Excel(name = "订单状态", width = 15)
	private String orderStatus;
	/**确认时间*/
	@Excel(name = "确认时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date confirmTime;
	/**取货时间*/
	@Excel(name = "取货时间", width = 15, format = "yyyy-MM-dd HH:mm")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date pickTime;
	/**订单备注*/
	private String remark;
	
	@ExcelCollection(name="社区分享配送订单商品")
	private List<NshareDistriOrderGoods> goodsList;
	
}
