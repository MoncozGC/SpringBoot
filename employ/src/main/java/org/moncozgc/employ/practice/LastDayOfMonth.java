package org.moncozgc.employ.practice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 判断时间是否为当月最后一天
 * Created by MoncozGC on 2023/1/11
 */
public class LastDayOfMonth {
    public static void main(String[] args) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01");
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
        int i = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(i);
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            System.out.println("YES");
        } else
            System.out.println("NO");
    }
}
