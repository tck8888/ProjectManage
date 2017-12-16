
package com.healthmudi.commonlibrary.widget.calendarview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthmudi.commonlibrary.R;

class YearAdapter extends BaseRecyclerAdapter<Month> {
    private CustomCalendarViewDelegate mDelegate;
    private int mItemHeight;
    private int mTextHeight;

    YearAdapter(Context context) {
        super(context);
        mTextHeight = Util.dipToPx(context,56);
    }

    void setup(CustomCalendarViewDelegate delegate) {
        this.mDelegate = delegate;
    }

    void setItemHeight(int itemHeight) {
        this.mItemHeight = itemHeight;
    }

    @Override
    RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new YearViewHolder(mInflater.inflate(R.layout.cv_item_list_year, parent, false), mDelegate);
    }

    @Override
    void onBindViewHolder(RecyclerView.ViewHolder holder, Month item, int position) {
        YearViewHolder h = (YearViewHolder) holder;
        YearView view = h.mYearView;
        view.setSchemes(mDelegate.mSchemeDate);
        view.setSchemeColor(mDelegate.getYearViewSchemeTextColor());
        view.setTextStyle(mDelegate.getYearViewDayTextSize(),
                mDelegate.getYearViewDayTextColor());
        view.init(item.getDiff(), item.getCount(), item.getYear(), item.getMonth());
        view.getLayoutParams().height = mItemHeight - mTextHeight;
        h.mTextMonth.setText(String.format("%s月", item.getMonth()));
        h.mTextMonth.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDelegate.getYearViewMonthTextSize());
        h.mTextMonth.setTextColor(mDelegate.getYearViewMonthTextColor());
    }

    private static class YearViewHolder extends RecyclerView.ViewHolder {
        YearView mYearView;
        TextView mTextMonth;

        YearViewHolder(View itemView, CustomCalendarViewDelegate delegate) {
            super(itemView);
            mYearView = (YearView) itemView.findViewById(R.id.selectView);
            mYearView.setup(delegate);
            mTextMonth = (TextView) itemView.findViewById(R.id.tv_month);
        }
    }
}
