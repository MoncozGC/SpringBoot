package com.moncozgc.mall.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 汉字辅助类
 * Created by FSL on 2021/1/20 10:15
 */
public class ChineseConversion {
    private static final Logger logger = LoggerFactory.getLogger(ChineseConversion.class);

    /**
     * 简要说明：将汉字转换为全拼
     *
     * @param hz   [需要转换的汉字]
     * @param type [首字母是否大写]
     * @return java.lang.String
     */
    public static String getCamelPinYin(String hz, boolean type) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String m;
        StringBuilder r = new StringBuilder();
        try {
            for (char value : hz.toCharArray()) {
                // 判断是否为汉字字符
                if (Character.toString(value).matches("[\\u4E00-\\u9FA5]+")) {
                    // 取出该汉字全拼的第一种读音并连接到字符串m后
                    m = PinyinHelper.toHanyuPinyinStringArray(value, format)[0];
                } else {
                    // 如果不是汉字字符，直接取出字符并连接到字符串m后
                    m = Character.toString(value);
                }

                if (type) {
                    r.append(m.substring(0, 1).toUpperCase()).append(m.substring(1));
                } else {
                    r.append(m);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error(e.getMessage(), e);
        }

        return r.toString();
    }

    /**
     * 简要说明：获取汉字首字母
     *
     * @param hz   [需要转换的汉字]
     * @param type [是否大写]
     * @return java.lang.String
     */
    public static String getPinYinHeadChar(String hz, boolean type) {
        StringBuilder r = new StringBuilder();
        String s = "";
        for (char v : hz.toCharArray()) {
            // 判断是否为汉字字符
            if (Character.toString(v).matches("[\\u4E00-\\u9FA5]+")) {
                s = PinyinHelper.toHanyuPinyinStringArray(v)[0];
            } else {
                // 如果不是汉字字符，直接取出字符并连接到字符串m后
                s = Character.toString(v);
            }

            r.append(s != null ? s.substring(0, 1) : null);
        }

        return type ? r.toString().toUpperCase() : r.toString();
    }

    public static void main(String[] args) {
        String str = "美国";
        String getCamelPinYin = getCamelPinYin(str, true);
        String getPinYinHeadChar = getPinYinHeadChar(str, false);
        System.out.println(getCamelPinYin);
        System.out.println(getPinYinHeadChar);
    }
}
