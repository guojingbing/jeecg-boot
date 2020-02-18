package org.jeecg.modules.mp.nshare.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopAdmin;

import java.util.List;

/**
 * @Description: 社区分享配送店铺管理员
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareDistriShopAdminService extends IService<NshareDistriShopAdmin> {

	public List<NshareDistriShopAdmin> selectByMainId(String mainId);
}
