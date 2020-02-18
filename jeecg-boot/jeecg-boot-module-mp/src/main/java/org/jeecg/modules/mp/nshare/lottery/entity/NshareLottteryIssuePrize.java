package org.jeecg.modules.mp.nshare.lottery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 社区分享彩票奖项
 * @Author:
 * @Date:   2020-02-16
 * @Version: V1.0
 */
@Data
@TableName("nshare_lotttery_issue_prize")
public class NshareLottteryIssuePrize implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**彩票种类*/
	@Excel(name = "彩票种类", width = 15)
	private String lotteryId;
	/**开奖期数*/
	private String issueId;
	/**奖项名称*/
	@Excel(name = "奖项名称", width = 15)
	private String prizeName;
	/**中奖规则*/
	@Excel(name = "中奖规则", width = 15)
	private String prizeRule;
	/**单注奖金*/
	@Excel(name = "单注奖金", width = 15)
	private Double perBonus;
	/**中奖人数*/
	@Excel(name = "中奖人数", width = 15)
	private Integer num;
}
