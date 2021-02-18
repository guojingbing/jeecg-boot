package org.jeecg.modules.mp.nshare.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.user.entity.NshareUserTeam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享终端用户
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface NshareUserTeamMapper extends BaseMapper<NshareUserTeam> {
    IPage<Map> loadList4API(Page<Map> page, BigDecimal lng, BigDecimal lat, String searchKey, String userId,String shopId);

    List<NshareUserTeam> selectByMainId(@Param("mainId") String mainId);
}
