package org.jeecg.modules.mp.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:小程序的初始配置信息
 * @Author: Kingpin
 * @Date: 2021-05-31 13:54:24
 **/
public class MpConfig {
    //小程序配置信息
    public static final Map<String, Map<String, String>> appAuth = new HashMap<>();
    static {
        Map<String, String> config = new HashMap();
        config.put("MP-SECRET", "a07fe08b99b99242a822ba83e65d0db4");
//        config.put("TRTC-SDKAPPID","1400474322");
//        config.put("TRTC-SECRET","739991025da854e1c67ca2bf1809a564c324926b22dfc1cdac2d7726622a5ea2");
        //近邻
        appAuth.put("wx5879c81e4cfc8ef5", config);
        config = new HashMap();
        config.put("MP-SECRET", "756a7872834755e493adb2a481a045c6");
//        config.put("TRTC-SDKAPPID","1400474322");
//        config.put("TRTC-SECRET","739991025da854e1c67ca2bf1809a564c324926b22dfc1cdac2d7726622a5ea2");
        //多多
        appAuth.put("wx035e90552bbb21fb", config);
        //多多锦医未
        config = new HashMap();
        config.put("MP-SECRET", "f1369440e6c36daf2d1758dd731192d0");
        config.put("TRTC-SDKAPPID", "1400474322");
        config.put("TRTC-SECRET", "739991025da854e1c67ca2bf1809a564c324926b22dfc1cdac2d7726622a5ea2");
        appAuth.put("wx004c4e41ee5ca5f8", config);
    }
}
