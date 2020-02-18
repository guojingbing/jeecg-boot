package org.jeecg.modules.mp.nshare.shop.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopAdmin;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopStation;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: 社区分享配送店铺
 * @Author:
 * @Date:   2020-02-18
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
	/**店铺地址*/
	@Excel(name = "店铺地址", width = 15)
	private String address;
	/**店铺级别*/
	@Excel(name = "店铺级别", width = 15)
	private String shopLevel;
	/**图片路径*/
	@Excel(name = "图片路径", width = 15)
	private String picPath;
	/**店主*/
	@Excel(name = "店主", width = 15)
	private String ownerId;
	/**店主电话*/
	@Excel(name = "店主电话", width = 15)
	private String ownerPhone;
	/**授权认证码*/
	@Excel(name = "授权认证码", width = 15)
	private String authCode;
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
	
	@ExcelCollection(name="社区分享配送店铺管理员")
	private List<NshareDistriShopAdmin> nshareDistriShopAdminList;
	@ExcelCollection(name="社区分享店铺配送点")
	private List<NshareDistriShopStation> nshareDistriShopStationList;
	
}
