package org.jeecg;

import org.jeecg.modules.mp.tlearn.poetry.entity.TlKbPoetry;
import org.jeecg.modules.mp.tlearn.poetry.service.ITlKbPoetryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

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
}
