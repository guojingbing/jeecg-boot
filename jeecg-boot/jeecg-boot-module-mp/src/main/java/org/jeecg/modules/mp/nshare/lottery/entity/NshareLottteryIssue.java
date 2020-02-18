package org.jeecg.modules.mp.nshare.lottery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 社区分享彩票期数
 * @Author:
 * @Date:   2020-02-16
 * @Version: V1.0
 */
@Data
@TableName("nshare_lotttery_issue")
public class NshareLottteryIssue implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**彩票编号*/
    private String lotteryId;
	/**期号*/
    private String issueNo;
	/**开奖号码*/
    private String number;
	/**特别号码*/
    private String speNumber;
	/**销售额*/
    private Double saleAmount;
	/**奖池金额*/
    private Double jackpot;
	/**开奖日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date openDate;
	/**兑奖截止日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date deadLine;
}
