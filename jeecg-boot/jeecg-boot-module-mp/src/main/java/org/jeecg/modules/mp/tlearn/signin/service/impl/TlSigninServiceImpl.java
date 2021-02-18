package org.jeecg.modules.mp.tlearn.signin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.tlearn.signin.entity.TlSignin;
import org.jeecg.modules.mp.tlearn.signin.mapper.TlSigninMapper;
import org.jeecg.modules.mp.tlearn.signin.service.ITlSigninService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2020-08-18 11:33:54
 **/
@Service
public class TlSigninServiceImpl extends ServiceImpl<TlSigninMapper, TlSignin> implements ITlSigninService {
    @Autowired
    TlSigninMapper tlSigninMapper;
    @Override
    public List<Map> selectAllTlSignin(String uid) {
        List<Map> list=tlSigninMapper.selectAllTlSignin();
        if (!CollectionUtils.isEmpty(list)) {
            for (Map m : list) {
                if (m == null || m.isEmpty()) {
                    continue;
                }
                String siginId = m.get("id").toString();
                //排行前五的
                IPage<Map> topPage = this.loadPagerRankingList(5, 1, siginId,null);
                m.put("top", topPage == null ? null : topPage.getRecords());

                //统计我参与的最长连续天数，累计时长
                List<Map> mine = this.selectSigninMine(siginId, uid);
                Map mmap = new HashMap();
                if (!CollectionUtils.isEmpty(mine)) {
                    mmap.put("days", Integer.parseInt(mine.get(0).get("days").toString())+1);
                    int seconds = 0;
                    for (Map mm : mine) {
                        if(mm.containsKey("seconds")&&mm.get("seconds")!=null){
                            seconds += Integer.parseInt(mm.get("seconds").toString());
                        }
                    }
                    mmap.put("seconds", seconds);
                    m.put("mine", mmap);
                }
            }
        }
        return list;
    }

    @Override
    public List<Map> selectSigninUsers(String id) {
        return null;
    }

    @Override
    public List<Map> selectSigninMine(String id, String uid) {
        return tlSigninMapper.selectSigninMine(id,uid);
    }

    @Override
    public List<Map> selectSigninCurTerm(String signinId, String userId){
        return tlSigninMapper.selectSigninCurTerm(signinId,userId);
    }
    @Override
    public IPage<Map> loadPagerRankingList(int pageSize, int pageNo, String signinId, String userId){
        Page<Map> page = new Page<>(pageNo, pageSize);
        return tlSigninMapper.loadPagerRankingList(page,signinId,userId);
    }
}
