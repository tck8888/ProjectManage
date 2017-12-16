
package com.healthmudi.commonlibrary.widget.my_calendarview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.healthmudi.commonlibrary.widget.calendarview.Calendar;
import com.healthmudi.commonlibrary.widget.calendarview.WeekView;

/**
 * Created by tck
 * Date: 2017/12/16 17：14
 */

public class ScheduleWeekView extends WeekView {

    private int mRadius;


    public ScheduleWeekView(Context context) {
        super(context);
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDrawSelected(Canvas canvas, Calendar calendar, int x, boolean hasScheme) {

    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x) {
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine ;
        int cx = x + mItemWidth / 2;
        if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mSchemeTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mCurMonthTextPaint);
        }
    }
}
