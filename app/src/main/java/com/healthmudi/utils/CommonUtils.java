package com.healthmudi.utils;

import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

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
        if (mobile.length() != 11) {
            return true;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\\\d{8}$");
        Matcher m = p.matcher(mobile);
        if (m.matches()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动   false：不可以滚动
     */
    public static boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    /**
     * decription:
     * what：对SpannableString进行润色的各种Span；
     * int：需要润色文字段开始的下标；
     * end：需要润色文字段结束的下标；
     * flags：决定开始和结束下标是否包含的标志位，有四个参数可选
     * <p>
     * SPAN_INCLUSIVE_EXCLUSIVE：包括开始下标，但不包括结束下标
     * SPAN_EXCLUSIVE_INCLUSIVE：不包括开始下标，但包括结束下标
     * SPAN_INCLUSIVE_INCLUSIVE：既包括开始下标，又包括结束下标
     * SPAN_EXCLUSIVE_EXCLUSIVE：不包括开始下标，也不包括结束下标
     */
    public static SpannableString getSpannableString(String str, int color) {
        SpannableString spannableString = new SpannableString(str);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(foregroundColorSpan, str.indexOf("("), str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}
