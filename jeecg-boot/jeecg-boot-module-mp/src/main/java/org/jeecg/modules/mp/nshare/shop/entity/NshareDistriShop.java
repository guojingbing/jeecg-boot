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
 * @Date:   2020-02-18
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
	/**店铺地址*/
    private String address;
	/**店铺级别*/
    private String shopLevel;
	/**图片路径*/
    private String picPath;
	/**店主*/
    private String ownerId;
	/**店主电话*/
    private String ownerPhone;
	/**授权认证码*/
    private String authCode;
	/**创建人*/
    private String createBy;
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
