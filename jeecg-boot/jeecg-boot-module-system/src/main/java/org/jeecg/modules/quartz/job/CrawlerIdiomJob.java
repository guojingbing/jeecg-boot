package org.jeecg.modules.quartz.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.http.HttpUtil;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.PinYinUtil;
import org.jeecg.modules.mp.tlearn.idiom.entity.TlKbIdiom;
import org.jeecg.modules.mp.tlearn.idiom.service.ITlKbIdiomService;
import org.jeecg.modules.mp.tlearn.util.CrawlerUtil;
import org.jeecg.modules.system.entity.SysDictItem;
import org.jeecg.modules.system.service.ISysDictItemService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2020-01-10 09:52:20
 **/
public class CrawlerIdiomJob implements Job {
    @Autowired
    ITlKbIdiomService tlKbIdiomService;
    @Autowired
    ISysDictItemService dictSer;
    //成语列表
    public final static String URL_IDIOM = "https://hanyu.baidu.com/hanyu/ajax/search_list?wd=%E6%88%90%E8%AF%AD&from=poem&userid=69040232&pn=";
    //文件存储跟路径
    @Value(value = "${jeecg.path.upload}")
    private String uploadpath;
    public final static String PATH_TONE_PY="\\audio\\idiom\\";

    public final static String DIT_MAINID_IDIOM="1218366870004985858";
    public List<Map> proxys;
    public SysDictItem synIdiomLog;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(String.format("成语获取定时任务，时间:" + DateUtils.getTimestamp()));
        CrawlerUtil.crawlProxyFromZhiMa(1);
        //获取代理服务器信息
        proxys = CrawlerUtil.readProxyFromTxt();
        //成语信息历史同步进度记录
        synIdiomLog=dictSer.getById(DIT_MAINID_IDIOM);

        int lastPage=Integer.parseInt(synIdiomLog.getItemValue());
        int currentPage = lastPage+1;
        int sumPage = currentPage+1;

        for (Map proxy : proxys) {
            while(currentPage<=sumPage){
                try {
                    Thread.sleep((long)Math.random()*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int[] page=crawlAndSaveIdiomData(currentPage,sumPage,null);
                currentPage=page[0];
                sumPage=page[1];
                if(page[2]==1){
                    break;
                }
            }
        }

        System.out.println("成语数据获取定时任务执行完成，时间:" + DateUtils.gettimestamp());
    }

    /**
     * 获取成语数据
     * @param currentPage
     * @param sumPage
     * @param proxy
     * @return
     */
    public int[] crawlAndSaveIdiomData(int currentPage,int sumPage,Map proxy){
        int[] page={currentPage,sumPage,0};
        if (currentPage > sumPage){
            return page;
        }
        try {
            JSONObject json = CrawlerUtil.getForJSONObject(URL_IDIOM+currentPage, null, null, proxy==null?null:(String)proxy.get("ip"), proxy==null?null:(Integer) proxy.get("port"));
            if (json == null) {
                System.out.println("共"+sumPage+"页，当前第"+currentPage+"页获取失败");
                return page;
            }
            System.out.println("共"+sumPage+"页，当前第"+currentPage+"页>>>"+json.toJSONString());

            if(!json.containsKey("extra")){
                return page;
            }

            JSONObject extra=json.getJSONObject("extra");
            sumPage = extra.getIntValue("total-page");

            List<String> skeys=new ArrayList<>();
            //处理数据
            JSONArray datas=json.getJSONArray("ret_array");

            List<TlKbIdiom> list=new ArrayList<>();
            for(int i=0;i<datas.size();i++){
                JSONObject a=datas.getJSONObject(i);
                JSONArray nameArr=a.getJSONArray("name");
                String title=nameArr.getString(0);
                //排重
                if(skeys.contains(title)){
                    continue;
                }

                TlKbIdiom entity=new TlKbIdiom();
                entity.setTitle(title);

                if(a.containsKey("pinyin")){
                    JSONArray pyArr=a.getJSONArray("pinyin");
                    String pinyin=pyArr.getString(0);
                    entity.setPinyin(pinyin);
                }

                if(a.containsKey("mean_list")){
                    JSONArray meanArr=a.getJSONArray("mean_list");
                    JSONObject meanJson=meanArr.getJSONObject(0);
                    JSONArray defArr=meanJson.getJSONArray("definition");
                    String def=defArr.getString(0);
                    entity.setDefinition(def);

                    JSONArray tonefArr=meanJson.getJSONArray("tone_py");
                    String tone=tonefArr.getString(0);
                    entity.setTonePy(tone);
                    entity.setToneMp3(PATH_TONE_PY.replace("\\","/")+PinYinUtil.getPinYinFullCode(entity.getTitle().trim(),false)+".mp3");
                }

                if(a.containsKey("antonym")){
                    JSONArray antonymArr=a.getJSONArray("antonym");
                    entity.setAntonym(antonymArr.toJSONString());
                }

                if(a.containsKey("synonym")){
                    JSONArray synonymArr=a.getJSONArray("synonym");
                    entity.setSynonym(synonymArr.toJSONString());
                }

                if(a.containsKey("liju")){
                    JSONArray lijuArr=a.getJSONArray("liju");
                    entity.setExample(lijuArr.toJSONString());
                }

                if(a.containsKey("source")){
                    JSONArray sourceArr=a.getJSONArray("source");
                    String source=sourceArr.getString(0);
                    entity.setSource(source);
                }

                entity.setCreateTime(DateUtils.getTimestamp());
                entity.setUpdateTime(entity.getCreateTime());
                list.add(entity);
            }
            //批量保存信息
            tlKbIdiomService.saveBatch(list);

            //下载音频文件
            for(TlKbIdiom idiom:list){
                String destPath=uploadpath+idiom.getToneMp3();
                if(!new File(destPath).exists()){
                    downloadFile(destPath, idiom.getTonePy());
                }
            }

            //更新同步日志
            synIdiomLog.setItemValue(currentPage+"");
            dictSer.updateById(synIdiomLog);

            currentPage = currentPage+1;
            page[0]=currentPage;
            page[1]=sumPage;
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("共"+sumPage+"页，当前第"+currentPage+"页获取失败");
            page[2]=1;
        }
        return page;
    }

    /**
     * 下载网络文件
     * @param destFilePath
     * @param url
     */
    public static void downloadFile(String destFilePath, String url){
        try {
            if(new File(destFilePath).exists()){
                return;
            }
            if(!new File(destFilePath).getParentFile().exists()){
                new File(destFilePath).getParentFile().mkdirs();
            }
            HttpUtil.downloadFileFromNet(url,destFilePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        CrawlerIdiomJob job=new CrawlerIdiomJob();
        job.crawlAndSaveIdiomData(1,100,null);
        System.out.println(URLDecoder.decode("%E4%B8%A4%E6%B1%89"));
    }
}
