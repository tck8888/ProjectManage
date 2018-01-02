
package com.healthmudi.commonlibrary.widget.my_calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.healthmudi.commonlibrary.widget.calendarview.Calendar;
import com.healthmudi.commonlibrary.widget.calendarview.MonthView;


/**
 * Created by tck
 * Date: 2017/12/16 17：22
 */

public class ScheduleCalendarCardView extends MonthView {

    private int mRadius;
    private float mRadio;

    private Paint mSchemeBasicPaint = new Paint();

    public ScheduleCalendarCardView(Context context) {
        super(context);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setColor(0xffed5353);
        mSchemeBasicPaint.setFakeBoldText(true);

        mRadio = dipToPx(getContext(), 7);

    }

    @Override
    protected void onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {

    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int top = y - mItemHeight / 6;
        if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);


            mSchemeBasicPaint.setColor(calendar.getSchemeColor());

            canvas.drawCircle(x + mItemWidth - mRadio / 2, y + mRadio, mRadio, mSchemeBasicPaint);


        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);

        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
