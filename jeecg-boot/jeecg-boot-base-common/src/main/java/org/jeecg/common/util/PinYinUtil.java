package org.jeecg.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang.StringUtils;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2020-01-10 16:56:19
 **/
public class PinYinUtil {
    /**
     * 默认获取大写
     * @param str
     * @return
     */
    public static String getPinYinFullCode(String str){
        try {
            return getPinYinFullCode(str, true);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return null;
    }
    public static String getPinYinFirstCode(String str){
        try {
            return getPinYinFirstCode(str, true);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return null;
    }
    /**
     * 获取中文拼音
     * @param str
     * @param isUper
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String getPinYinFullCode(String str, Boolean isUper) throws BadHanyuPinyinOutputFormatCombination {
        if(StringUtils.isEmpty(str)){
            return str;
        }
        StringBuilder pinyin = new StringBuilder();
        char[] charArray = str.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        //设置大小写格式
        if(isUper!=null&&isUper){
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        }else{
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        //设置声调格式：
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < charArray.length; i++) {
            //匹配中文,非中文转换会转换成null
            if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i],defaultFormat);
                String string =hanyuPinyinStringArray[0];
                pinyin.append(string);
            } else {
                pinyin.append(charArray[i]);
            }
        }
        return pinyin.toString();
    }

    /**
     * 获取中文拼音首字母
     * @param str
     * @param isUper
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String getPinYinFirstCode(String str, Boolean isUper) throws BadHanyuPinyinOutputFormatCombination {
        if(StringUtils.isEmpty(str)){
            return str;
        }
        char[] charArray = str.toCharArray();
        StringBuilder pinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 设置大小写格式
        if(isUper!=null&&isUper){
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        }else{
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        // 设置声调格式：
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < charArray.length; i++) {
            //匹配中文,非中文转换会转换成null
            if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat);
                if (hanyuPinyinStringArray != null) {
                    pinyin.append(hanyuPinyinStringArray[0].charAt(0));
                }
            }
        }
        return pinyin.toString();
    }
    public static void main(String args[]){
        System.out.println(getPinYinFullCode("山东"));
        System.out.println(getPinYinFirstCode("山东"));
    }
}
