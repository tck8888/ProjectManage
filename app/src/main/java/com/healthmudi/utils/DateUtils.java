package com.healthmudi.utils;

import android.support.annotation.Nullable;

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

}
