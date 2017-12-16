
package com.healthmudi.commonlibrary.widget.calendarview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 年份+月份选择布局
 * ViewPager + RecyclerView
 */
public class YearSelectLayout extends ViewPager {
    private int mYearCount;
    private CustomCalendarViewDelegate mDelegate;
    private YearRecyclerView.OnMonthSelectedListener mListener;

    public YearSelectLayout(Context context) {
        this(context, null);
    }

    public YearSelectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    void setup(CustomCalendarViewDelegate delegate) {
        this.mDelegate = delegate;
        this.mYearCount = mDelegate.getMaxYear() - mDelegate.getMinYear() + 1;
        setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mYearCount;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                YearRecyclerView view = new YearRecyclerView(getContext());
                container.addView(view);
                view.setup(mDelegate);
                view.setOnMonthSelectedListener(mListener);
                view.setup(mDelegate);
                view.init(position + mDelegate.getMinYear());
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                if (object instanceof YearRecyclerView)
                    container.removeView((YearRecyclerView) object);
            }
        });
        setCurrentItem(mDelegate.getCurrentDay().getYear() - mDelegate.getMinYear());
    }

    void notifyDataSetChanged() {
        this.mYearCount = mDelegate.getMaxYear() - mDelegate.getMinYear() + 1;
        getAdapter().notifyDataSetChanged();
    }

    void scrollToYear(int year) {
        setCurrentItem(year - mDelegate.getMinYear());
    }


    void update() {
        for (int i = 0; i < getChildCount(); i++) {
            YearRecyclerView view = (YearRecyclerView) getChildAt(i);
            view.getAdapter().notifyDataSetChanged();
        }
    }

    public void setOnMonthSelectedListener(YearRecyclerView.OnMonthSelectedListener listener) {
        this.mListener = listener;
    }
}
