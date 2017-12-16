
package com.healthmudi.commonlibrary.widget.calendarview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthmudi.commonlibrary.R;

/**
 * 星期栏
 * Created by huanghaibin on 2017/11/30.
 */
public class WeekBar extends LinearLayout {
    public WeekBar(Context context) {
        super(context);
        if ("WeekBar".equals(getClass().getName())) {
            LayoutInflater.from(context).inflate(R.layout.cv_week_bar, this, true);
        }

    }

    /**
     * 传递属性
     *
     * @param delegate delegate
     */
    void setup(CustomCalendarViewDelegate delegate) {
        setBackgroundColor(delegate.getWeekBackground());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Util.dipToPx(getContext(), 40), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置文本颜色
     *
     * @param color color
     */
    void setTextColor(int color) {
        for (int i = 0; i < getChildCount(); i++) {
            ((TextView) getChildAt(i)).setTextColor(color);
        }
    }
}
