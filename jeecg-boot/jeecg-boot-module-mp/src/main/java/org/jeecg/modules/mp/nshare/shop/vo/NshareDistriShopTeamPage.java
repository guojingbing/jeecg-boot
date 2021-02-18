package org.jeecg.modules.mp.nshare.shop.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 社区分享配送店铺管理员
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Data
public class NshareDistriShopTeamPage implements Serializable {
    private static final long serialVersionUID = 1L;
	private String id;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**店铺*/
	private String shopId;
	/**社群主键*/
	@Excel(name = "社群主键", width = 15)
	private String teamId;
	/**站点名称*/
	@Excel(name = "社群名称", width = 15)
	private String teamName;
	/**区域*/
	@Excel(name = "区域", width = 15)
	private String district;
	/**城市*/
	@Excel(name = "城市", width = 15)
	private String city;
	/**省份*/
	@Excel(name = "省份", width = 15)
	private String province;
	/**国家*/
	@Excel(name = "国家", width = 15)
	private String country;
	/**区域代码*/
	@Excel(name = "区域代码", width = 15)
	private String adcode;
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
	private String address;
}
