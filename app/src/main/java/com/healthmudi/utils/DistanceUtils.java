package com.healthmudi.utils;

import android.support.annotation.Nullable;

/**
 * Created by tck
 * Date: 2017/12/21 10：04
 */

public class DistanceUtils {


    public static String getDistance(@Nullable double distance) {

        StringBuilder sb = new StringBuilder();
        if (distance < 100) {
            sb.append("<100米以内");
        } else if (distance >= 1000) {
            sb.append(formatDouble(distance / 1000)).append("公里");
        } else if (distance >= 100 && distance < 1000) {
            sb.append(distance).append("米");
        }
        return sb.toString();
    }

    public static String formatDouble(@Nullable double d) {
        String str = String.format("%.1f", d);
        return str;
    }
}
