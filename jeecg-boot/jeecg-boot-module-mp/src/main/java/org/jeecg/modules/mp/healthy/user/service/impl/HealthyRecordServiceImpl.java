package org.jeecg.modules.mp.healthy.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mp.healthy.user.entity.HealthyRecord;
import org.jeecg.modules.mp.healthy.user.mapper.HealthyRecordMapper;
import org.jeecg.modules.mp.healthy.user.service.IHealthyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 社区分享终端用户
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class HealthyRecordServiceImpl extends ServiceImpl<HealthyRecordMapper, HealthyRecord> implements IHealthyRecordService {
    @Autowired
    private HealthyRecordMapper healthyRecordMapper;
    @Override
    public void saveRecBatch(JSONArray objArr){
        if(objArr!=null&&objArr.size()>0){
            List objs=new ArrayList<>();
            for(int i=0;i<objArr.size();i++){
                JSONObject obj=objArr.getJSONObject(i);
                HealthyRecord rec=new HealthyRecord();
                rec.setUserId(obj.getString("userId"));
                rec.setCreateTime(new Date());
                rec.setHtype(obj.getShort("htype"));
                rec.setParam1(obj.getDouble("param1"));
                rec.setParam2(obj.getDouble("param2"));
                rec.setParam3(obj.getDouble("param3"));
                rec.setParamExtra(obj.getString("paramExtra"));
                rec.setRecDate(obj.getDate("recDate"));
                rec.setSrc(obj.getShort("src"));
                objs.add(rec);
            }
            saveBatch(objs);
        }
    }
    @Override
    public IPage<Map> loadList4API(int pageSize, int pageNo, String userId, Integer htype, String startDate, String endDate) {
        Page<Map> page = new Page<>(pageNo, pageSize);
        return healthyRecordMapper.loadList4API(page,userId,htype,startDate,endDate);
    }
}
