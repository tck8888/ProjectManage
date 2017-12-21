package com.healthmudi.utils;

import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tck
 * Date: 2017/12/21 15：21
 */

public class CommonUtils {


    /**
     * 验证手机格式
     */
    public static boolean checkMobile(@Nullable String mobile) {

        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
}
