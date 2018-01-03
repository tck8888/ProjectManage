
package com.healthmudi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.healthmudi.R;
import com.healthmudi.commonlibrary.widget.calendarview.Calendar;
import com.healthmudi.commonlibrary.widget.calendarview.MonthView;

/**
 * Created by tck
 * Date: 2017/12/16 17：14
 */

public class ScheduleCalendarView extends MonthView {

    private Paint mTextPaint = new Paint();
    private Paint mSchemeBasicPaint = new Paint();
    private float mRadio;
    private int mPadding;
    private float mSchemeBaseLine;

    private int mRadius;

    public ScheduleCalendarView(Context context) {
        super(context);

        mTextPaint.setTextSize(dipToPx(context, 8));
        mTextPaint.setColor(0xff111111);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setColor(0xffed5353);
        mSchemeBasicPaint.setFakeBoldText(true);

        mRadio = dipToPx(getContext(), 4);
        Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
        mSchemeBaseLine = mRadio - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(getContext(), 1);
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mOtherMonthTextPaint.setColor(getResources().getColor(R.color.color_00000000));
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);

    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        if (calendar.isCurrentMonth()) {
            canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
        }

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        float baselineY = mTextBaseLine + y;
        if (hasScheme) {
            if (isSelected) {
                canvas.drawText(calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay()),
                        cx,
                        baselineY,
                        calendar.isCurrentDay() ? mCurDayTextPaint :
                                calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);
            } else {
                canvas.drawText(calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay()),
                        cx,
                        baselineY, calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);
            }
            if (!calendar.isCurrentDay()) {
                mSchemeBasicPaint.setColor(calendar.getSchemeColor());
                canvas.drawCircle((float) (cx + Math.sqrt(2) * mRadius / 2), (float) (cy - Math.sqrt(2) / 2 * mRadius), mRadio, mSchemeBasicPaint);
            }
        } else {
            canvas.drawText(calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay()), cx, baselineY,
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
