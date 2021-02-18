package org.jeecg.modules.mp.nshare.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 社区分享配送店铺
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Data
@TableName("nshare_distri_shop")
public class NshareDistriShop implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**店铺码*/
    private String shopCode;
	/**店铺名称*/
    private String shopName;
	/**店铺描述*/
    private String shopDesc;
	/**店铺级别*/
    private String shopLevel;
	/**店主*/
    private String ownerId;
    /**店主电话*/
    private String ownerPhone;
	/**店铺状态*/
    private Integer shopStatus;

    /**店铺地址*/
    private String address;
    /**店铺地址补充*/
    private String addrMore;
    /**经度*/
    private java.math.BigDecimal lng;
    /**维度*/
    private java.math.BigDecimal lat;
    /**区域代码*/
    private String adcode;
    /**国家*/
    private String nation;
    /**省份*/
    private String province;
    /**城市*/
    private String city;
    /**区县*/
    private String district;
    /**营业开始时间*/
    private String onTime;
    /**营业结束时间*/
    private String offTime;
    /**是否营业*/
    private Integer onDuty;
    /**店铺分类*/
    private String shopType;
	/**排序*/
    private Integer orderNum;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**更新人*/
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
