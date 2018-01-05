package com.healthmudi.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
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

}
