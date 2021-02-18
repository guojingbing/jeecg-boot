package org.jeecg.modules.mp.nshare.shop.entity;

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
 * @Description: 社区分享配送店铺管理员
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Data
@TableName("nshare_distri_shop_admin")
public class NshareDistriShopAdmin implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**店铺*/
	private String shopId;
	/**用户*/
	@Excel(name = "用户", width = 15)
	private String userId;
	/**管理类型*/
	@Excel(name = "管理类型", width = 15)
	private String adminType;
}
