package com.healthmudi.utils;

import android.support.annotation.Nullable;

/**
 * Created by tck
 * Date: 2017/12/26 14：36
 */

public class StringConvertCodeEachUtils {

    public static String getString(@Nullable int code) {
        String str = "";
        switch (code) {
            case 1:
                str = "上次实际访视日期";
                break;
            case 2:
                str = "知情同意书签署日期";
                break;
            case 3:
                str = "随机分组日期";
                break;
            case 4:
                str = "首次服药日期";
                break;
        }
        return str;
    }


    public static String getString(@Nullable String type) {
        String str = "";
        switch (type) {
            case "上次实际访视日期":
                str = "1";
                break;
            case "知情同意书签署日期":
                str = "2";
                break;
            case "随机分组日期":
                str = "3";
                break;
            case "首次服药日期":
                str = "4";
                break;
        }
        return str;
    }

}
