package org.jeecg.modules.mp.nshare.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 社区分享终端用户
 * @Author:
 * @Date:   2020-02-18
 * @Version: V1.0
 */
@Data
@TableName("nshare_user_team")
public class NshareUserTeam implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**用户编号*/
	@Excel(name = "用户编号", width = 15)
	private String userId;
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
	/**经度*/
	@Excel(name = "经度", width = 15)
	private java.math.BigDecimal longitude;
	/**维度*/
	@Excel(name = "维度", width = 15)
	private java.math.BigDecimal latitude;
	/**审核状态*/
	@Excel(name = "审核状态", width = 15)
    private Integer teamStatus;
	/**站点类型*/
	@Excel(name = "站点类型", width = 15)
	private Integer teamType;
	/**使用状态*/
	@Excel(name = "使用状态", width = 15)
	private Integer useStatus;
}
