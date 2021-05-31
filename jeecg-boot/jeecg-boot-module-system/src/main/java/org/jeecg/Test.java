//package org.jeecg;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryService;
//import org.jeecg.modules.mp.tlearn.util.WebTTSWS;
//import org.jeecg.modules.system.entity.SysDictItem;
//import org.jeecg.modules.system.service.ISysDictItemService;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * @Description:
// * @Author: Kingpin
// * @Date: 2020-01-20 11:08:44
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class Test {
//    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);
//    @Resource
//    ITlKbPoetryService tlKbPoetryService;
//    @Resource
//    ISysDictItemService dictItemSer;
//    //文件存储跟路径
//    @Value(value = "${jeecg.path.upload}")
//    private String uploadpath;
//    public final static String PATH_TONE_POE="\\audio\\poetry\\";
//
//    @org.junit.Test
//    public void poetryRepeatClean() {
//        tlKbPoetryService.delPoetrysRepeat();
//    }
//
//    @org.junit.Test
//    public void initPoetrysAudio(){
//        IPage<Map> poetrys = tlKbPoetryService.loadList4API(null, null, 100, 1,null, "早教",null);
//        if(poetrys!=null) {
//            List<SysDictItem> dyns = dictItemSer.selectItemsByMainId("1212644360118607874");
//            List<String> ids = new ArrayList<>();
//            for (SysDictItem id : dyns) {
//                ids.add(id.getItemValue());
//            }
//            String baseFilePath = uploadpath + PATH_TONE_POE;
//            File f=new File(baseFilePath);
//            if(!f.exists()){
//                f.mkdirs();
//            }
//            List<Map> list=poetrys.getRecords();
//            for(int i=0;i<1;i++) {
//                Map p = list.get(i);
//                String formId = p.get("form_id").toString();
//                String id=p.get("id").toString();
//                if (!"1".equalsIgnoreCase(formId) && !"2".equalsIgnoreCase(formId) && !"3".equalsIgnoreCase(formId)) {
//                    continue;
//                }
//                if(p.get("audio_path")!=null&&!"".equals(p.get("audio_path").toString())){
//                    continue;
//                }
//                StringBuffer sb = new StringBuffer();
//                sb.append(p.get("title"));
//                sb.append("\n");
//                int index = ids.indexOf(p.get("dyn_id"));
//                sb.append(p.get("author"));
//                sb.append("·");
//                sb.append(dyns.get(index).getItemText());
//                sb.append("\n");
//                String content = p.get("content").toString().replaceAll("<span.*?</span>", "").replaceAll("\\(.*?\\)", "").replaceAll("（.*?）", "").replaceAll("<.*?>", "");
//                sb.append(content);
//                System.out.println(sb.toString());
//                String[] args=new String[3];
//                args[0]=id;
////                args[1]=sb.toString();
//                args[1]="迟日江山丽，春风花草香。";
//                args[2]=uploadpath+PATH_TONE_POE+id+".pcm";
//
//                WebTTSWS ttsws=new WebTTSWS();
//                try {
//                    ttsws.convert(args);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                TestXunFei.Callback callback=new TestXunFei.Callback() {
//                    @Override
//                    public void complete(String id, String errCode) {
//                        System.out.println("complete");
//                    }
//                };
//                TestXunFei t = new TestXunFei(args);
//                t.callback = callback;
//                threadPool.execute(t);
//            }
//        }
//    }
//
//}