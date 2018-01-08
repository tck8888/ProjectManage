package com.healthmudi.utils;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tck
 * Date: 2017/12/21 09：22
 */

public class DateUtils {

    /**
     * 返回年-月-日 hh:mm
     */
    public static String getFormatTime(@Nullable long time) {
        if (String.valueOf(time).length() <= 10) {
            time = time * 1000;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(time);
        String formatTime = format.format(date);
        return formatTime;
    }

    /**
     * 返回年-月-日 hh:mm
     */
    public static String getFormatTime1(@Nullable long time) {
        if (String.valueOf(time).length() <= 10) {
            time = time * 1000;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(time);
        String formatTime = format.format(date);
        return formatTime;
    }


    public static String getFormatTime2(@Nullable long time) {
        if (String.valueOf(time).length() <= 10) {
            time = time * 1000;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        String formatTime = format.format(date);
        return formatTime;
    }

    public static String getFormatTime3(@Nullable long time) {
        if (String.valueOf(time).length() <= 10) {
            time = time * 1000;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(time);
        String formatTime = format.format(date);
        return formatTime;
    }

    /**
     * 返回年-月-日 hh:mm
     */
    public static String getFormatTime(@Nullable String time) {
        long actualTime;
        if (TextUtils.isEmpty(time)) {
            actualTime = getCurrentTime();
        } else {
            if (time.length() <= 10) {
                actualTime = Long.parseLong(time) * 1000;
            } else {
                actualTime = Long.parseLong(time);
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(actualTime);
        String formatTime = format.format(date);
        return formatTime;
    }

    /**
     * decription:获取当前系统时间
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * decription:获取当前系统时间
     */
    public static String getCurrentTimeStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date date = new Date(System.currentTimeMillis());
        String formatTime = format.format(date);
        return formatTime;
    }

    /**
     * @param date
     * @return yyyy-MM-dd
     */
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 计算两个时间的日期差
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int getDifferenceDay(long beginTime, long endTime) {
        if (String.valueOf(beginTime).length() <= 10) {
            beginTime = beginTime * 1000;
        }

        if (String.valueOf(endTime).length() <= 10) {
            endTime = endTime * 1000;
        }
        return (int) ((endTime - beginTime) / (1000 * 24 * 60 * 60));
    }


    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    //返回当前年月日
    public static String getNowDate() {
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        return nowDate;
    }

    /**
     * 得到某一个月的具体天数
     *
     * @param year  参数月所在年
     * @param month 参数月
     * @return int 参数月所包含的天数
     */
    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;
        // 闰年2月29天
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            monthDays[1] = 29;
        }
        try {
            days = monthDays[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }
        return days;
    }

    /**
     * 得到当前月第一天在其周的位置
     *
     * @param year  当前年
     * @param month 当前月
     * @param type  周排列方式 0代表周一作为本周的第一天， 2代表周日作为本周的第一天
     * @return int 本月第一天在其周的位置
     */
    public static int getFirstDayWeekPosition(int year, int month, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (type == 1) {
            return week_index;
        } else {
            week_index = cal.get(Calendar.DAY_OF_WEEK) + 5;
            if (week_index >= 7) {
                week_index -= 7;
            }
        }
        return week_index;

    }

    /**
     * 将yyyy-MM-dd类型的字符串转化为对应的Date对象
     *
     * @param year  当前年
     * @param month 当前月
     * @return Date  对应的Date对象
     */
    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month)) + "-01";
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }


    public static String getDateStr(int year, int month, int day) {
        if (month < 10) {
            if (day < 10) {
                return year + "年" + "0" + month + "月" + "0" + day + "日";
            } else {
                return year + "年" + "0" + month + "月" + day + "日";
            }
        } else {
            if (day < 10) {
                return year + "年" + month + "月" + "0" + day + "日";
            } else {
                return year + "年" + month + "月" + day + "日";
            }
        }
    }

    public static String getDateStr(int year, int month) {
        if (month < 10) {
            return year + "年" + "0" + month + "月";
        } else {
            return year + "年" + month + "月";
        }
    }

    public static String getDateStr1(int year, int month) {
        if (month < 10) {
            return String.valueOf(year) + "0" + String.valueOf(month);
        } else {
            return String.valueOf(year) + String.valueOf(month);
        }
    }

    public static long getTimestamp(String str) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd");
            Date date = simpleDateFormat.parse(str);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return getCurrentTime();
        }

    }

}
