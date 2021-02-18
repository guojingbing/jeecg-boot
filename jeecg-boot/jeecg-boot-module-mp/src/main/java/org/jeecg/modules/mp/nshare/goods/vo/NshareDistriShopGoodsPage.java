package org.jeecg.modules.mp.nshare.goods.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopPromotion;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: 社区分享配送店铺商品
 * @Author:
 * @Date:   2020-03-05
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
	/**店铺*/
	@Excel(name = "店铺", width = 15)
	private String shopId;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
	private String goodsName;
	/**商品代码*/
	@Excel(name = "商品代码", width = 15)
	private String goodsCode;
	/**商品分类*/
	@Excel(name = "商品分类", width = 15)
	private String goodsCat;
	/**商品描述*/
	@Excel(name = "商品描述", width = 15)
	private String goodsDesc;
	/**标准价格*/
	@Excel(name = "标准价格", width = 15)
	private Double normPrice;
	/**销售价格*/
	@Excel(name = "销售价格", width = 15)
	private Double salePrice;
	/**单位*/
	@Excel(name = "单位", width = 15)
	private String priceUnit;
	/**库存数量*/
	@Excel(name = "库存数量", width = 15)
	private String skuNum;
	/**是否上架*/
	@Excel(name = "是否上架", width = 15)
	private Integer onSale;
	/**是否显示价格*/
	@Excel(name = "是否显示价格", width = 15)
	private Integer showPrice;
	/**是否主推产品*/
	@Excel(name = "是否主推产品", width = 15)
	private Integer mainRec;

	@ExcelCollection(name="社区分享配送店铺商品图片")
	private List<String> imgList;

    @ExcelCollection(name="社区分享配送店铺商品详情图片")
    private List<String> dimgList;

	@ExcelCollection(name="社区分享配送店铺商品详情图片")
	private List<NshareDistriShopPromotion> promoList;
}
