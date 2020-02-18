package org.jeecg.modules.mp.nshare.lottery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssuePrize;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: 社区分享彩票期数
 * @Author:
 * @Date:   2020-02-16
 * @Version: V1.0
 */
@Data
public class NshareLottteryIssuePage {
	
	/**主键*/
	private String id;
	/**彩票编号*/
	@Excel(name = "彩票编号", width = 15)
	private String lotteryId;
	/**期号*/
	@Excel(name = "期号", width = 15)
	private String issueNo;
	/**开奖号码*/
	@Excel(name = "开奖号码", width = 15)
	private String number;
	/**特别号码*/
	@Excel(name = "特别号码", width = 15)
	private String speNumber;
	/**销售额*/
	@Excel(name = "销售额", width = 15)
	private Double saleAmount;
	/**奖池金额*/
	@Excel(name = "奖池金额", width = 15)
	private Double jackpot;
	/**开奖日期*/
	@Excel(name = "开奖日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date openDate;
	/**兑奖截止日期*/
	@Excel(name = "兑奖截止日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date deadLine;
	
	@ExcelCollection(name="社区分享彩票奖项")
	private List<NshareLottteryIssuePrize> nshareLottteryIssuePrizeList;
	
}
