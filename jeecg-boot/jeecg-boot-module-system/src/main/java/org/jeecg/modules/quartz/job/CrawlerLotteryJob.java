package org.jeecg.modules.quartz.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottery;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssue;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssuePrize;
import org.jeecg.modules.mp.nshare.lottery.service.INshareLotteryService;
import org.jeecg.modules.mp.nshare.lottery.service.INshareLottteryIssueService;
import org.jeecg.modules.mp.tlearn.idiom.service.ITlKbIdiomService;
import org.jeecg.modules.mp.tlearn.util.CrawlerUtil;
import org.jeecg.modules.system.entity.SysDictItem;
import org.jeecg.modules.system.service.ISysDictItemService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2020-01-10 09:52:20
 **/
public class CrawlerLotteryJob implements Job {
    @Autowired
    ITlKbIdiomService tlKbIdiomService;
    @Autowired
    ISysDictItemService dictSer;
    @Autowired
    ISysDictItemService dictItemSer;
    @Autowired
    private INshareLotteryService nshareLotteryService;
    @Autowired
    private INshareLottteryIssueService nshareLottteryIssueService;
    //成语列表
    public final static String URL_IDIOM = "https://hanyu.baidu.com/hanyu/ajax/search_list?wd=%E6%88%90%E8%AF%AD&from=poem&userid=69040232&pn=";
    //文件存储跟路径
    @Value(value = "${jeecg.path.upload}")
    private String uploadpath;
    public final static String PATH_TONE_PY="\\audio\\idiom\\";

    public final static String DIT_MAINID_IDIOM="1218366870004985858";
    public List<Map> proxys;
    public SysDictItem synIdiomLog;
    //API key
    String[] keyArr=new String[]{"05cebc8a13d81f12","540f28090d68de7d","f214b52afec62d52","bde8cd0a641f1efc"};

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(String.format("彩票期数获取定时任务，时间:" + DateUtils.getTimestamp()));
        syncLotteryIssues();
        System.out.println("彩票期数数据获取定时任务执行完成，时间:" + DateUtils.gettimestamp());
    }

    public void syncLotteryIssues(){
        String mainId="1229007163196710913";
        List<SysDictItem> logs=dictItemSer.selectItemsByMainId(mainId);
        Map logMap=new HashMap();
        if(!CollectionUtils.isEmpty(logs)){
            for(SysDictItem log:logs){
                logMap.put(log.getItemValue(),log);
            }
        }
        List<NshareLottery> lotterys=nshareLotteryService.list();
        for(NshareLottery lott:lotterys){
            String catId=lott.getCatId();
            //仅同步体彩和福彩数据
            if("1".equalsIgnoreCase(catId)||"2".equalsIgnoreCase(catId)){
                String lotteryId=lott.getLotteryId();
                SysDictItem curLog=new SysDictItem();
                int hisPages=0;
                if(logMap.containsKey(lotteryId)){
                    curLog=(SysDictItem)logMap.get(lotteryId);
                    hisPages=Integer.parseInt(curLog.getDescription());
                    curLog.setItemText(lott.getLotteryName());
                }else{
                    curLog.setItemValue(lotteryId);
                    curLog.setDictId(mainId);
                    curLog.setItemText(lott.getLotteryName());
                    curLog.setStatus(1);
                }
                loadAndSaveLotteryIssuesFromJisu(lotteryId,hisPages,20,curLog);
            }
        }
    }

    /**
     * 从极速同步彩票开奖信息
     * @return
     */
    public int loadAndSaveLotteryIssuesFromJisu(String lotteryId, int historyPages, int pageSize, SysDictItem log){
        try {
            String url="https://api.jisuapi.com/caipiao/history";
            int currentPage=1;
            int newPageNum=0;
            for(int k=0;k<keyArr.length;k++){
                while(currentPage>0){
                    Random random=new Random();
                    int sleepTime=random.nextInt(10)*1000;
                    Thread.sleep(sleepTime);

                    Map params=new HashMap();
                    params.put("appkey",keyArr[k]);
                    params.put("caipiaoid",lotteryId);
                    params.put("num",pageSize);
                    params.put("start",pageSize*(currentPage-1));
                    JSONObject json = CrawlerUtil.getForJSONObject(url, params, null);
                    if (json == null) {
                        System.out.println("当前第" + currentPage + "页获取失败");
                        break;
                    }
                    if(json.getIntValue("status")!=0){
                        System.out.println(json.getString("msg"));
                        break;
                    }
                    System.out.println("当前第" + currentPage + "页>>>" + json.toJSONString());

                    JSONObject rslt=json.getJSONObject("result");
                    if (rslt == null) {
                        System.out.println("当前第" + currentPage + "没有结果");
                        break;
                    }
                    //处理数据
                    JSONArray datas = rslt.getJSONArray("list");
                    if(datas==null||datas.size()<1){
                        System.out.println("当前第" + currentPage + "没有数据");
                        break;
                    }
                    int total=rslt.getIntValue("total");

                    //查询历史数据
                    List<NshareLottteryIssue> issues = nshareLottteryIssueService.list();
                    Map skeyMap = new HashMap();
                    if (!CollectionUtils.isEmpty(issues)) {
                        for (int j = 0; j < issues.size(); j++) {
                            NshareLottteryIssue p = issues.get(j);
                            skeyMap.put(p.getLotteryId()+"-"+p.getIssueNo(), p);
                        }
                    }

//                    List<NshareLottteryIssue> list = new ArrayList<>();
                    for (int i = 0; i < datas.size(); i++) {
                        JSONObject a = datas.getJSONObject(i);
                        String issueno=a.getString("issueno");
                        String key=lotteryId+"-"+issueno;
                        if (!skeyMap.isEmpty() && skeyMap.containsKey(lotteryId+"-"+issueno)) {
                            continue;
                        }
                        NshareLottteryIssue entity = new NshareLottteryIssue();
                        entity.setIssueNo(issueno);
                        entity.setJackpot(a.getDoubleValue("totalmoney"));
                        entity.setLotteryId(lotteryId);
                        entity.setNumber(a.getString("number"));
                        entity.setOpenDate(a.getDate("opendate"));
                        entity.setDeadLine(a.getDate("deadline"));
                        entity.setSaleAmount(a.getDoubleValue("saleamount"));
                        entity.setSpeNumber(a.getString("refernumber"));

                        List<NshareLottteryIssuePrize> plist = new ArrayList<>();
                        if(a.containsKey("prize")&&!"false".equalsIgnoreCase(a.getString("prize"))){
                            JSONArray prizes = a.getJSONArray("prize");
                            for (int j = 0; j < prizes.size(); j++) {
                                JSONObject b = prizes.getJSONObject(j);
                                NshareLottteryIssuePrize prize=new NshareLottteryIssuePrize();
                                prize.setLotteryId(lotteryId);
                                prize.setNum(b.getIntValue("num"));
                                prize.setPerBonus(b.getDoubleValue("singlebonus"));
                                prize.setPrizeName(b.getString("prizename"));
                                prize.setPrizeRule(b.getString("require"));
                                plist.add(prize);
                            }
                        }
                        nshareLottteryIssueService.saveMain(entity,plist);
                        skeyMap.put(key, entity);
//                        list.add(entity);
                    }
//                    //批量保存信息
//                    if(!CollectionUtils.isEmpty(list)){
//                        nshareLottteryIssueService.saveOrUpdateBatch(list);
//                        System.out.println("保存【"+lotteryId+"】开奖数量>>>"+list.size());
//                    }
                    //已经同步页数加1
                    historyPages++;
                    newPageNum++;
                    //每次获取的第一页用于获取总页数，数据实际未从后面页数获取
                    if(newPageNum>1){
                        //更新同步日志
                        log.setDescription(historyPages+"");
                        dictItemSer.saveOrUpdate(log);
                    }

                    int totalPage=(int)Math.ceil(total/pageSize);
                    currentPage=totalPage-historyPages;
                }
            }
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static void main(String args[]){
        CrawlerLotteryJob job=new CrawlerLotteryJob();
    }
}
