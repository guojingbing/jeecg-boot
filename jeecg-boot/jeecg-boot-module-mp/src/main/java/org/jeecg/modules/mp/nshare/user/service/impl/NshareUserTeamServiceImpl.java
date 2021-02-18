package org.jeecg.modules.mp.nshare.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.user.entity.NshareUserTeam;
import org.jeecg.modules.mp.nshare.user.mapper.NshareUserTeamMapper;
import org.jeecg.modules.mp.nshare.user.service.INshareUserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享社群注册
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareUserTeamServiceImpl extends ServiceImpl<NshareUserTeamMapper, NshareUserTeam> implements INshareUserTeamService {
    @Autowired
    private NshareUserTeamMapper nshareUserTeamMapper;
    @Override
    public IPage<Map> loadList4API(int pageSize, int pageNo, BigDecimal lng, BigDecimal lat, String searchKey, String userId,String shopId) {
        Page<Map> page = new Page<>(pageNo, pageSize);
        return nshareUserTeamMapper.loadList4API(page,lng,lat,searchKey,userId,shopId);
    }

    @Override
    public List<NshareUserTeam> selectByMainId(String userId){
        return nshareUserTeamMapper.selectByMainId(userId);
    }
}
