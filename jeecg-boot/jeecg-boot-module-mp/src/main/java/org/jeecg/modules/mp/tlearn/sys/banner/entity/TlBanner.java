package org.jeecg.modules.mp.tlearn.sys.banner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @Description: 轮播
 * @Author:
 * @Date:   2020-01-30
 * @Version: V1.0
 */
@Data
@TableName("tl_banner")
public class TlBanner implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**创建人*/
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**更新人*/
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
	/**所属部门*/
    private String sysOrgCode;
	/**标题*/
    private String title;
	/**描述*/
    private String bannerDesc;
	/**位置代码*/
    private String locCode;
	/**是否使用*/
    private transient String isUseString;

    private byte[] isUse;

    public byte[] getIsUse(){
        if(isUseString==null){
            return null;
        }
        try {
            return isUseString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getIsUseString(){
        if(isUse==null || isUse.length==0){
            return "";
        }
        try {
            return new String(isUse,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
