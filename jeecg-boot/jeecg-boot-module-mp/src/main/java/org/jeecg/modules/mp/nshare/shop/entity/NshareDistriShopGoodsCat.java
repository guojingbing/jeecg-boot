package org.jeecg.modules.mp.nshare.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 社区分享配送店铺商品分类
 * @Author:
 * @Date:   2020-03-05
 * @Version: V1.0
 */
@Data
@TableName("nshare_distri_shop_goods_cat")
public class NshareDistriShopGoodsCat implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**店铺编号*/
	private String shopId;
	/**品类编号*/
	@Excel(name = "品类编号", width = 15)
	private String catId;
	/**品类名称*/
	@Excel(name = "品类名称", width = 15)
	private String catName;
	/**排序*/
	@Excel(name = "排序", width = 15)
	private Integer orderNum;
}
