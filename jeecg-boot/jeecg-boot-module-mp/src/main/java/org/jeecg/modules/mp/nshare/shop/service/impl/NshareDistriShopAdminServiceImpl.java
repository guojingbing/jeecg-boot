package org.jeecg.modules.mp.nshare.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopAdmin;
import org.jeecg.modules.mp.nshare.shop.mapper.NshareDistriShopAdminMapper;
import org.jeecg.modules.mp.nshare.shop.service.INshareDistriShopAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 社区分享配送店铺管理员
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareDistriShopAdminServiceImpl extends ServiceImpl<NshareDistriShopAdminMapper, NshareDistriShopAdmin> implements INshareDistriShopAdminService {
	
	@Autowired
	private NshareDistriShopAdminMapper nshareDistriShopAdminMapper;
	
	@Override
	public List<NshareDistriShopAdmin> selectByMainId(String mainId) {
		return nshareDistriShopAdminMapper.selectByMainId(mainId);
	}
}
