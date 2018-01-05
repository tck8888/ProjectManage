package com.healthmudi.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Calendar;

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

    /**
     * 返回年-月-日 hh:mm
     */
    public static String getFormatTime2(@Nullable long time) {
        if (String.valueOf(time).length() <= 10) {
            time = time * 1000;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
        Date date = new Date();
        String year = new SimpleDateFormat("yyyy").format(date);
        return Integer.parseInt(year);
    }

    public static int getMonth() {
        Date date = new Date();
        String month = new SimpleDateFormat("MM").format(date);
        return Integer.parseInt(month);
    }

    public static int getDay() {
        Date date = new Date();
        String day = new SimpleDateFormat("dd").format(date);
        return Integer.parseInt(day);
    }

    //返回当前年月日
    public static String getNowDate() {
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        return nowDate;
    }

    //返回当月天数
    public static int getDays(int year, int month) {
        int days;
        int FebDay = 28;
        if (isLeap(year))
            FebDay = 29;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = FebDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }

    //判断闰年
    public static boolean isLeap(int year) {
        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
            return true;
        else
            return false;
    }

    /**
     * 返回当前月份1号位于周几
     * @param year
     * 		年份
     * @param month
     * 		月份，传入系统获取的，不需要正常的
     * @return
     * 	日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

}
