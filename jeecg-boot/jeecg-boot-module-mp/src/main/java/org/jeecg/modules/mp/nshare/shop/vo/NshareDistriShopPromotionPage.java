package org.jeecg.modules.mp.nshare.shop.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: 社区分享店铺活动
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Data
public class NshareDistriShopPromotionPage {
	/**主键*/
	private String id;
	@Excel(name = "活动类型", width = 15)
	private String proType;
	@Excel(name = "活动标题", width = 15)
	private String title;
	@Excel(name = "活动说明", width = 15)
	private String proDesc;
	@Excel(name = "店铺编号", width = 15)
	private String shopId;
	@Excel(name = "开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@Excel(name = "结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	@Excel(name = "持续小时数", width = 15)
	private Integer proHours;

	@Excel(name = "商品编号", width = 15)
	private String goodsId;

	@Excel(name = "商品名称", width = 15)
	private String goodsName;

	/**标准价格*/
	@Excel(name = "标准价格", width = 15)
	private Double normPrice;

	/**销售价格*/
	@Excel(name = "销售价格", width = 15)
	private Double salePrice;

	/**单位*/
	@Excel(name = "单位", width = 15)
	private String priceUnit;

	/**活动商品总数*/
	@Excel(name = "活动商品总数", width = 15)
	private Integer totalNum;

	/**成团数量*/
	@Excel(name = "成团数量", width = 15)
	private Integer limitNum;

	@Excel(name = "最少购买数量", width = 15)
	private Integer minNum;

	/**最多购买数量*/
	@Excel(name = "最多购买", width = 15)
	private Integer maxNum;

	@Excel(name = "审核状态", width = 15)
	private Integer auditStatus;
	@Excel(name = "使用状态", width = 15)
	private Integer useStatus;

	/**排序*/
	@Excel(name = "排序", width = 15)
	private Integer orderNum;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private String createBy;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@ExcelCollection(name="社区分享配送店铺营销活动图片")
	private List<String> imgList;
}
