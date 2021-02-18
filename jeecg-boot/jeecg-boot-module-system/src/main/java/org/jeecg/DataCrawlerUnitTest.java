package org.jeecg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottery;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssue;
import org.jeecg.modules.mp.nshare.lottery.entity.NshareLottteryIssuePrize;
import org.jeecg.modules.mp.nshare.lottery.service.INshareLotteryService;
import org.jeecg.modules.mp.nshare.lottery.service.INshareLottteryIssuePrizeService;
import org.jeecg.modules.mp.nshare.lottery.service.INshareLottteryIssueService;
import org.jeecg.modules.mp.tlearn.joke.entity.TlKbJoke;
import org.jeecg.modules.mp.tlearn.joke.service.ITlKbJokeService;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbBookChapter;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbBookContent;
import org.jeecg.modules.mp.tlearn.kb.entity.TlKbQa;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbBookChapterService;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbBookContentService;
import org.jeecg.modules.mp.tlearn.kb.service.ITlKbQaService;
import org.jeecg.modules.mp.tlearn.util.CrawlerUtil;
import org.jeecg.modules.system.entity.SysDictItem;
import org.jeecg.modules.system.service.ISysDictItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2020-02-09 17:42:43
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DataCrawlerUnitTest {
    @Autowired
    ITlKbJokeService tlKbJokeService;
    @Autowired
    private ITlKbBookChapterService tlKbBookChapterService;
    @Autowired
    private ITlKbBookContentService tlKbBookContentService;
    @Autowired
    private ITlKbQaService tlKbQaService;
    @Autowired
    private INshareLotteryService nshareLotteryService;
    @Autowired
    private INshareLottteryIssueService nshareLottteryIssueService;
    @Autowired
    private INshareLottteryIssuePrizeService nshareLottteryIssuePrizeService;
    @Autowired
    ISysDictItemService dictItemSer;
    SysDictItem syncJokeLog;
    final static String DIT_ITEMID_SYNLOG_JOKE = "1219216552037998594";
    final static String URL_JOKE_A = "http://v.juhe.cn/joke/content/list.php";
    final static String URL_JOKE_B = "https://api.jisuapi.com/xiaohua/text";

    //书籍
    final static String DIT_MAINID_BOOKS = "1212644360118607875";
    //问答
    final static String DIT_MAINID_QAS = "1212644360118607876";
    String[] keyArr=new String[]{"05cebc8a13d81f12","540f28090d68de7d","f214b52afec62d52","bde8cd0a641f1efc"};
//    @Test
//    public void syncJokeDataWithLog(){
//        syncJokeLog = dictItemSer.getById(DIT_ITEMID_SYNLOG_JOKE);
//        if(syncJokeLog!=null){
//            String valStr=syncJokeLog.getItemValue();
//            if(!StringUtils.isEmpty(valStr)){
//                String API="AVATAR";
//                JSONArray logArr = JSONObject.parseArray(valStr);
//                JSONObject curLog=null;
//                if(logArr!=null){
//                    for (int i = 0; i < logArr.size(); i++) {
//                        JSONObject log = logArr.getJSONObject(i);
//                        if (log.get("API").toString().equalsIgnoreCase(API)) {
//                            curLog = log;
//                            break;
//                        }
//                    }
//                }
//                long timestamp=System.currentTimeMillis()/1000;
//                String sort="desc";
//                if(curLog!=null){
//                    timestamp= curLog.getLongValue("lastSyncTime");
//                    sort="asc";
//                }
//                for(int i=1;i<1001;i++){
//                    int re=crawlAndSaveData(timestamp,i,sort);
//                    if(re==0){
//                        break;
//                    }
//                }
//
//                curLog=null;
//                API="JISU";
//                if(logArr!=null){
//                    for (int i = 0; i < logArr.size(); i++) {
//                        JSONObject log = logArr.getJSONObject(i);
//                        if (log.get("API").toString().equalsIgnoreCase(API)) {
//                            curLog = log;
//                            break;
//                        }
//                    }
//                }
//
//                for(int i=1;i<1001;i++){
//                    int re=crawlAndSaveJokeDataFromJisu(i,20);
//                    if(re==0){
//                        break;
//                    }
//                }
//
//            }
//        }
//    }

    /**
     * 获取笑话
     */
    @Test
    public void syncJokeData(){
        int hisPages=0;
        syncJokeLog = dictItemSer.getById(DIT_ITEMID_SYNLOG_JOKE);
        if(syncJokeLog!=null&&!StringUtils.isEmpty(syncJokeLog.getItemValue())){
            JSONObject obj=JSONObject.parseObject(syncJokeLog.getItemValue());
            hisPages=obj.getIntValue("page");
        }
        int re=crawlAndSaveJokeDataFromJisu(hisPages,20);
    }

    /**
     * 获取书籍章节列表
     */
    @Test
    public void syncBookChapters(){
        List<SysDictItem> books=dictItemSer.selectItemsByMainId(DIT_MAINID_BOOKS);
        if(!CollectionUtils.isEmpty(books)){
            for (SysDictItem book:books){
                if(StringUtils.isEmpty(book.getDescription())){
                    continue;
                }
                if(book.getId().equalsIgnoreCase("1225289873796198411")||book.getId().equalsIgnoreCase("1225289873796198413")){
                    continue;
                }

                JSONArray urlArr = JSONObject.parseArray(book.getDescription());
                crawlAndSaveBookChaptersFromJisu(urlArr.get(0).toString(),book.getId());
                System.out.println(book.getItemText()+" 章节同步完成");
            }
        }
    }

    /**
     * 获取数据章节内容
     */
    @Test
    public void syncBookContents(){
        List<SysDictItem> books=dictItemSer.selectItemsByMainId(DIT_MAINID_BOOKS);
        if(!CollectionUtils.isEmpty(books)){
            for (SysDictItem book:books){
                if(StringUtils.isEmpty(book.getDescription())){
                    continue;
                }

                JSONArray urlArr = JSONObject.parseArray(book.getDescription());
                crawlAndSaveBookContentsFromJisu(urlArr.get(1).toString(),book.getId());
                System.out.println(book.getItemText()+" 章节内容同步完成");
            }
        }
    }

    /**
     * 获取问答数据
     */
    @Test
    public void syncQas(){
        List<SysDictItem> qas=dictItemSer.selectItemsByMainId(DIT_MAINID_QAS);
        if(!CollectionUtils.isEmpty(qas)){
            for (SysDictItem item:qas){
                if(StringUtils.isEmpty(item.getDescription())){
                    continue;
                }
                crawlAndSaveQaFromJisu(item.getDescription(),item.getItemValue());
                System.out.println(item.getItemText()+" 内容同步完成");
            }
        }
    }

    /**
     * 获取彩票开奖信息
     */
    @Test
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
     * 获取笑话
     * @param timestamp
     * @param currentPage
     * @param sort
     * @return
     */
    public int crawlAndSaveData(long timestamp,int currentPage,String sort){
        try {
            Map params=new HashMap();
            params.put("key","fb7f24e17aa1611f8c2de44450db6817");
            params.put("time",timestamp+"");
            params.put("page",currentPage);
            params.put("pagesize",20);
            params.put("sort",sort);
            JSONObject json = CrawlerUtil.getForJSONObject(URL_JOKE_A, params, null);
            if (json == null) {
                System.out.println("当前第" + currentPage + "页获取失败");
                return 0;
            }
            if(json.getIntValue("error_code")!=0){
                System.out.println(json.getString("reason"));
                return 0;
            }
            System.out.println("当前第" + currentPage + "页>>>" + json.toJSONString());

            //查询历史数据，用于排重
            List<TlKbJoke> oldJokes = tlKbJokeService.list();
            Map skeyMap = new HashMap();
            if (!CollectionUtils.isEmpty(oldJokes)) {
                for (int j = 0; j < oldJokes.size(); j++) {
                    TlKbJoke p = oldJokes.get(j);
                    skeyMap.put(p.getSourceKey(), j);
                }
            }
            JSONObject rslt=json.getJSONObject("result");
            if (rslt == null) {
                System.out.println("当前第" + currentPage + "没有结果");
                return 0;
            }
            //处理数据
            JSONArray jokes = rslt.getJSONArray("data");
            if(jokes==null||jokes.size()<1){
                System.out.println("当前第" + currentPage + "没有数据");
                return 0;
            }

            List<TlKbJoke> jokeList = new ArrayList<>();
            for (int i = 0; i < jokes.size(); i++) {
                JSONObject a = jokes.getJSONObject(i);
                if (!skeyMap.isEmpty() && skeyMap.containsKey(a.getString("hashId"))) {
//                    System.out.println("重复数据"+a.getString("idnew")+":"+oldPoetrys.size()+"-"+index+":"+oldPoetrys.get(index).getTitle()+":"+a.getString("nameStr"));
                    continue;
                }
                TlKbJoke joke = new TlKbJoke();
                joke.setSourceKey(a.getString("hashId"));
                joke.setContent(a.getString("content"));
                joke.setCreateTime(new Timestamp(System.currentTimeMillis()));
                joke.setType(1);
                jokeList.add(joke);
                skeyMap.put(joke.getSourceKey(), skeyMap.size());
            }
            //批量保存信息
            tlKbJokeService.saveBatch(jokeList);
            System.out.println("保存段子数量>>>"+jokeList.size());

//            JSONObject val=new JSONObject();
//            if(syncJokeLog!=null){
//                String valStr=syncJokeLog.getItemValue();
//                if(!StringUtils.isEmpty(valStr)){
//                    val=JSONObject.parseObject(valStr);
//                }
//            }else{
//                syncJokeLog=new SysDictItem();
//            }
//            val.put("lastSyncTime", timestamp);
//            syncJokeLog.setItemValue(val.toJSONString());
//            dictItemSer.saveOrUpdate(syncJokeLog);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 从极速获取笑话段子
     * @param historyPages
     * @param pageSize
     * @return
     */
    public int crawlAndSaveJokeDataFromJisu(int historyPages, int pageSize){
        try {
            int currentPage=1;
            for(int k=0;k<keyArr.length;k++){
                while(currentPage>0){
                    Random random=new Random();
                    int sleepTime=random.nextInt(10)*1000;
                    Thread.sleep(sleepTime);

                    Map params=new HashMap();
                    params.put("appkey",keyArr[k]);
                    params.put("pagenum",currentPage);
                    params.put("pagesize",pageSize);
                    params.put("sort","addtime");
                    JSONObject json = CrawlerUtil.getForJSONObject(URL_JOKE_B, params, null);
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
                    JSONArray jokes = rslt.getJSONArray("list");
                    if(jokes==null||jokes.size()<1){
                        System.out.println("当前第" + currentPage + "没有数据");
                        break;
                    }
                    int total=rslt.getIntValue("total");

                    //查询历史数据
                    List<TlKbJoke> oldJokes = tlKbJokeService.list();
                    Map skeyMap = new HashMap();
                    if (!CollectionUtils.isEmpty(oldJokes)) {
                        for (int j = 0; j < oldJokes.size(); j++) {
                            TlKbJoke p = oldJokes.get(j);
                            skeyMap.put(p.getSourceKey(), j);
                        }
                    }

                    List<TlKbJoke> jokeList = new ArrayList<>();
                    for (int i = 0; i < jokes.size(); i++) {
                        JSONObject a = jokes.getJSONObject(i);
                        if (!skeyMap.isEmpty() && skeyMap.containsKey(a.getString("url"))) {
//                    System.out.println("重复数据"+a.getString("idnew")+":"+oldPoetrys.size()+"-"+index+":"+oldPoetrys.get(index).getTitle()+":"+a.getString("nameStr"));
                            continue;
                        }
                        TlKbJoke joke = new TlKbJoke();
                        joke.setSourceKey(a.getString("url"));
                        joke.setContent(a.getString("content"));
                        joke.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        joke.setType(1);
                        jokeList.add(joke);
                        skeyMap.put(joke.getSourceKey(), skeyMap.size());
                    }
                    //批量保存信息
                    tlKbJokeService.saveBatch(jokeList);
                    System.out.println("保存段子数量>>>"+jokeList.size());
                    //已经同步页数加1
                    historyPages++;

                    JSONObject val=new JSONObject();
                    if(syncJokeLog!=null){
                        String valStr=syncJokeLog.getItemValue();
                        if(!StringUtils.isEmpty(valStr)){
                            val=JSONObject.parseObject(valStr);
                        }
                    }else{
                        syncJokeLog=new SysDictItem();
                    }
                    val.put("pageSize", pageSize);
                    val.put("page", historyPages);
                    syncJokeLog.setItemValue(val.toJSONString());
                    dictItemSer.saveOrUpdate(syncJokeLog);

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

    /**
     * 从极速获取书籍章节
     * @return
     */
    public int crawlAndSaveBookChaptersFromJisu(String url,String bookId){
        try {
            for(int k=0;k<keyArr.length;k++){
                Map params=new HashMap();
                params.put("appkey",keyArr[k]);
                JSONObject json = CrawlerUtil.getForJSONObject(url, params, null);
                if (json == null) {
                    System.out.println("获取失败");
                    continue;
                }
                if(json.getIntValue("status")!=0){
                    System.out.println(json.getString("msg"));
                    continue;
                }
                System.out.println(json.toJSONString());

                JSONArray rslt = json.getJSONArray("result");
                if(rslt==null||rslt.size()<1){
                    System.out.println("没有数据");
                    continue;
                }

                if("1225289873796198412".equalsIgnoreCase(bookId)){
                    TlKbBookChapter obj = new TlKbBookChapter();
                    obj.setBookId(bookId);
                    obj.setParentId("0");
                    obj.setTitle("章节");
                    obj.setLevel(1);
                    obj.setIsLeaf(false);
                    obj.setOrderNum("1");
                    tlKbBookChapterService.saveOrUpdate(obj);
                    obj=tlKbBookChapterService.findBookChapterByOrderNum(bookId,1,"1");

                    List<TlKbBookChapter> subList = new ArrayList<>();
                    for (int i = 0; i < rslt.size(); i++) {
                        JSONObject a = rslt.getJSONObject(i);
                        String name=a.getString("name");
                        String detailid=a.getString("detailid");
                        TlKbBookChapter chapter=new TlKbBookChapter();
                        chapter.setBookId(bookId);
                        chapter.setParentId(obj.getId());
                        chapter.setTitle(name);
                        chapter.setLevel(2);
                        chapter.setIsLeaf(true);
                        chapter.setOrderNum(detailid);
                        subList.add(chapter);
                    }
                    if(!CollectionUtils.isEmpty(subList)){
                        tlKbBookChapterService.saveOrUpdateBatch(subList);
                    }
                }else{
                    for (int i = 0; i < rslt.size(); i++) {
                        JSONObject a = rslt.getJSONObject(i);
                        String title=a.getString("chaptername");
                        String orderNum=a.getString("chapterid");

                        TlKbBookChapter obj = new TlKbBookChapter();
                        obj.setBookId(bookId);
                        obj.setParentId("0");
                        obj.setTitle(title);
                        obj.setLevel(1);
                        obj.setIsLeaf(false);
                        obj.setOrderNum(orderNum);
                        tlKbBookChapterService.saveOrUpdate(obj);
                        obj=tlKbBookChapterService.findBookChapterByOrderNum(bookId,1,orderNum);

                        JSONArray subs = a.getJSONArray("list");
                        if(subs!=null&&subs.size()>0){
                            List<TlKbBookChapter> subList = new ArrayList<>();
                            for(int j = 0; j < subs.size(); j++){
                                JSONObject b = subs.getJSONObject(j);
                                String name=b.getString("name");
                                String detailid=b.getString("detailid");
                                TlKbBookChapter chapter=new TlKbBookChapter();
                                chapter.setBookId(bookId);
                                chapter.setParentId(obj.getId());
                                chapter.setTitle(name);
                                chapter.setLevel(2);
                                chapter.setIsLeaf(true);
                                chapter.setOrderNum(detailid);
                                subList.add(chapter);
                            }
                            if(!CollectionUtils.isEmpty(subList)){
                                tlKbBookChapterService.saveOrUpdateBatch(subList);
                            }
                        }
                    }
                }
            }
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 从极速API同步数据章节内容
     * @param url
     * @param bookId
     * @return
     */
    public int crawlAndSaveBookContentsFromJisu(String url,String bookId){
        try {
            List<TlKbBookChapter> chapters=tlKbBookChapterService.listBookChapters(bookId);
            for(int k=0;k<keyArr.length;k++){
                if(!CollectionUtils.isEmpty(chapters)){
                    for(TlKbBookChapter ch:chapters){
                        if(!StringUtils.isEmpty(ch.getContentId())||!ch.getIsLeaf()){
                            continue;
                        }
                        Random random=new Random();
                        int sleepTime=random.nextInt(10)*1000;
                        Thread.sleep(sleepTime);
                        String detailId=ch.getOrderNum();
                        Map params=new HashMap();
                        params.put("appkey",keyArr[k]);
                        params.put("detailid",detailId);
                        params.put("isdetailed",1);
                        JSONObject json = CrawlerUtil.getForJSONObject(url, params, null);
                        if (json == null) {
                            System.out.println("获取失败");
                            return 0;
                        }
                        if(json.getIntValue("status")!=0){
                            System.out.println(json.getString("msg"));
                            break;
                        }

                        JSONObject rslt = json.getJSONObject("result");
                        if (json == null) {
                            System.out.println("没有数据");
                            continue;
                        }

                        System.out.println(json.toJSONString());
                        TlKbBookContent cont=tlKbBookContentService.selectBookContentByDetailId(bookId, detailId);
                        if(cont!=null){
                            ch.setContentId(cont.getId());
                            tlKbBookChapterService.saveOrUpdate(ch);
                            continue;
                        }

                        String appreciation=rslt.getString("appreciation");
                        String commentary=rslt.getString("commentary");
                        String content=rslt.getString("content");
                        String interpretation=rslt.getString("interpretation");
                        String title=rslt.getString("name");
                        String translation=rslt.getString("translation");
                        cont=new TlKbBookContent();
                        cont.setBookId(bookId);
                        cont.setDetailId(detailId);
                        cont.setAppreciation(appreciation);
                        cont.setCommentary(commentary);
                        cont.setContent(content);
                        cont.setInterpretation(interpretation);
                        cont.setTitle(title);
                        cont.setTranslation(translation);
                        tlKbBookContentService.saveOrUpdate(cont);
                        cont=tlKbBookContentService.selectBookContentByDetailId(bookId,detailId);
                        ch.setContentId(cont.getId());
                        tlKbBookChapterService.saveOrUpdate(ch);
                    }
                }
            }

            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 从极速API同步问答类数据
     * @param url
     * @param type
     * @return
     */
    public int crawlAndSaveQaFromJisu(String url,String type){
        try {
            for(int k=0;k<keyArr.length;k++){
                for(int i=0;i<1000;i++){
                    List<TlKbQa> qas=tlKbQaService.listQaByType(type);
                    Map oldMap=new HashMap();
                    if(!CollectionUtils.isEmpty(qas)){
                        for(TlKbQa qa:qas){
                            String key=qa.getContent()+"-"+qa.getQaType();
                            oldMap.put(key,1);
                        }
                    }

                    Random random=new Random();
                    int sleepTime=random.nextInt(10)*1000;
                    Thread.sleep(sleepTime);

                    Map params=new HashMap();
                    params.put("appkey",keyArr[k]);
                    params.put("pagenum",1);
                    params.put("pagesize",5);
                    JSONObject json = CrawlerUtil.getForJSONObject(url, params, null);
                    if (json == null) {
                        System.out.println("获取失败");
                        return 0;
                    }
                    if(json.getIntValue("status")!=0){
                        System.out.println(json.getString("msg"));
                        break;
                    }

                    JSONObject rslt = json.getJSONObject("result");
                    if (json == null) {
                        System.out.println("没有结果");
                        continue;
                    }

                    JSONArray list = rslt.getJSONArray("list");
                    if(list==null||list.size()<1){
                        System.out.println("没有数据");
                        return 0;
                    }
                    Integer classId=null;
                    if(rslt.containsKey("classid")){
                        classId=rslt.getIntValue("classid");
                    }
                    System.out.println(json.toJSONString());

                    List<TlKbQa> qaList=new ArrayList<>();
                    for(int j=0;j<list.size();j++){
                        JSONObject obj=list.getJSONObject(j);
                        String cont=obj.getString("content");
                        String answer=obj.getString("answer");
                        if("4".equalsIgnoreCase(type)){
                            answer=obj.getString("title");
                        }
                        String newKey=cont+"-"+type;
                        if(oldMap.containsKey(newKey)){
                            continue;
                        }
                        TlKbQa qa=new TlKbQa();
                        qa.setQaType(Integer.parseInt(type));
                        qa.setContent(cont);
                        qa.setAnswer(answer);
                        qa.setClassId(classId);
                        qaList.add(qa);
                    }
                    if(!CollectionUtils.isEmpty(qaList)){
                        tlKbQaService.saveOrUpdateBatch(qaList);
                        System.out.println(qaList.size()+"条问答数据保存成功");
                    }
                }
            }
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 从极速同步彩票信息
     * @return
     */
    @Test
    public void loadAndSaveLotteryFromJisu(){
        String url="https://api.jisuapi.com/caipiao/class";
        try {
            Map params=new HashMap();
            params.put("appkey",keyArr[0]);
            JSONObject json = CrawlerUtil.getForJSONObject(url, params, null);
            if (json == null) {
                System.out.println("获取失败");
                return;
            }
            if(json.getIntValue("status")!=0){
                System.out.println(json.getString("msg"));
                return;
            }

            JSONArray rslt = json.getJSONArray("result");
            if (json == null) {
                System.out.println("没有结果");
                return;
            }

            List<NshareLottery> list=new ArrayList<>();
            for(int j=0;j<rslt.size();j++){
                JSONObject obj=rslt.getJSONObject(j);
                String parentid=obj.getString("parentid");
                String caipiaoid=obj.getString("caipiaoid");
                String name=obj.getString("name");

                if("0".equalsIgnoreCase(parentid)){
                    continue;
                }
                NshareLottery entity=new NshareLottery();
                entity.setCatId(parentid);
                entity.setLotteryName(name);
                entity.setLotteryId(caipiaoid);
                list.add(entity);
            }
            if(!CollectionUtils.isEmpty(list)){
                nshareLotteryService.saveOrUpdateBatch(list);
                System.out.println(list.size()+"条彩票数据保存成功");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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


    /**
     * 获取年级分册
     * @return
     */
    @Test
    public void loadGradeVolume(){
        String url="https://www.zujuan.com/api/catalog/cate-tree";
        try {
            Map params=new HashMap();
            params.put("xd",1);
            params.put("chid",3);
            params.put("chapter_id",23313);
            Map headers=new HashMap();
            headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
            JSONObject json = CrawlerUtil.getForJSONObject(url, params, headers);
            if (json == null) {
                System.out.println("获取失败");
                return;
            }
            if(json.getIntValue("status")!=0){
                System.out.println(json.getString("msg"));
                return;
            }

            JSONArray rslt = json.getJSONArray("result");
            if (json == null) {
                System.out.println("没有结果");
                return;
            }

            List<NshareLottery> list=new ArrayList<>();
            for(int j=0;j<rslt.size();j++){
                JSONObject obj=rslt.getJSONObject(j);
                String parentid=obj.getString("parentid");
                String caipiaoid=obj.getString("caipiaoid");
                String name=obj.getString("name");

                if("0".equalsIgnoreCase(parentid)){
                    continue;
                }
                NshareLottery entity=new NshareLottery();
                entity.setCatId(parentid);
                entity.setLotteryName(name);
                entity.setLotteryId(caipiaoid);
                list.add(entity);
            }
            if(!CollectionUtils.isEmpty(list)){
                nshareLotteryService.saveOrUpdateBatch(list);
                System.out.println(list.size()+"条彩票数据保存成功");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]){
        System.out.println(System.currentTimeMillis()/1000);
    }
}
