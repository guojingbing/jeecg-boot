package org.jeecg.modules.mp.tlearn.signin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSignin;

import java.util.List;
import java.util.Map;

/**
 * @Description: 打卡
 * @Author:
 * @Date:
 * @Version: V1.0
 */
public interface TlSigninMapper extends BaseMapper<TlSignin> {
    IPage<Map> loadPagerRankingList(Page<Map> page, String signinId, String userId);
    List<Map> selectAllTlSignin();
    List<Map> selectSigninUsers(@Param("id") String id);
    List<Map> selectSigninMine(@Param("id") String id,@Param("uid") String uid);
    List<Map> selectSigninCurTerm(@Param("signinId") String signinId,@Param("userId") String userId);
}
