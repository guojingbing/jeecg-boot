package org.jeecg.modules.mp.tlearn.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubIdiom;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubPoetry;
import org.jeecg.modules.mp.tlearn.user.entity.TlUserSubTag;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: 小程序用户
 * @Author:
 * @Date:   2020-02-03
 * @Version: V1.0
 */
@Data
public class TlUserPage {
	
	/**主键*/
	private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
	private String sysOrgCode;
	/**昵称*/
	@Excel(name = "昵称", width = 15)
	private String nickName;
	/**性别*/
	@Excel(name = "性别", width = 15)
	private Integer gender;
	/**城市*/
	@Excel(name = "城市", width = 15)
	private String city;
	/**省份*/
	@Excel(name = "省份", width = 15)
	private String province;
	/**国家*/
	@Excel(name = "国家", width = 15)
	private String country;
	/**头像*/
	@Excel(name = "头像", width = 15)
	private String avatarUrl;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
	private String phone;
	/**级别*/
	@Excel(name = "级别", width = 15)
	private Integer level;
	/**子级别*/
	@Excel(name = "子级别", width = 15)
	private Integer levelSub;
	/**openid*/
	@Excel(name = "openid", width = 15)
	private String openid;
	/**unionid*/
	@Excel(name = "unionid", width = 15)
	private String unionid;
	
	@ExcelCollection(name="用户收藏的诗词")
	private List<TlUserSubPoetry> tlUserSubPoetryList;
	@ExcelCollection(name="用户收藏的成语")
	private List<TlUserSubIdiom> tlUserSubIdiomList;
	@ExcelCollection(name="用户订阅的诗词分类")
	private List<TlUserSubTag> tlUserSubTagList;
	
}
