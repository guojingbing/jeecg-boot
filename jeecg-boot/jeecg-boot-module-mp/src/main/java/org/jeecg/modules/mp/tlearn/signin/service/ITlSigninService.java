package org.jeecg.modules.mp.tlearn.signin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSignin;

import java.util.List;
import java.util.Map;

/**
 * @Description: 打卡
 * @Author:
 * @Date:   2020-01-03
 * @Version: V1.0
 */
public interface ITlSigninService extends IService<TlSignin> {
    List<Map> selectAllTlSignin(String uid);
    List<Map> selectSigninUsers(@Param("id") String id);
    List<Map> selectSigninCurTerm(@Param("signinId") String signinId,@Param("userId") String userId);
    List<Map> selectSigninMine(@Param("id") String id,@Param("uid") String uid);
    IPage<Map> loadPagerRankingList(int pageSize, int pageNo, String signinId, String userId);
}
