package org.jeecg.modules.mp.tlearn.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 用户收藏的诗词
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
@Data
@TableName("tl_user_sub_poetry")
public class TlUserSubPoetry implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**用户编号*/
	private String userId;
	/**诗词编号*/
	@Excel(name = "诗词编号", width = 15)
	private String poetryId;
}
