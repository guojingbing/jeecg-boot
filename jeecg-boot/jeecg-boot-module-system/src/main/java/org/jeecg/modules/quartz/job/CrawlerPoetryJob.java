package org.jeecg.modules.quartz.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.http.HttpUtil;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.PinYinUtil;
import org.jeecg.modules.mp.tlearn.author.entity.TlKbAuthor;
import org.jeecg.modules.mp.tlearn.author.service.ITlKbAuthorService;
import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetry;
import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryService;
import org.jeecg.modules.mp.tlearn.util.CrawlerUtil;
import org.jeecg.modules.system.entity.SysDictItem;
import org.jeecg.modules.system.service.ISysDictItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2020-01-10 09:52:20
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrawlerPoetryJob implements Job {
    @Autowired
    ITlKbAuthorService tlKbAuthorService;
    @Autowired
    ITlKbPoetryService tlKbPoetryService;
    @Autowired
    ISysDictItemService dictSer;
    //列表通用参数，token：gswapi；page:
    //作者信息
    public final static String URL_AUTHORS_POETRY = "https://app.gushiwen.cn/api/author/Default10.aspx";
    public final static String URL_AUTHOR_PIC_BASE = "https://song.gushiwen.org/authorImg/";
    public final static String PATH_AUTHOR_PIC = "\\images\\author\\";
    //诗文列表
    public final static String URL_POETRYS = "https://app.gushiwen.cn/api/shiwen/Default11.aspx";
    //诗文信息附带译注
    //idnew:5D03E334F2F92C8C702583CB8BB40180
    //value:yizhushang
    //token:gswapi
    public final static String URL_POETRY_WITH_TRANS = "https://app.gushiwen.cn/api/shiwen/ajaxshiwencont11.aspx";
    //获取诗文详情
    public final static String URL_POETRY_INFO = "https://app.gushiwen.cn/api/shiwen/shiwenv11.aspx";

    public final static String URL_POETRY_RANK_BAIDU = "http://www.baidu.com/s";

    public final static String URL_POETRY_RANK_BING = "https://api.cognitive.microsoft.com/bing/v7.0/search";
    //文件存储跟路径
    @Value(value = "${jeecg.path.upload}")
    private String uploadpath;

    public final static String DIT_MAINID_DYN = "1212644360118607874";
    public final static String DIT_MAINID_FORM = "1213299435442868225";
    public final static String DIT_MAINID_POETRY_TAG = "1218549620674772993";
    public final static String DIT_ITEMID_SYNLOG_AUTHOR_DYN = "1215864932050935810";
    public final static String DIT_ITEMID_SYNLOG_AUTHOR_ALL = "1219216552037998593";
    public final static String DIT_ITEMID_SYNLOG_POETRY = "1215865541684633601";
    public final static String DIT_ITEMID_SYNLOG_AUTHOR_POETRY = "1216713494640988161";
    public List<Map> proxys;
    public Map<String, String> authorsMap;
    public Map<String, String> tagsMap;
    public Map<String, String> dynsMap;
    public Map<String, String> formsMap;
    public SysDictItem synAuthorDynLog;
    public SysDictItem synAuthorAllLog;
    public SysDictItem synPoetryLog;
    public SysDictItem synAuthorPoetryLog;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(String.format("数据获取定时任务，时间:" + DateUtils.getTimestamp()));
//        CrawlerUtil.crawlProxyFromZhiMa(1);
        //获取代理服务器信息
//        proxys = CrawlerUtil.readProxyFromTxt();
        //获取诗词形式信息
        List<SysDictItem> forms = dictSer.selectItemsByMainId(DIT_MAINID_FORM);
        if (!CollectionUtils.isEmpty(forms)) {
            formsMap = new HashMap();
            for (SysDictItem f : forms) {
                formsMap.put(f.getItemText(), f.getItemValue());
            }
        }
//        //获取朝代信息
//        List<SysDictItem> dyns=dictSer.selectItemsByMainId(DIT_MAINID_DYN);
//        if(!CollectionUtils.isEmpty(dyns)){
//            dynsMap=new HashMap();
//            for(SysDictItem dyn:dyns){
//                dynsMap.put(dyn.getItemText(),dyn.getItemValue());
//            }
//        }

//        //获取诗词标签
//        List<SysDictItem> tags=dictSer.selectItemsByMainId(DIT_MAINID_POETRY_TAG);
//        if(!CollectionUtils.isEmpty(tags)){
//            tagsMap=new HashMap();
//            for(SysDictItem tag:tags){
//                tagsMap.put(tag.getItemText(),tag.getItemValue());
//            }
//        }

//        //按朝代同步作者
//        synDynAuthors(dyns);
//        //按形式同步诗词
//        synFormPoetrys(forms);
        //作者同步诗文
        synAuthorPoetrys();
        System.out.println("数据获取定时任务执行完成，时间:" + DateUtils.gettimestamp());
    }

    /**
     * 按同步作者信息
     */
    @Test
    public void synAuthors() {
        //获取朝代信息
        List<SysDictItem> dyns = dictSer.selectItemsByMainId(DIT_MAINID_DYN);
        if (!CollectionUtils.isEmpty(dyns)) {
            dynsMap = new HashMap();
            for (SysDictItem dyn : dyns) {
                dynsMap.put(dyn.getItemText(), dyn.getItemValue());
            }
        }
        //作者信息历史同步进度记录
        synAuthorAllLog = dictSer.getById(DIT_ITEMID_SYNLOG_AUTHOR_ALL);
        int lastPage = 0;
        if (synAuthorAllLog != null) {
            String logStr = synAuthorAllLog.getItemValue();
            if (!StringUtils.isEmpty(logStr)) {
                lastPage = Integer.parseInt(logStr);
            }
        }

        int currentPage = lastPage + 1;
        int sumPage = currentPage + 1;

        while (currentPage <= 50) {
            int[] page = crawlAndSaveAuthorData(null, currentPage, sumPage, null);
            currentPage = page[0];
            sumPage = page[1];
            if (page[2] == 1) {
                break;
            }
        }
    }

    /**
     * 按朝代同步作者信息
     */
    public void synDynAuthors(List<SysDictItem> dyns) {
        //作者信息历史同步进度记录
        synAuthorDynLog = dictSer.getById(DIT_ITEMID_SYNLOG_AUTHOR_DYN);
        for (SysDictItem dyn : dyns) {
            String formValue = dyn.getItemValue();
            String formText = dyn.getItemText();
            int lastPage = 0;
            if (synAuthorDynLog != null) {
                String logStr = synAuthorDynLog.getItemValue();
                if (!StringUtils.isEmpty(logStr)) {
                    JSONArray logArr = JSONObject.parseArray(logStr);
                    for (int i = 0; i < logArr.size(); i++) {
                        JSONObject log = logArr.getJSONObject(i);
                        if (log.get("c").toString().equalsIgnoreCase(formValue)) {
                            lastPage = log.getIntValue("p");
                            break;
                        }
                    }
                }
            }

            //接口限制最多100页
            if (lastPage >= 100) {
                continue;
            }
            int currentPage = lastPage + 1;
            int sumPage = currentPage + 1;
            System.out.println(dyn.getItemText() + ">>>作者获取");

            Map dynMap = new HashMap();
            dynMap.put("itemValue", formValue);
            dynMap.put("itemText", formText);
            for (Map proxy : proxys) {
                if (currentPage >= sumPage) {
                    System.out.println(dyn.getItemText() + ">>>作者信息获取完成");
                    break;
                }
                while (currentPage <= sumPage) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    int[] page = crawlAndSaveAuthorData(dynMap, currentPage, sumPage, proxy);
                    currentPage = page[0];
                    sumPage = page[1];
                    if (page[2] == 1) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * 按形式同步诗词信息
     */
    @Test
    public void synFormPoetrys() {
        //获取诗词形式信息
        List<SysDictItem> forms = dictSer.selectItemsByMainId(DIT_MAINID_FORM);
        if (!CollectionUtils.isEmpty(forms)) {
            formsMap = new HashMap();
            for (SysDictItem f : forms) {
                formsMap.put(f.getItemText(), f.getItemValue());
            }
        }
        //以下为通过接口获取诗词信息
        //获取作者信息
        List<TlKbAuthor> authors = tlKbAuthorService.list();
        if (!CollectionUtils.isEmpty(authors)) {
            authorsMap = new HashMap();
            for (TlKbAuthor author : authors) {
                authorsMap.put(author.getAuthorName(), author.getId());
            }
        }
        //诗词信息历史同步进度记录
        synPoetryLog = dictSer.getById(DIT_ITEMID_SYNLOG_POETRY);
        for (SysDictItem f : forms) {
            try {
                String formValue = f.getItemValue();
                String formText = f.getItemText();
                int lastPage = 0;
                if (synPoetryLog != null) {
                    String logStr = synPoetryLog.getItemValue();
                    if (!StringUtils.isEmpty(logStr)) {
                        JSONArray logArr = JSONObject.parseArray(logStr);
                        for (int i = 0; i < logArr.size(); i++) {
                            JSONObject log = logArr.getJSONObject(i);
                            if (log.get("c").toString().equalsIgnoreCase(formValue)) {
                                lastPage = log.getIntValue("p");
                                break;
                            }
                        }
                    }
                }

                //接口限制最多100页
                if (lastPage >= 100) {
                    continue;
                }
                System.out.println(formText + ">>>诗词获取");
                int currentPage = lastPage + 1;
                int sumPage = currentPage + 1;
//                for (Map proxy : proxys) {
                if (currentPage >= sumPage) {
                    System.out.println(formText + ">>>诗词信息获取完成");
                    break;
                }
                while (currentPage <= sumPage) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Map params = new HashMap();
                    String key = null;
                    try {
                        key = URLEncoder.encode(formText, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    params.put("page", currentPage);
                    params.put("token", "gswapi");
                    params.put("xstr", key);
//                        proxy.put("ip",null);
//                        proxy.put("port",null);
                    int[] page = crawlAndSavePoetryData(params, currentPage, sumPage, null, formValue, 0);
                    currentPage = page[0];
                    sumPage = page[1];
                    if (page[2] == 1) {
                        break;
                    }
                }
//                }
            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }
        }
    }

    /**
     * 按作者同步诗文
     */
    @Test
    public void synAuthorPoetrys() {
        //获取诗词形式信息
        List<SysDictItem> forms = dictSer.selectItemsByMainId(DIT_MAINID_FORM);
        if (!CollectionUtils.isEmpty(forms)) {
            formsMap = new HashMap();
            for (SysDictItem f : forms) {
                formsMap.put(f.getItemText(), f.getItemValue());
            }
        }
        //获取作者信息
        List<TlKbAuthor> authors = tlKbAuthorService.listRecAuthors();
        if (!CollectionUtils.isEmpty(authors)) {
            authorsMap = new HashMap();
            for (int i = 0; i < authors.size(); i++) {
                TlKbAuthor author = authors.get(i);
                authorsMap.put(author.getAuthorName(), author.getId());
            }
        }

        synAuthorPoetryLog = dictSer.getById(DIT_ITEMID_SYNLOG_AUTHOR_POETRY);
        int lastAuthorIndex = 0;
        int lastSynPage = 0;
        if (!StringUtils.isEmpty(synAuthorPoetryLog.getItemValue())) {
            JSONArray arr = JSONArray.parseArray(synAuthorPoetryLog.getItemValue());
            lastAuthorIndex = Integer.parseInt(arr.get(0).toString());
            lastSynPage = Integer.parseInt(arr.get(1).toString());
        }
        for (int i = 0; i < authors.size(); i++) {
            try {
                if (i < lastAuthorIndex) {
                    continue;
                }
                TlKbAuthor author = authors.get(i);
                //获取代理服务器信息
                proxys = CrawlerUtil.readProxyFromTxt();
                System.out.println(author.getAuthorName() + ">>>诗词获取");
                int lastPage = 0;
                if (i == lastAuthorIndex) {
                    lastPage = lastSynPage;
                }
                int currentPage = lastPage + 1;
                int sumPage = currentPage + 1;
//            for (Map proxy : proxys) {
                if (currentPage >= sumPage) {
                    System.out.println(author.getAuthorName() + ">>>诗词信息获取完成");
                    break;
                }
                while (currentPage <= sumPage) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    proxy.put("ip",proxy.get("ip"));
//                    proxy.put("port",proxy.get("port"));

                    Map params = new HashMap();
                    String key = null;
                    try {
                        key = URLEncoder.encode(author.getAuthorName(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    params.put("page", currentPage);
                    params.put("token", "gswapi");
                    params.put("astr", key);

                    try {
                        Thread.sleep((long) Math.random() * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int[] page = new int[0];
                    page = crawlAndSavePoetryData(params, currentPage, sumPage, null, null, i);
                    currentPage = page[0];
                    sumPage = page[1];
                    if (page[2] == 1) {
                        break;
                    }
                }
//            }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * 指定作者同步诗文
     */
    @Test
    public void synTheAuthorPoetrys() {
        String authorName="夏完淳";
        String authorId="1216224970837868549";
        authorsMap = new HashMap();
        authorsMap.put(authorName, authorId);
        //获取诗词形式信息
        List<SysDictItem> forms = dictSer.selectItemsByMainId(DIT_MAINID_FORM);
        if (!CollectionUtils.isEmpty(forms)) {
            formsMap = new HashMap();
            for (SysDictItem f : forms) {
                formsMap.put(f.getItemText(), f.getItemValue());
            }
        }
        int lastPage = 0;
        int currentPage = lastPage + 1;
        int sumPage = currentPage + 1;
        if (currentPage >= sumPage) {
            return;
        }
        while (currentPage <= sumPage) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map params = new HashMap();
            String key = null;
            try {
                key = URLEncoder.encode(authorName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("page", currentPage);
            params.put("token", "gswapi");
            params.put("astr", key);

            try {
                Thread.sleep((long) Math.random() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int[] page = new int[0];
            try {
                page = crawlAndSavePoetryData(params, currentPage, sumPage, null, null, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            currentPage = page[0];
            sumPage = page[1];
            if (page[2] == 1) {
                break;
            }
        }
    }
    /**
     * 获取作者数据
     * 递归页面获取并处理数据
     * 若代理有效递归下面的页面，无效则退出
     *
     * @param currentPage
     * @param sumPage
     * @param proxy
     * @return
     */
    public int[] crawlAndSaveAuthorData(Map dynMap, int currentPage, int sumPage, Map proxy) {
        Map params = new HashMap();
        int[] page = {currentPage, sumPage, 0};
        if (currentPage > sumPage) {
            return page;
        }
        try {
            params.put("page", currentPage);
            params.put("token", "gswapi");
            if (dynMap != null) {
                String key = URLEncoder.encode(dynMap.get("itemText").toString(), "UTF-8");
                params.put("c", key);
            }
            JSONObject json = CrawlerUtil.getForJSONObject(URL_AUTHORS_POETRY, params, null, proxy == null ? null : (String) proxy.get("ip"), proxy == null ? null : (Integer) proxy.get("port"));
            if (json == null) {
                System.out.println("共" + sumPage + "页，当前第" + currentPage + "页获取失败");
                return page;
            }
            System.out.println("共" + sumPage + "页，当前第" + currentPage + "页>>>" + json.toJSONString());
            //查询历史数据，用于排重
            List<TlKbAuthor> oldAuthors = tlKbAuthorService.list();
            Map skeyMap = new HashMap();
            if (!CollectionUtils.isEmpty(oldAuthors)) {
                for (int i = 0; i < oldAuthors.size(); i++) {
                    TlKbAuthor p = oldAuthors.get(i);
//                    skeys.add(p.getSourceKey());
                    skeyMap.put(p.getAuthorName(), i);
                }
            }

            //处理数据
            JSONArray authors = json.getJSONArray("authors");
            List<TlKbAuthor> authorList = new ArrayList<>();
            List<TlKbAuthor> authorUpList = new ArrayList<>();
            for (int i = 0; i < authors.size(); i++) {
                JSONObject a = authors.getJSONObject(i);
                //排重
                if (skeyMap.containsKey(a.getString("nameStr"))) {
                    int index = Integer.parseInt(skeyMap.get(a.getString("nameStr")).toString());
                    TlKbAuthor oa = oldAuthors.get(index);
                    oa.setRecIndex(1);
                    authorUpList.add(oa);
                    continue;
                }
                TlKbAuthor author = new TlKbAuthor();
                author.setAuthorName(a.getString("nameStr"));
                author.setSummary(a.getString("cont"));
                if (dynMap != null) {
                    author.setDynId(dynMap.get("itemValue").toString());
                }
                author.setAvatar(PATH_AUTHOR_PIC.replace("\\", "/") + PinYinUtil.getPinYinFullCode(author.getAuthorName(), false) + ".jpg");
                author.setSourceKey(a.getString("idnew"));
//                    author.setCreateBy();
                author.setCreateTime(DateUtils.getTimestamp());
                authorList.add(author);
            }
            //批量保存作者信息
            tlKbAuthorService.saveBatch(authorList);
            //更新已存在数据
            if (dynMap == null && !CollectionUtils.isEmpty(authorUpList)) {
                tlKbAuthorService.saveOrUpdateBatch(authorUpList);
                System.out.println(authorUpList.size() + "条作者信息更新成功");
            }

            if (dynMap == null && synAuthorAllLog != null) {
                synAuthorAllLog.setItemValue(currentPage + "");
                dictSer.updateById(synAuthorAllLog);
            } else {
                //记录同步进度
                JSONArray logArr = new JSONArray();
                JSONObject log = new JSONObject();
                log.put("c", dynMap.get("itemValue").toString());
                log.put("p", (currentPage - 1));
                int index = 0;
                if (synAuthorDynLog != null) {
                    String logStr = synAuthorDynLog.getItemValue();
                    if (!StringUtils.isEmpty(logStr)) {
                        logArr = JSONObject.parseArray(logStr);
                        index = logArr.size();
                        for (int i = 0; i < logArr.size(); i++) {
                            JSONObject l = logArr.getJSONObject(i);
                            if (l.get("c").toString().equalsIgnoreCase(dynMap.get("itemValue").toString())) {
                                index = i;
                                break;
                            }
                        }
                    }
                }
                logArr.set(index, log);
                synAuthorDynLog.setItemValue(logArr.toJSONString());
                dictSer.updateById(synAuthorDynLog);
            }

            sumPage = json.getIntValue("sumPage");
            currentPage = json.getIntValue("currentPage") + 1;

            System.out.println(authorList.size() + ">>>条作者数据保存成功");
            //下载头像文件
            for (TlKbAuthor author : authorList) {
                String destPath = uploadpath + author.getAvatar();
                loadAvatar(destPath, PinYinUtil.getPinYinFullCode(author.getAuthorName(), false));
            }
            page[0] = currentPage;
            page[1] = sumPage;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("共" + sumPage + "页，当前第" + currentPage + "页获取失败");
            page[2] = 1;
        }
        return page;
    }

    /**
     * 按形式获取诗词
     *
     * @param params
     * @param currentPage
     * @param sumPage
     * @param proxy
     * @return
     */
    public int[] crawlAndSavePoetryData(Map params, int currentPage, int sumPage, Map proxy, String formId, int authorIndex) throws Exception {
        int[] page = {currentPage, sumPage, 0};
        if (currentPage > sumPage) {
            return page;
        }
        try {
            JSONObject json = CrawlerUtil.getForJSONObject(URL_POETRYS, params, null, proxy == null ? null : (String) proxy.get("ip"), proxy == null ? null : (Integer) proxy.get("port"));
            if (json == null) {
                System.out.println("共" + sumPage + "页，当前第" + currentPage + "页获取失败");
                return page;
            }
            System.out.println("共" + sumPage + "页，当前第" + currentPage + "页>>>" + json.toJSONString());
            sumPage = json.getIntValue("sumPage");
            currentPage = json.getIntValue("currentPage") + 1;
            //查询历史数据，用于排重
            List<TlKbPoetry> oldPoetrys = tlKbPoetryService.list();
            Map skeyMap = new HashMap();
            if (!CollectionUtils.isEmpty(oldPoetrys)) {
                for (int j = 0; j < oldPoetrys.size(); j++) {
                    TlKbPoetry p = oldPoetrys.get(j);
                    skeyMap.put(p.getSourceKey(), j);
                }
            }
            //处理数据
            JSONArray poetrys = json.getJSONArray("gushiwens");
            List<TlKbPoetry> poetryList = new ArrayList<>();
            List<TlKbPoetry> poetryUpList = new ArrayList<>();
            for (int i = 0; i < poetrys.size(); i++) {
                TlKbPoetry poetry = new TlKbPoetry();
                JSONObject a = poetrys.getJSONObject(i);
                if (!skeyMap.isEmpty() && skeyMap.containsKey(a.getString("idnew"))) {
                    int index = Integer.parseInt(skeyMap.get(a.getString("idnew")).toString());
                    if (!StringUtils.isEmpty(formId)) {
                        TlKbPoetry up = oldPoetrys.get(index);
                        up.setFormId(formId);
                        poetryUpList.add(up);
                    }
//                    System.out.println("重复数据"+a.getString("idnew")+":"+oldPoetrys.size()+"-"+index+":"+oldPoetrys.get(index).getTitle()+":"+a.getString("nameStr"));
                    continue;
                }
                poetry.setTitle(a.getString("nameStr"));
                poetry.setTagStr(a.getString("tag"));
                poetry.setSourceKey(a.getString("idnew"));
                poetry.setFormId(formId);
                poetry.setContent(a.getString("cont"));

                oldPoetrys.add(poetry);
                skeyMap.put(poetry.getSourceKey(), oldPoetrys.size() - 1);

                if (authorsMap != null) {
                    String authorId = authorsMap.get(a.getString("author"));
                    poetry.setAuthorId(authorId);
                }

                //通过译注接口获取译注信息
                Map params1 = new HashMap();
                params1.put("token", "gswapi");
                params1.put("idnew", a.getString("idnew"));
                params1.put("value", "yizhu");
                try {
                    try {
                        Thread.sleep((long) Math.random() * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    JSONObject jsonObj = CrawlerUtil.getForJSONObject(URL_POETRY_WITH_TRANS, params1, null, proxy == null ? null : (String) proxy.get("ip"), proxy == null ? null : (Integer) proxy.get("port"));
                    if (jsonObj != null) {
                        if (!StringUtils.isEmpty(jsonObj.getString("cont"))) {
                            poetry.setContent(jsonObj.getString("cont"));
                            poetry.setReference(jsonObj.getString("cankao"));
                        }
                    }
                } catch (Exception ex) {
                    for (Map pro : proxys) {
                        //不使用代理
                        pro = null;
                        try {
                            try {
                                Thread.sleep((long) Math.random() * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            JSONObject jsonObj = CrawlerUtil.getForJSONObject(URL_POETRY_WITH_TRANS, params1, null, pro == null ? null : (String) pro.get("ip"), pro == null ? null : (Integer) pro.get("port"));
                            if (jsonObj != null) {
                                if (!StringUtils.isEmpty(jsonObj.getString("cont"))) {
                                    poetry.setContent(jsonObj.getString("cont"));
                                    poetry.setReference(jsonObj.getString("cankao"));
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("诗词译注获取失败,代理服务器：" + pro == null ? "" : pro.toString());
                            e.printStackTrace();
                            continue;
                        }
                    }
                    ex.printStackTrace();
                    throw ex;
                }

                if(StringUtils.isEmpty(formId)){
                    //通过详情接口获取诗文信息主要是形式信息
                    Map params2 = new HashMap();
                    params2.put("token", "gswapi");
                    params2.put("id", a.getString("idnew"));
                    try {
                        try {
                            Thread.sleep((long)Math.random()*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        JSONObject jsonObj = CrawlerUtil.getForJSONObject(URL_POETRY_INFO, params2, null, proxy==null?null:(String)proxy.get("ip"), proxy==null?null:(Integer) proxy.get("port"));
                        if (jsonObj != null) {
                            JSONObject obj=jsonObj.getJSONObject("tb_gushiwen");
                            if(obj!=null&&obj.containsKey("type")&&!StringUtils.isEmpty(obj.getString("type"))){
                                poetry.setFormId(formsMap.get(obj.getString("type")));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        if(proxys!=null&&proxys.size()>0){
                            for (Map pro : proxys) {
                                pro=null;
                                try {
                                    try {
                                        Thread.sleep((long)Math.random()*1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    JSONObject jsonObj = CrawlerUtil.getForJSONObject(URL_POETRY_INFO, params2, null, pro==null?null:(String)pro.get("ip"), pro==null?null:(Integer) pro.get("port"));
                                    if (jsonObj != null) {
                                        JSONObject obj=jsonObj.getJSONObject("tb_gushiwen");
                                        if(obj!=null&&obj.containsKey("type")&&!StringUtils.isEmpty(obj.getString("type"))){
                                            poetry.setFormId(formsMap.get(obj.getString("type")));
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("诗词详情获取失败,代理服务器："+pro==null?"":pro.toString());
                                    continue;
                                }
                            }
                        }
                    }
                }
//                    author.setCreateBy();
                poetry.setCreateTime(DateUtils.getTimestamp());
                poetryList.add(poetry);
            }
            //批量保存信息
            tlKbPoetryService.saveBatch(poetryList);
            if (!CollectionUtils.isEmpty(poetryUpList)) {
                tlKbPoetryService.saveOrUpdateBatch(poetryUpList);
                System.out.println(poetryUpList.size() + "条诗词数据更新成功");
            }

            //记录诗词形式同步进度
            if (!StringUtils.isEmpty(formId)) {
                JSONArray logArr = new JSONArray();
                JSONObject log = new JSONObject();
                log.put("c", formId);
                log.put("p", (currentPage - 1));
                int index = 0;
                if (synPoetryLog != null) {
                    String logStr = synPoetryLog.getItemValue();
                    if (!StringUtils.isEmpty(logStr)) {
                        logArr = JSONObject.parseArray(logStr);
                        index = logArr.size();
                        for (int i = 0; i < logArr.size(); i++) {
                            JSONObject l = logArr.getJSONObject(i);
                            if (l.get("c").toString().equalsIgnoreCase(formId)) {
                                index = i;
                                break;
                            }
                        }
                    }
                }
                logArr.set(index, log);
//                synPoetryLog.setItemValue(logArr.toJSONString());
//                dictSer.updateById(synPoetryLog);
            } else {
//                synAuthorPoetryLog.setItemValue("[" + authorIndex + "," + (currentPage - 1) + "]");
//                dictSer.updateById(synAuthorPoetryLog);
            }
//            System.out.println(proxy.toString());
            System.out.println(poetryList.size() + ">>>条诗文数据保存成功");
            page[0] = currentPage;
            page[1] = sumPage;
        } catch (Exception ex) {
//            System.out.println("诗词列表获取失败,代理服务器："+proxy==null?"":proxy.toString());
            ex.printStackTrace();
            System.out.println("共" + sumPage + "页，当前第" + currentPage + "页获取失败");
            page[2] = 1;
        }
        return page;
    }

    /**
     * 查询诗词百度搜索结果数
     */
    @Test
    public void rankPoetryBaidu() {
        List<TlKbPoetry> poetrys = tlKbPoetryService.listPoetryNoRank("BAIDU");
//        List<TlKbPoetry> poetrys = tlKbPoetryService.list();
        for (int i = 0; i < poetrys.size(); i++) {
            try {
                TlKbPoetry poetry = poetrys.get(i);
//                if (poetry.getRank() == null) {
                    Map params = new HashMap();
                    TlKbAuthor author = tlKbAuthorService.getById(poetry.getAuthorId());
                    String key;
                    if(author==null){
                        key=poetry.getTitle();
                    }else{
                        key = author.getAuthorName()+" "+poetry.getTitle();
                    }
//                    key = URLEncoder.encode(key, "UTF-8");
                    params.put("wd", key);
                    params.put("pn", 50);
                    params.put("rn", 50);
                    params.put("tn", "json");
                    JSONObject jsonObj = CrawlerUtil.getForJSONObject(URL_POETRY_RANK_BAIDU, params, null, null, null);
                    if (jsonObj != null) {
                        if (!StringUtils.isEmpty(jsonObj.getString("feed"))) {
                            JSONObject feedJson = jsonObj.getJSONObject("feed");
                            if (feedJson != null && feedJson.containsKey("all")) {
                                poetry.setRank(feedJson.getIntValue("all"));
                            }
                            tlKbPoetryService.saveOrUpdate(poetry);
                            System.out.println(poetrys.size()+":"+i+">>>>>>"+key);
                        }
                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }

    /**
     * 查询诗词bing搜索结果数
     */
    @Test
    public void rankPoetryBing() {
        List<TlKbPoetry> poetrys = tlKbPoetryService.listPoetryNoRank("BING");
        for (int i = 0; i < poetrys.size(); i++) {
            try {
                TlKbPoetry poetry = poetrys.get(i);
//                if (poetry.getRank() == null) {
                Map params = new HashMap();

                TlKbAuthor author = tlKbAuthorService.getById(poetry.getAuthorId());
                String key;
                if(author==null){
                    key=poetry.getTitle();
                }else{
                    key = author.getAuthorName()+" "+poetry.getTitle();
                }
//                    key = URLEncoder.encode(key, "UTF-8");
                params.put("q", key);
                params.put("safesearch", "Strict");
                params.put("mkt", "zh-cn");
                Map headers = new HashMap();
                headers.put("Ocp-Apim-Subscription-Key", "75bda3f4653f417aad3f7559df4c825d");
                JSONObject jsonObj = CrawlerUtil.getForJSONObject(URL_POETRY_RANK_BING, params, headers, null, null);
                if (jsonObj != null) {
                    if (!StringUtils.isEmpty(jsonObj.getString("webPages"))) {
                        JSONObject feedJson = jsonObj.getJSONObject("webPages");
                        if (feedJson != null && feedJson.containsKey("totalEstimatedMatches")) {
                            poetry.setRankBing(feedJson.getIntValue("totalEstimatedMatches"));
                        }
                        tlKbPoetryService.saveOrUpdate(poetry);
                        System.out.println(poetrys.size()+":"+i+">>>>>>"+key);
                    }
                }
//                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }

    /**
     * 下载头像文件
     *
     * @param destFilePath
     * @param fileNameWithoutExt
     */
    public static void loadAvatar(String destFilePath, String fileNameWithoutExt) {
        String url = URL_AUTHOR_PIC_BASE + fileNameWithoutExt + ".jpg";
        try {
            if (new File(destFilePath).exists()) {
                return;
            }
            if (!new File(destFilePath).getParentFile().exists()) {
                new File(destFilePath).getParentFile().mkdirs();
            }
            HttpUtil.downloadFileFromNet(url, destFilePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
//        System.out.println(String.format("数据爬取定时任务，开始时间:" + DateUtils.getTimestamp()));
//        //获取代理服务器信息
//        List<Map> proxys = CrawlerUtil.readProxyFromTxt();
//        //获取朝代信息
//        List<SysDictItem> dyns=new ArrayList<>();
//        for(int i=0;i<2;i++){
//            SysDictItem item=new SysDictItem();
//            item.setItemText("先秦");
//            item.setItemValue("1");
//            dyns.add(item);
//        }
//        CrawlerJob job=new CrawlerJob();
//        for(SysDictItem dyn:dyns){
//            System.out.println(dyn.getItemText()+">>>作者获取");
//            int sumPage = 2;
//            int currentPage = 1;
//            Map dynMap=new HashMap();
//            dynMap.put("itemValue",dyn.getItemValue());
//            dynMap.put("itemText",dyn.getItemText());
//            for (Map proxy : proxys) {
//                if(currentPage>=sumPage){
//                    System.out.println(dyn.getItemText()+">>>作者信息获取完成");
//                    break;
//                }
//                while(currentPage<=sumPage){
//                    int[] page=job.crawlAndSaveAuthorData(dynMap,currentPage,sumPage,proxy);
//                    currentPage=page[0];
//                    sumPage=page[1];
//                    if(page[2]==1){
//                        break;
//                    }
//                }
//            }
//            System.out.println("数据爬取定时任务执行完成，完成时间:" + DateUtils.gettimestamp());
//        }
        System.out.println(URLDecoder.decode("%E4%B8%A4%E6%B1%89"));
    }
}
