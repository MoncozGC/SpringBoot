package com.moncozgc.mall.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtils {

    /**
     * 一周间隔时间
     */
    public static final long PERIOD_WEEK = 7 * 24 * 60 * 60 * 1000L;

    /**
     * 一天得间隔时间
     */
    public static final long PERIOD_DAY = 24 * 60 * 60 * 1000L;

    /**
     * 一小时间隔时间
     */
    public static final long PERIOD_HOUR = 60 * 60 * 1000L;

    /**
     * 一个月
     */
    public static final long PERIOD_MONTH = 30 * 24 * 60 * 60 * 1000L;

    /**
     * 添加x天
     */
    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    /**
     * 获取当天时间: 2022-12-05 16:27:09
     */
    public static String inDay() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 减少x天
     */
    public static Date subtractDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, -num);
        return startDT.getTime();
    }

    /**
     * string -> date ,  参数:"11:00:00"  如果小于当前时间,向后加一天
     */
    public static Date str_Hms_2Date(String timeString) {
        try {
            String[] strArr = timeString.split(":");

            Calendar calendar = Calendar.getInstance();
            if (strArr.length >= 1) {
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strArr[0]));
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, 0);
            }
            if (strArr.length >= 2) {
                calendar.set(Calendar.MINUTE, Integer.parseInt(strArr[1]));
            } else {
                calendar.set(Calendar.MINUTE, 0);
            }
            if (strArr.length >= 3) {
                calendar.set(Calendar.SECOND, Integer.parseInt(strArr[2]));
            } else {
                calendar.set(Calendar.SECOND, 0);
            }
            Date date = calendar.getTime();
            if (date.before(new Date())) {
                date = addDay(date, 1);
            }
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 例: 2017-11-11 9:50:00
     */
    public static Date str_yMd_Hms_2Date(String timeString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 例: 2017-11-11
     */
    public static Date str_yMd_2Date(String timeString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 例: 2017-11-11 9:50:00
     */
    public static String date_yMd_Hms_2String(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 例: 2017-11-11
     */
    public static String date_yMd_2String(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param fromat 指定格式格式化
     * @Author wuchao
     * @Description:
     * @Params: * @param date 日期
     * @Date 2019/12/20
     **/
    public static String dateFormat(String date, String oldFormat, String newFormat) {

        SimpleDateFormat oldSimpleDateFormat = new SimpleDateFormat(oldFormat);
        SimpleDateFormat newSimpleDateFormat = new SimpleDateFormat(newFormat);
        String newDate = "";
        try {
            newDate = newSimpleDateFormat.format(oldSimpleDateFormat.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * 例: 1111 标识xx月xx日
     */
    public static String date_Md_2String(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 例: 1111 标识xx月xx日
     */
    public static int getCurrentDate_Md() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
        try {
            return Integer.parseInt(simpleDateFormat.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 例: 2017-11-11 9:50:00
     */
    public static String date_Hms_2String(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 毫秒数-> x天x小时x分x秒
     *
     * @author lzp
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days + "天");
        }
        if (hours > 0) {
            sb.append(hours + "小时");
        }
        if (minutes > 0) {
            sb.append(minutes + "分钟");
        }
        if (seconds > 0) {
            sb.append(seconds + "秒");
        }
        return sb.toString();
    }

    /**
     * 获取当前年份
     */
    public static int getCurrentYear() {
        try {
            return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 1900;
    }

    public static int[] getCurrAndLastYear() {
        int curr = getCurrentYear();

        return new int[]{curr - 1, curr};
    }

    public static int[] getDBableCurrAndLastYear() {
        int curr = getCurrentYear();

        if (curr < 2019) {
            return new int[0];
        }

        if (curr == 2019) {
            return new int[]{curr};
        }

        return new int[]{curr - 1, curr};
    }

    /**
     * 获取指定年份
     */
    public static int getCurrentYear(Date date) {
        try {
            return Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 1900;
    }

    /**
     * 获取当前日期
     */
    public static String getCurrentDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "1900-01-01";
    }

    public static String getDate() {
        try {
            return new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "1900年01月01日";
    }

    public static String getDate(String date) {
        try {
            return new SimpleDateFormat("yyyy年mm月dd日").format(date);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "1900年01月01日";
    }

    /**
     * 获取当前时间
     */
    public static String getCurrentTime() {
        try {
            return new SimpleDateFormat("HH:mm:ss").format(new Date());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "00:00:00";
    }


    /**
     * 格式为"HH:mm:ss"
     * 判断当前时间是否在[startDate, endDate]区间，注意时间格式要一致
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isEffectiveTime(String startTime, String endTime) {
        try {
            String format = "HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String nowTime = sdf.format(new Date());
            Date nowDate = sdf.parse(nowTime);
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            return isEffectiveDate(nowDate, startDate, endDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断当前时间是否在[startDate, endDate]区间，注意时间格式要一致
     *
     * @param nowDate   当前时间
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date nowDate, Date startDate, Date endDate) {
        if (nowDate.getTime() == startDate.getTime()
                || nowDate.getTime() == endDate.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowDate);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 判断时间1是否在时间2之后
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return
     */
    public static boolean after(String date1, String date2) {

        Date d1 = str_yMd_Hms_2Date(date1);
        Date d2 = str_yMd_Hms_2Date(date2);

        return d1.after(d2);
    }

    public static boolean afterDay(String date1, String date2) {

        Date d1 = str_yMd_2Date(date1);
        Date d2 = str_yMd_2Date(date2);

        return d1.after(d2);
    }

    public static int getYearByOrderno(String orderno) {
        if (StringUtils.isEmpty(orderno)) {
            return getCurrentYear();
        }

        try {
            return Integer.parseInt("20" + orderno.substring(0, 2));
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * @Author wuchao
     * @Description:字符串转日期
     * @Params: * @param str
     * @Date 2019/10/10
     **/
    public static Date convertStringToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @Author wuchao
     * @Description:获取当前日期
     * @Params: * @param
     * @Date 2019/11/18
     **/
    public static String getNewDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 0);//如果把0修改为-1就代表昨天
        date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(date);
        return dateString;
    }

    /**
     * @Author wuchao
     * @Description:获取当前日期
     * @Params: * @param
     * @Date 2019/11/18
     **/
    public static String date2_yy_MM_dd_HH_mm_ss() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        String dateString = format.format(new Date());
        return dateString;
    }

    /**
     * @Author wuchao
     * @Description:获取当前日期
     * @Params: * @param
     * @Date 2019/11/21
     **/
    public static String getNewToDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 0);//如果把0修改为-1就代表昨天
        date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);
        return dateString;
    }

    /**
     * @Author wuchao
     * @Description:获取当前日期
     * @Params: * @param
     * @Date 2019/11/21
     **/
    public static String getSpecNumberDateToString(int dayNumber) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, dayNumber);//如果把0修改为-1就代表昨天
        date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);
        return dateString;
    }


    /**
     * 获取两个时间相差多少天
     *
     * @param sDate
     * @param eDate
     * @return
     */
    public static long getDateDiscrepancyDayNumber(String sDate, String eDate) {
        Date sdate = str_yMd_2Date(sDate);//开始日期
        Date edate = str_yMd_2Date(eDate);//结束日期

        Calendar scalendar = Calendar.getInstance();
        scalendar.setTime(sdate);
        long sDay = scalendar.getTimeInMillis();


        Calendar ecalendar = Calendar.getInstance();
        ecalendar.setTime(edate);
        long eDay = ecalendar.getTimeInMillis();

        long disTimes = eDay - sDay;
        if (disTimes <= 0) {
            return 0;
        }
        return (disTimes / 1000 / 60 / 60 / 24);
    }

    /**
     * 获取两个时间相差多少天
     *
     * @param sDate
     * @param eDate
     * @return
     */
    public static int getMonthDiff(String sDate, String eDate) {
        Date sdate = str_yMd_2Date(sDate);
        Date edate = str_yMd_2Date(eDate);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdate);
        c2.setTime(edate);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
            monthInterval--;
        }
        monthInterval %= 12;
        int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);
        return monthsDiff;
    }

    /**
     * 例: 2017-11
     */
    public static String date_yM_2String(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间几年前或是几年后
     *
     * @param distance 距离年数
     * @param addOrSub 年前年后 true-年前 false 年后
     * @return
     */
    public static String getYearDistanceByValue(int distance, boolean addOrSub) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (addOrSub) {
            year = year - distance;
        } else {
            year = year + distance;
        }

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, year);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(calendar1.getTime());
        return dateString;
    }

    private static final String TIME_MATCH_REGULAR_YYYYMMDD_HHMMSS = "(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2})\\s+(\\d{1,2}):(\\d{1,2}):(\\d{1,2})";
    private static final String TIME_MATCH_REGULAR_YYYYMMDD = "(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2})";

    public static String[] regularMatchTimeStr(String text) {
        List<String> list = new ArrayList<>();

        Pattern r1 = Pattern.compile(TIME_MATCH_REGULAR_YYYYMMDD_HHMMSS);
        Matcher m1 = r1.matcher(text);
        while (m1.find()) {
            String tstr = m1.group();
            list.add(tstr);
            text = text.replaceFirst(tstr, "");
        }

        Pattern r2 = Pattern.compile(TIME_MATCH_REGULAR_YYYYMMDD);
        Matcher m2 = r2.matcher(text);
        while (m2.find()) {
            String tstr = m2.group();
            list.add(tstr);
            text = text.replaceFirst(tstr, "");
        }


        String[] arr = new String[list.size()];
        return list.toArray(arr);
    }

    /**
     * 判断当前时间是否是当月最后一天
     *
     * @param date 需判断的时间
     * @return true: 是最后一天; false: 不是最后一天
     */
    public static boolean isLastDayOfMonth(Date date) {
        // 1. 创建日历类
        Calendar calendar = Calendar.getInstance();
        // 2. 设置当前传递的时间
        calendar.setTime(date);
        // 3. data的日期是N，那么N+1【假设当月是30天，30+1=31，如果当月只有30天，那么最终结果为1，也就是下月的1号】
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        // 4. 判断是否是当月最后一天
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }

    public static void main(String[] args) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-11");
        System.out.println(isLastDayOfMonth(date));
    }

}
