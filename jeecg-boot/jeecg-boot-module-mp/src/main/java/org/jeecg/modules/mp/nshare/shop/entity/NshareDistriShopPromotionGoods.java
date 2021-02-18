package org.jeecg.modules.mp.nshare.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 社区分享店铺活动商品
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Data
@TableName("nshare_distri_shop_promotion_goods")
public class NshareDistriShopPromotionGoods implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;

	@Excel(name = "活动主键", width = 15)
	private String promoId;

	@Excel(name = "商品编号", width = 15)
	private String goodsId;

	@Excel(name = "商品名称", width = 15)
	private String goodsName;

	/**标准价格*/
	@Excel(name = "标准价格", width = 15)
	private Double normPrice;

	/**销售价格*/
	@Excel(name = "销售价格", width = 15)
	private Double salePrice;

	/**单位*/
	@Excel(name = "单位", width = 15)
	private String priceUnit;

	/**总数量*/
	@Excel(name = "总数量", width = 15)
	private Integer totalNum;

	/**最多购买数量*/
	@Excel(name = "最多购买", width = 15)
	private Integer maxNum;

	@Excel(name = "排序", width = 15)
	private Integer orderNum;
}
