package org.jeecg.modules.mp.nshare.comm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.nshare.comm.entity.NshareCommAttach;
import org.jeecg.modules.mp.nshare.comm.mapper.NshareCommAttachMapper;
import org.jeecg.modules.mp.nshare.comm.service.INshareCommAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 社区分享通用附件
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class NshareCommAttachServiceImpl extends ServiceImpl<NshareCommAttachMapper, NshareCommAttach> implements INshareCommAttachService {
    @Autowired
    private NshareCommAttachMapper nshareCommAttachMapper;
    public List<NshareCommAttach> selectByBuss(String bussTableName, String bussId, String attachCode){
        return nshareCommAttachMapper.selectByBuss(bussTableName,bussId,attachCode);
    }
    public void deleteByBuss(String bussTableName, String bussId, String attachCode){
        nshareCommAttachMapper.deleteByBuss(bussTableName,bussId,attachCode);
    }
}
