package org.jeecg.modules.mp.nshare.lottery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 社区分享彩票信息
 * @Author:
 * @Date:   2020-02-16
 * @Version: V1.0
 */
@Data
@TableName("nshare_lottery")
public class NshareLottery implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**彩票id*/
	@Excel(name = "彩票id", width = 15)
    private String lotteryId;
	/**名称*/
	@Excel(name = "名称", width = 15)
    private String lotteryName;
	/**分类*/
	@Excel(name = "分类", width = 15)
    private String catId;
}
