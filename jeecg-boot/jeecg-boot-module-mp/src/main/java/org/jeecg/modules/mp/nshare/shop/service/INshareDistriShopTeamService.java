package org.jeecg.modules.mp.nshare.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.shop.entity.NshareDistriShopTeam;

import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享店铺配送社群
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareDistriShopTeamService extends IService<NshareDistriShopTeam> {

	List<NshareDistriShopTeam> selectByMainId(String mainId);

	List<Map> selectShopTeams(String mainId);
}
