package org.jeecg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.common.http.HttpUtil;
import org.jeecg.modules.mp.stock.service.IStockInfoService;
import org.jeecg.modules.mp.stock.service.IStockKlineService;
import org.jeecg.modules.mp.stock.service.IStockProfitService;
import org.jeecg.modules.mp.tlearn.kb.entity.*;
import org.jeecg.modules.mp.tlearn.kb.service.*;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetry;
import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2020-02-09 16:18:00
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UnitTest {
    @Resource
    ITlKbPoetryService tlKbPoetryService;
    @Resource
    ITlKbService tlKbService;
    @Resource
    IStockInfoService stockSer;
    @Resource
    IStockProfitService spSer;
    @Resource
    IStockKlineService klineSer;
    @Test
    public void initContentOrig(){
        List<TlKbPoetry>  poes=tlKbPoetryService.list();
        for(int i=0;i<poes.size();i++){
            TlKbPoetry p=poes.get(i);
            if(p.getContentOrig()!=null){
                continue;
            }
            System.out.println(poes.size()+":"+i);
            String contentOrig = p.getContent().replaceAll("<span.*?</span>", "")
                    .replaceAll("\\(.*?\\)", "")
                    .replaceAll("（.*?）", "");
//                    .replaceAll("<br.*?>", "");
            p.setContentOrig(contentOrig);
            tlKbPoetryService.saveOrUpdate(p);
        }
    }

    @Test
    public void pythonTest(){
//        stockSer.upStockInfoFromBaoStock();
//        spSer.syncStockProfitFromBaoStock(null,2020,2);
        klineSer.syncStockValuationFromBaoStock(null,"2020-01-01","2020-08-10","d","2");
    }

    @Test
    public void addToKb(){
        IPage<Map> page = tlKbPoetryService.loadList4API(null, null, 100000, 1,null,"早教",null);
        List<Map> list=page.getRecords();
        Collection colls=new ArrayList();
        Map idCache=new HashMap();

        if(!CollectionUtils.isEmpty(list)){
            for(Map poe:list){
                if(!idCache.containsKey(poe.get("id").toString())) {
                    TlKb kb = new TlKb();
                    kb.setKbId(poe.get("id").toString());
                    kb.setKbType(1);
                    kb.setTitle(poe.get("title").toString());
                    kb.setContent(poe.get("content_orig").toString());
                    colls.add(kb);
                    idCache.put(kb.getKbId(), kb.getKbId());
                }
            }
        }
        page = tlKbPoetryService.loadList4API(null, null, 100000, 1,null,"小学",null);
        list=page.getRecords();
        if(!CollectionUtils.isEmpty(list)){
            for(Map poe:list){
                if(!idCache.containsKey(poe.get("id").toString())){
                    TlKb kb=new TlKb();
                    kb.setKbId(poe.get("id").toString());
                    kb.setKbType(1);
                    kb.setTitle(poe.get("title").toString());
                    kb.setContent(poe.get("content_orig").toString());
                    colls.add(kb);
                    idCache.put(kb.getKbId(),kb.getKbId());
                }
            }
        }

        tlKbService.saveBatch(colls);
        System.out.println(list.size());
//        List<TlKbPoetry> poetries = tlKbPoetryService.list(new LambdaQueryWrapper<TlKbPoetry>().like(TlKbPoetry::getTagStr, ""));

    }

    @Resource
    ITlKbCourseService tlKbCourseService;
    @Resource
    ITlKbCourseVersionService kbVerSer;
    @Resource
    ITlKbCourseVolumeService kbVolSer;
    @Resource
    ITlKbCourseQuestionBankService kbQueSer;
    @Test
    public void loadTlKbGradeVolume(){
        List<TlKbCourseVersion> versions=kbVerSer.list();
        if(CollectionUtils.isEmpty(versions)){
            return;
        }
        for(TlKbCourseVersion v:versions){
            Collection colls=new ArrayList();
            String url="https://www.zujuan.com/api/catalog/cate-tree?xd=1&chid=3&chapter_id="+v.getFkCode()+"&forpaper=";
            Map<String, Object> headers=new HashMap();
            headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
            try {
                JSONObject resp= HttpUtil.getForJSONObject(url,null,headers,null,null);
                if(resp==null||resp.getIntValue("code")!=0){
                    continue;
                }
                JSONArray objs=resp.getJSONArray("data");
                for(int i=0;i<objs.size();i++){
                    JSONObject obj=objs.getJSONObject(i);
                    if(obj==null){
                        continue;
                    }
                    TlKbCourseVolume vol=new TlKbCourseVolume();
                    vol.setTitle(obj.getString("title"));
                    vol.setVersionId(v.getId());
                    vol.setGradeId(1);
                    vol.setFkCode(obj.getString("id"));
                    colls.add(vol);
                }
                if(!CollectionUtils.isEmpty(colls)){
                    kbVolSer.saveBatch(colls);
                }
                System.out.println(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Resource
    ITlKbCourseChapterService kbChapterSer;
    @Resource
    ITlKbCourseKnowledgeService kbKnowledgeSer;
    @Test
    public void loadTlKbChapter(){
        List<TlKbCourseVolume> volumes=kbVolSer.list();
        if(CollectionUtils.isEmpty(volumes)){
            return;
        }
        for(TlKbCourseVolume vol:volumes){
            String url="https://www.zujuan.com/api/catalog/all-cate-tree?chapter_id="+vol.getFkCode();
            Map<String, Object> headers=new HashMap();
            headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
            try {
                JSONObject resp= HttpUtil.getForJSONObject(url,null,headers,null,null);
                if(resp==null||resp.getIntValue("code")!=0){
                    continue;
                }
                JSONArray objs=resp.getJSONArray("data");
                recursiveChapterTree(vol.getId(),0,0,"",objs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void recursiveChapterTree(int volId, int parentId, int level, String nodePath, JSONArray arr){
        if(arr==null){
            return;
        }
        for(int i=0;i<arr.size();i++){
            JSONObject obj=arr.getJSONObject(i);
            String fkCode=obj.getString("id");
            String title=obj.getString("title");
            int lev=level+1;
            String np=nodePath+"/"+fkCode;

            TlKbCourseChapter chapter=new TlKbCourseChapter();
            chapter.setVolumeId(volId);
            chapter.setTitle(title);
            chapter.setFkCode(fkCode);
            chapter.setParentId(parentId);
            chapter.setLevel(lev);
            chapter.setNodePath(np);
            if(!obj.getBooleanValue("hasChild")){
                chapter.setIsLeaf(1);
            }else{
                chapter.setIsLeaf(0);
            }
            kbChapterSer.save(chapter);
            TlKbCourseChapter c = kbChapterSer.getOne(new LambdaQueryWrapper<TlKbCourseChapter>().eq(TlKbCourseChapter::getFkCode, chapter.getFkCode()));
            recursiveChapterTree(volId,c.getId(),lev,np,obj.getJSONArray("son"));
        }
    }

    @Test
    public void loadTlKbKnowledge(){
        int schoolStage=1;
        for(int i=2;i<4;i++){
            String url="https://www.zujuan.com/api/catalog/all-know-tree?xd="+schoolStage+"&chid="+i+"&_="+System.currentTimeMillis();
            Map<String, Object> headers=new HashMap();
            headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
            try {
                JSONObject resp= HttpUtil.getForJSONObject(url,null,headers,null,null);
                if(resp==null||resp.getIntValue("code")!=0){
                    continue;
                }
                JSONArray objs=resp.getJSONArray("data");
                recursiveKnowledgeTree(schoolStage,i-1,0,0,"",objs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void recursiveKnowledgeTree(int schoolStage, int courseId, int parentId, int level, String nodePath, JSONArray arr){
        if(arr==null){
            return;
        }
        for(int i=0;i<arr.size();i++){
            JSONObject obj=arr.getJSONObject(i);
            String fkCode=obj.getString("id");
            String title=obj.getString("title");
            int lev=level+1;
            String np=nodePath+"/"+fkCode;

            TlKbCourseKnowledge entity=new TlKbCourseKnowledge();
            entity.setCourseId(courseId);
            entity.setTitle(title);
            entity.setFkCode(fkCode);
            entity.setParentId(parentId);
            entity.setLevel(lev);
            entity.setNodePath(np);
            entity.setSchoolStage(schoolStage);
            if(!obj.getBooleanValue("hasChild")){
                entity.setIsLeaf(1);
            }else{
                entity.setIsLeaf(0);
            }
            kbKnowledgeSer.save(entity);
            TlKbCourseKnowledge e = kbKnowledgeSer.getOne(new LambdaQueryWrapper<TlKbCourseKnowledge>().eq(TlKbCourseKnowledge::getFkCode, entity.getFkCode()));
            recursiveKnowledgeTree(schoolStage,courseId,e.getId(),lev,np,obj.getJSONArray("son"));
        }
    }

    @Test
    public void syncCourseQuestion(){
        String cfk="2";
        List<TlKbCourseVolume> volumes=kbVolSer.listCourseVolume(cfk);
        if(CollectionUtils.isEmpty(volumes)){
            return;
        }
        Map<String, Object> headers=new HashMap();
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
        for(int k=0;k<volumes.size();k++){
            TlKbCourseVolume vol=volumes.get(k);
            List<TlKbCourseChapter> chapters = kbChapterSer.list(new LambdaQueryWrapper<TlKbCourseChapter>().eq(TlKbCourseChapter::getVolumeId, vol.getId()));
            Map chapterMap=new HashMap();
            if(!CollectionUtils.isEmpty(chapters)){
                for(TlKbCourseChapter c:chapters){
                    chapterMap.put(c.getFkCode(),c.getId());
                }
            }
            String url="https://www.zujuan.com/api/question/list?xd=1&chid="+cfk+"&categories="+vol.getFkCode()+"&question_channel_type=1&sort_field=time&filterquestion=0&page=1&_="+System.currentTimeMillis();
            try {
                JSONObject resp= HttpUtil.getForJSONObject(url,null,headers,null,null);
                if(resp==null||resp.getIntValue("code")!=0){
                    continue;
                }
                JSONObject data=resp.getJSONObject("data");
                int total=data.getIntValue("total");
                int pageNum=total/10;
                if(total%10>0){
                    pageNum++;
                }
                for(int j=1;j<=pageNum;j++){
                    Collection colls=new ArrayList();
                    url="https://www.zujuan.com/api/question/list?xd=1&chid="+cfk+"&categories="+vol.getFkCode()+"&question_channel_type=1&sort_field=time&filterquestion=0&page="+j+"&_="+System.currentTimeMillis();
                    resp= HttpUtil.getForJSONObject(url,null,headers,null,null);
                    if(resp==null||resp.getIntValue("code")!=0){
                        continue;
                    }
                    data=resp.getJSONObject("data");
                    JSONArray arr=data.getJSONArray("questions");
                    if(arr==null||arr.size()==0){
                        continue;
                    }
                    List<TlKbCourseQuestionBank> qbs=kbQueSer.list();
                    Map qbMap=new HashMap();
                    if(!CollectionUtils.isEmpty(qbs)){
                        for(TlKbCourseQuestionBank qb:qbs){
                            qbMap.put(qb.getFkCode(),qb.getId());
                        }
                    }
                    for(int i=0;i<arr.size();i++){
                        JSONObject obj=arr.getJSONObject(i);
                        String fkCode=obj.getString("question_id");
                        if(qbMap.size()>0&&qbMap.containsKey(fkCode)){
                            continue;
                        }
                        String title=obj.getString("title");
                        String options=obj.getString("options");
                        String pagerSource=obj.getString("paper_source");
                        int type=obj.getIntValue("question_channel_type");
                        int examType=obj.getIntValue("exam_type");
                        int difficultLevel=obj.getIntValue("difficult_index");
                        JSONArray catArr=obj.getJSONArray("category_path");
                        catArr.get(0);

                        JSONObject ch=obj.getJSONObject("category");
                        Integer cid=null;
                        if(ch!=null){
                            Set<String> keys = ch.keySet();
                            String chapterKey=null;
                            if(!CollectionUtils.isEmpty(keys)){
                                for(String key:keys){
                                    if(chapterKey==null){
                                        chapterKey=key;
                                    }
                                }
                            }
                            if(chapterKey!=null&&chapterMap.containsKey(chapterKey)){
                                cid=Integer.parseInt(chapterMap.get(chapterKey).toString());
                            }
                        }
                        TlKbCourseQuestionBank qb=new TlKbCourseQuestionBank();
                        qb.setTitle(title);
                        qb.setContent(options);
                        qb.setType(type);
                        qb.setFkCode(fkCode);
                        qb.setExamType(examType);
                        qb.setDiffLevel(difficultLevel);
                        qb.setPaperSource(pagerSource);
                        colls.add(qb);
                    }
                    if(!CollectionUtils.isEmpty(colls)){
                        kbQueSer.saveBatch(colls);
                    }
                    System.out.println("分册数量>>>"+volumes.size()+"当前同步>>>"+k+"题库分页>>>"+pageNum+"当前页数>>>"+j+"题数>>>"+colls.size());
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
