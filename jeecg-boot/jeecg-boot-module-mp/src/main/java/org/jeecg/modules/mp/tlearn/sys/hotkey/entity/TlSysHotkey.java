package org.jeecg.modules.mp.tlearn.sys.hotkey.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 搜索记录
 * @Author:
 * @Date:   2020-02-01
 * @Version: V1.0
 */
@Data
@TableName("tl_sys_hotkey")
public class TlSysHotkey implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**搜索字段*/
	@Excel(name = "搜索字段", width = 15)
    private String hotKey;
	/**搜索分类*/
	@Excel(name = "搜索分类", width = 15)
    private String keyCat;
	/**搜索次数*/
	@Excel(name = "搜索次数", width = 15)
    private Integer searchNum;
}
