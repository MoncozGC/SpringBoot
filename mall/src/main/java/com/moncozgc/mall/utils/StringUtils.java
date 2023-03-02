package com.moncozgc.mall.utils;


import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class StringUtils {

    private static final String INTEGER_REGEX = "0|-?([1-9]{1}[0-9]*)";
    private static final String DOUBLE_REGEX = "-?[0-9]+\\.[0-9]+?";

    private final static String EMAIL_REGEXP =
            "^[\\.a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    private final static String LETTER_REGEXP = "^[a-zA-Z]+$";

    public static boolean isCompleteLetter(String str) {
        return !isEmpty(str) && Pattern.matches(LETTER_REGEXP, str);
    }

    //字符串不为空
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    //判断一组字符串都不为空
    public static boolean isEmpty(String... arr) {
        for (String str : arr) {
            if (isEmpty(str)) return true;
        }
        return false;
    }

    public static int getStrLength(String str) {
        return str == null ? -1 : str.length();
    }

    public static String trim(String text) {
        if (text == null || "".equals(text)) {
            return text;
        }
        return text.trim();
    }

    /**
     * 判定字符串是否为int范围。
     *
     * @param str
     * @return
     */
    public static boolean isInt(String str) {
        try {
            Integer.valueOf(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判定字符串是否为整数。
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        return !isEmpty(str) && Pattern.matches(INTEGER_REGEX, str);
    }

    /**
     * 判断字符串是否为浮点数
     *
     * @param str
     * @return
     */
    public static boolean isFloat(String str) {
        return !isEmpty(str) && Pattern.matches(DOUBLE_REGEX, str);
    }

    public static boolean isBiggerZero(String str) {
        return isInteger(str) && Long.parseLong(str) > 0;
    }

    public static boolean isDateFormatter(String str) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean isDateTimeFormatter(String str) {
        try {
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean isEmail(String str) {
        return !isEmpty(str) && Pattern.matches(EMAIL_REGEXP, str);
    }

//    public static boolean isJsonFormatter(String str) {
//        try {
//            JSON.parse(str);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }


    /* 文字转拼音大写字母 */
//    public static String converterToFirstSpell(String chines) {
//        return Py4jUtils.converterToFirstSpell(chines);
//    }

    /**
     * 获取指定字符在字符串中的个数
     */
    public static int targetStrCount(String str, String tag) {
        int index = 0;
        int count = 0;
        while ((index = str.indexOf(tag)) != -1) {
            str = str.substring(index + tag.length());
            count++;
        }
        return count;
    }


    public static boolean checkStrLength(String str, int maxLen) {
        return checkStrLength(str, maxLen, true);
    }

    public static boolean checkStrLength(String str, int maxLen, boolean nullAble) {
        if (maxLen < 0) {
            return false;
        }

        if (!nullAble) {
            if (str == null) {
                return false;
            }
        }

        return str == null || (str.trim().length() <= maxLen);
    }

    /**
     * 根据访问环境不同获取 脚本/资源 路径
     *
     * @param type       执行环境
     * @param pathSuffix 最后的目录名称
     * @return 整体的路径
     */
    public static String PathJudgment(String type, String pathSuffix) {
        String path = "";
        if ("LOCAL".equals(type)) {
            return path = System.getProperty("user.dir") + "/mall/src/main/resources/" + pathSuffix + "/";
        } else if ("SERVER".equals(type)) {
            return path = System.getProperty("user.dir") + "/" + pathSuffix + "/";
        } else {
            return path;
        }
    }

}
