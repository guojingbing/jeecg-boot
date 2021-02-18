package org.jeecg.modules.mp.nshare.comm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mp.nshare.comm.entity.NshareCommAttach;

import java.util.List;

/**
 * @Description: 社区分享终端用户
 * @Author:
 * @Date: 2020-02-17
 * @Version: V1.0
 */
public interface NshareCommAttachMapper extends BaseMapper<NshareCommAttach> {
    List<NshareCommAttach> selectByBuss(@Param("bussTableName") String bussTableName, @Param("bussId") String bussId, @Param("attachCode") String attachCode);
    boolean deleteByBuss(@Param("bussTableName") String bussTableName, @Param("bussId") String bussId, @Param("attachCode") String attachCode);
}
