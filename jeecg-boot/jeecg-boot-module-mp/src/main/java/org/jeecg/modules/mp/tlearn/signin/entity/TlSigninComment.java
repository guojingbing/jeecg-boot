package org.jeecg.modules.mp.tlearn.signin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 打卡用户评论
 * @Author:
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Data
@TableName("tl_signin_comment")
public class TlSigninComment implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**打卡信息主键*/
    private String signinId;
    /**参与用户编号*/
    private String userId;
	/**评论内容*/
    private String content;
    /**回复的子评论编号*/
    private String replyId;
    /**回复的评论编号*/
    private String parentId;
    /**发布时间*/
    @Excel(name = "发布时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
