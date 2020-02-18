package org.jeecg.modules.mp.tlearn.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 用户订阅的诗词分类
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
@Data
@TableName("tl_user_sub_tag")
public class TlUserSubTag implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**用户编号*/
	private String userId;
	/**订阅分类*/
	@Excel(name = "订阅分类", width = 15)
	private String tag;
}
