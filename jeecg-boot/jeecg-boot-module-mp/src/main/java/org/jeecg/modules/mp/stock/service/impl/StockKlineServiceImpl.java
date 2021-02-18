package org.jeecg.modules.mp.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.mp.stock.entity.StockInfo;
import org.jeecg.modules.mp.stock.entity.StockKline;
import org.jeecg.modules.mp.stock.mapper.StockKlineMapper;
import org.jeecg.modules.mp.stock.service.IStockInfoService;
import org.jeecg.modules.mp.stock.service.IStockKlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class StockKlineServiceImpl extends ServiceImpl<StockKlineMapper, StockKline> implements IStockKlineService {
    @Autowired
    StockKlineMapper enMapper;
    @Resource
    IStockInfoService stockSer;

    @Override
    public List<StockKline> list(String code, String startDate, String endDate,String frequency){
        return enMapper.selectByDateRange(code,startDate,endDate,frequency);
    }
    @Override
    public List<Map> listMap(String code, String startDate, String endDate){
        return enMapper.selectMapByDateRange(code,startDate,endDate);
    }
    @Override
    public IPage<Map> loadList4API(String code, String startDate, String endDate, int pageSize, int pageNo, String key){
        Page<Map> page = new Page<>(pageNo, pageSize);
        return enMapper.loadList4API(page,code,startDate,endDate,key);
    }
    @Override
    @Transactional
    public void syncStockValuationFromBaoStock(String code, String startDate, String endDate,String frequency,String adjustflag){
        List<StockInfo> stocks=null;
        //批量更新
        if(code==null){
            stocks=stockSer.list(1);
        }else{
            StockInfo stock=stockSer.getById(code);
            if(stock!=null){
                stocks=new ArrayList<>();
                stocks.add(stock);
            }
            stocks.add(stock);
        }
        if(!CollectionUtils.isEmpty(stocks)){
            System.out.println(stocks.size()+"只证券需要同步"+startDate+"到"+endDate+"的K线数据");
            Collection list=new ArrayList();
            for(int i=0;i<stocks.size();i++){
                StockInfo stock=stocks.get(i);
                List<StockKline> klines=this.list(stock.getCode(),startDate,endDate,frequency);
                Map dmap=new HashMap();
                if(!CollectionUtils.isEmpty(klines)){
                    for(StockKline kline:klines){
                        dmap.put(DateUtils.formatDate(kline.getDate(),"yyyy-MM-dd"),DateUtils.formatDate(kline.getDate(),"yyyy-MM-dd"));
                    }
                }
                try {
                    String[] args = new String[]{"python", "G:\\workgroup\\stock\\python\\stock-kline4java.py",stock.getCode(),startDate,endDate,frequency,adjustflag};
                    Process pr = Runtime.getRuntime().exec(args);
                    BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(),"GBK"));
                    String line;
                    while ((line = in.readLine()) != null) {
                        JSONArray arr=null;
                        try{
                            arr= JSON.parseArray(line);
                        }catch (Exception ex){
                        }
                        if(arr!=null&&arr.size()>0){
                            if(arr!=null&&arr.size()>0){
                                System.out.println("re>>>"+arr);
                                String dateStr=arr.getString(1);
                                //不存在的数据插入
                                if(dmap.isEmpty()||!dmap.containsKey(dateStr)){
                                    StockKline kline=new StockKline();
                                    kline.setCode(arr.getString(0));
                                    kline.setDate(DateUtils.parseDate(dateStr,"yyyy-MM-dd"));
                                    kline.setOpen(arr.getDouble(2));
                                    kline.setHigh(arr.getDouble(3));
                                    kline.setLow(arr.getDouble(4));
                                    kline.setClose(arr.getDouble(5));
                                    kline.setPreClose(arr.getDouble(6));
                                    kline.setVolume(arr.getDouble(7));
                                    kline.setAmount(arr.getDouble(8));
                                    kline.setTurn(arr.getDouble(9));
                                    kline.setPctChg(arr.getDouble(10));
                                    kline.setPbMrq(arr.getDouble(11));
                                    kline.setPeTtm(arr.getDouble(12));
                                    kline.setPsTtm(arr.getDouble(13));
                                    kline.setPcfNcfTtm(arr.getDouble(14));
                                    kline.setAdjustFlag(arr.getShort(15));
                                    kline.setTradeStatus(arr.getShort(16));
                                    kline.setIsSt(arr.getShort(17));
                                    kline.setFrequency(frequency);
                                    list.add(kline);
                                }
                                System.out.println(list.size()+"条Kline数据获取完成待提交");
                            }
                        }
                    }
                    //每处理100条保存一次
                    if((i%100==0||i==stocks.size()-1)&&!CollectionUtils.isEmpty(list)){
                        this.saveBatch(list,50);
                        System.out.println(list.size()+"条Kline数据写入完成");
                        list.clear();
                    }
                    System.out.println("证券"+stock.getCode()+"BaoStock Kline数据获取处理完成,剩余"+(stocks.size()-i-1));
                    in.close();
                    pr.waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
