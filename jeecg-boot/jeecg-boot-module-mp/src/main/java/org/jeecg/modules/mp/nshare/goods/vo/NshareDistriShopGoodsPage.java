package org.jeecg.modules.mp.nshare.goods.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.mp.nshare.goods.entity.NshareDistriShopGoodsDaily;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: 社区分享配送店铺商品
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Data
public class NshareDistriShopGoodsPage {
	
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
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
	private String goodsName;
	/**商品描述*/
	@Excel(name = "商品描述", width = 15)
	private String goodsDesc;
	/**店铺*/
	@Excel(name = "店铺", width = 15)
	private String shopId;
	
	@ExcelCollection(name="社区分享店铺配送商品每日信息")
	private List<NshareDistriShopGoodsDaily> nshareDistriShopGoodsDailyList;
	
}
