package org.jeecg.modules.mp.nshare.goods.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;

/**
 * @Description: 社区分享店铺配送商品每日信息
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Data
@TableName("nshare_distri_shop_goods_daily")
public class NshareDistriShopGoodsDaily implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**商品*/
	private String goodsId;
	/**标准价格*/
	@Excel(name = "标准价格", width = 15)
	private Double normPrice;
	/**销售价格*/
	@Excel(name = "销售价格", width = 15)
	private Double salePrice;
	/**单位*/
	@Excel(name = "单位", width = 15)
	private String priceUnit;
	/**是否上架*/
	@Excel(name = "是否上架", width = 15)
	private String onSale;
	/**销售日期*/
	@Excel(name = "销售日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date saleDate;
}
