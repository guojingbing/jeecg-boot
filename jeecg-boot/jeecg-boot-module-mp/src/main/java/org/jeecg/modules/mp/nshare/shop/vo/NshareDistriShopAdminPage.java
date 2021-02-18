package org.jeecg.modules.mp.nshare.shop.vo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 社区分享配送店铺管理员
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Data
public class NshareDistriShopAdminPage implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@Excel(name = "主键", width = 15)
	private String id;
	/**创建日期*/
	@Excel(name = "创建日期", width = 15)
	private Date createTime;
	/**店铺*/
	@Excel(name = "店铺", width = 15)
	private String shopId;
	/**用户*/
	@Excel(name = "用户", width = 15)
	private String userId;
	/**管理类型*/
	@Excel(name = "管理类型", width = 15)
	private String adminType;
	/**头像*/
	@Excel(name = "头像", width = 15)
	private String avatar;
}
