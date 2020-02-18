package org.jeecg.modules.mp.nshare.order.entity;

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
 * @Description: 社区分享配送订单商品
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Data
@TableName("nshare_distri_order_goods")
public class NshareDistriOrderGoods implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**订单*/
	private String orderId;
	/**商品*/
	@Excel(name = "商品", width = 15)
	private String goodsId;
	/**数量*/
	@Excel(name = "数量", width = 15)
	private Double orderNum;
	/**单位*/
	@Excel(name = "单位", width = 15)
	private String saleUnit;
	/**购买单价*/
	@Excel(name = "购买单价", width = 15)
	private Double salePrice;
}
