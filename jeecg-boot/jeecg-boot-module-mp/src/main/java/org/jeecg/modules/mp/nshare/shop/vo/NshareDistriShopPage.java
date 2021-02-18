package org.jeecg.modules.mp.nshare.shop.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopAdmin;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopGoodsCat;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopStation;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享配送店铺
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Data
public class NshareDistriShopPage {
	/**主键*/
	private String id;
	/**店铺码*/
	@Excel(name = "店铺码", width = 15)
	private String shopCode;
	/**店铺名称*/
	@Excel(name = "店铺名称", width = 15)
	private String shopName;
	/**店铺描述*/
	@Excel(name = "店铺描述", width = 15)
	private String shopDesc;
	/**店铺级别*/
	@Excel(name = "店铺级别", width = 15)
	private String shopLevel;
	/**店主*/
	@Excel(name = "店主", width = 15)
	private String ownerId;
	/**店主电话*/
	@Excel(name = "店主电话", width = 15)
	private String ownerPhone;
	/**店铺状态*/
	@Excel(name = "店铺状态", width = 15)
	private Integer shopStatus;
	/**店铺地址*/
	@Excel(name = "店铺地址", width = 15)
	private String address;
	/**店铺地址补充*/
	@Excel(name = "地址补充", width = 15)
	private String addrMore;
	/**经度*/
	@Excel(name = "经度", width = 15)
	private java.math.BigDecimal lng;
	/**维度*/
	@Excel(name = "维度", width = 15)
	private java.math.BigDecimal lat;
	/**区域代码*/
	@Excel(name = "维度", width = 15)
	private String adcode;
	/**国家*/
	@Excel(name = "国家", width = 15)
	private String nation;
	/**省份*/
	@Excel(name = "省份", width = 15)
	private String province;
	/**城市*/
	@Excel(name = "城市", width = 15)
	private String city;
	/**区县*/
	@Excel(name = "区县", width = 15)
	private String district;
	/**营业开始时间*/
	@Excel(name = "营业开始时间", width = 15)
	private String onTime;
	/**营业结束时间*/
	@Excel(name = "营业结束时间", width = 15)
	private String offTime;
	/**是否营业*/
	@Excel(name = "是否营业", width = 15)
	private Integer onDuty;
	/**店铺分类*/
	@Excel(name = "店铺分类", width = 15)
	private String shopType;

	/**排序*/
	@Excel(name = "排序", width = 15)
	private Integer orderNum;
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
	
	@ExcelCollection(name="社区分享配送店铺管理员")
	private List<NshareDistriShopAdmin> admins;
	@ExcelCollection(name="社区分享店铺配送点")
	private List<NshareDistriShopStation> stations;
	@ExcelCollection(name="社区分享配送店铺商品分类")
	private List<NshareDistriShopGoodsCat> cats;
	@ExcelCollection(name="社区分享配送店铺图片")
	private List<String> imgList;
	@ExcelCollection(name="社区分享店铺配送社群")
	private List<Map> teams;
}
