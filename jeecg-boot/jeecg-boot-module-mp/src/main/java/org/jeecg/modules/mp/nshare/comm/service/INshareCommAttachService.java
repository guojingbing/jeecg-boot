package org.jeecg.modules.mp.nshare.comm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mp.nshare.comm.entity.NshareCommAttach;

import java.util.List;

/**
 * @Description: 社区分享通用附件
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface INshareCommAttachService extends IService<NshareCommAttach> {
    List<NshareCommAttach> selectByBuss(String bussTableName, String bussId, String attachCode);
    void deleteByBuss(String bussTableName, String bussId, String attachCode);
}
