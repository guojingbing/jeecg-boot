package org.jeecg.modules.mp.nshare.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.user.entity.NshareUserTeam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享社群注册
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareUserTeamService extends IService<NshareUserTeam> {
    IPage<Map> loadList4API(int pageSize, int pageNo, BigDecimal lng, BigDecimal lat, String searchKey, String userId,String shopId);

    List<NshareUserTeam> selectByMainId(String userId);
}
