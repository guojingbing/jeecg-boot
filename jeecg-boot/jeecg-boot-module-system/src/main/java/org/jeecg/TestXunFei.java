//package org.jeecg;
//
//import com.iflytek.cloud.speech.*;
//import org.jeecg.common.util.AudioFileUtil;
//
///**
// * @Description: TestXunFei
// * @Author: Kingpin
// * @Date: 2020-02-08 21:10:11
// **/
//public class TestXunFei implements Runnable{
//    static String[] args;
//    public TestXunFei(String[] args){
//        this.args=args;
//    }
//
//    @Override
//    public void run() {
//        convert(this.args);
//    }
//
//    public interface Callback {
//        //执行完成回调通知
//        void complete(String id, String errCode);
//    }
//    public static Callback callback;
//
//    // 合成监听器
//    private final static SynthesizeToUriListener mSynListener = new SynthesizeToUriListener() {
//        //progress为合成进度0~100
//        public void onBufferProgress(int progress) {}
//        //会话合成完成回调接口
//        //uri为合成保存地址，error为错误信息，为null时表示合成会话成功
//        public void onSynthesizeCompleted(String uri, SpeechError error) {
//            System.out.println(uri);
//            try {
//                AudioFileUtil.convertAudioFiles(uri,uri.replace(".pcm",".wav"));
//                if (callback != null) {//通知调用者
//                    callback.complete(args[0],null);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        @Override
//        public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4, Object arg5) {
//            System.out.println("onEvent");
//        }
//    };
//    public void convert(String[] args) {
//        try {
//            SpeechUtility.createUtility(SpeechConstant.APPID + "=5e250ac9 ");
//            // 1.创建SpeechSynthesizer对象
//            SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
//            // 2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
//            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");// 设置发音人
//            mTts.setParameter(SpeechConstant.SPEED, "20");//设置语速，范围0~100
//            mTts.setParameter(SpeechConstant.PITCH, "50");//设置语调，范围0~100
//            mTts.setParameter(SpeechConstant.VOLUME, "50");//设置音量，范围0~100
//            // 3.开始合成
//            mTts.synthesizeToUri(args[1], args[2], mSynListener);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static void main(String[] args) {
//        try {
//            SpeechUtility.createUtility(SpeechConstant.APPID + "=5e250ac9 ");
//            // 1.创建SpeechSynthesizer对象
//            SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
//            // 2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
//            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");// 设置发音人
//            mTts.setParameter(SpeechConstant.SPEED, "20");//设置语速，范围0~100
//            mTts.setParameter(SpeechConstant.PITCH, "50");//设置语调，范围0~100
//            mTts.setParameter(SpeechConstant.VOLUME, "50");//设置音量，范围0~100
//            // 3.开始合成
//            mTts.synthesizeToUri("绝句\n" +
//                    "杜甫·唐代\n" +
//                    "迟日江山丽，春风花草香。\n" +
//                    "泥融飞燕子，沙暖睡鸳鸯。", "G:\\tmp\\111.pcm", mSynListener);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
