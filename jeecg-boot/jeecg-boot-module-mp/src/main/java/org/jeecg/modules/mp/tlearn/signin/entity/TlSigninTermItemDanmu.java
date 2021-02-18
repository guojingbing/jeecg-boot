package org.jeecg.modules.mp.tlearn.signin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 弹幕
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Data
@TableName("tl_signin_term_item_danmu")
public class TlSigninTermItemDanmu implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**参与用户编号*/
    private String userId;
    /**参与项目主键*/
    private String itemId;
    /**打卡参与主键*/
    private String termId;
	/**内容*/
    private String content;
    /**出现位置*/
    private Integer showSeconds;
}
