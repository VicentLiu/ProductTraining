package com.ruixinyuan.producttrainingfinal.utils;

import java.util.Calendar;
import java.util.Locale;


/*
 *@user vicentliu
 *@time 2013-6-26下午3:54:11
 *@package com.ruixinyuan.producttrainingfinal.utils
 */
public class TimeUtils {

    /**
     * 判断是否是早上八点，若是，则开始下载数据；否则，不下载
     * @return
     */
    public static boolean isEightOClockNow () {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int minuteOfDay = hour * 60 + minute;
        int startTime = 8 * 60 + 00; //开始时间8点
        int endTime = 8 * 60 + 40; //结束时间8点半
        if (minuteOfDay > startTime && minuteOfDay < endTime)
            return true;
        else
            return false;
    }
}
