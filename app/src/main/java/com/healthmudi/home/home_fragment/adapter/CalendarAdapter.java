package com.healthmudi.home.home_fragment.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthmudi.R;

import java.util.List;

/**
 * decription:
 * Created by tck on 2018/1/5.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private Context mContext;
    private List<CalendarBean> mDataList;
    private LayoutInflater mInflater;
    private int selectPosition = -1;

    public CalendarAdapter(Context context, List<CalendarBean> dataList) {
        mContext = context;
        mDataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_schedule_calendar, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        CalendarBean calendarBean = mDataList.get(position);
        if (calendarBean.isChecked()) {
            holder.mTvDay.setTextColor(ContextCompat.getColor(mContext, R.color.color_1abc9c));
            holder.mTvDay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_circular_solid_fff));
        } else {
            if (calendarBean.isCurrentDay()) {
                holder.mTvDay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_circular_border_fff));
            } else {
                holder.mTvDay.setBackground(null);
            }
            holder.mTvDay.setTextColor(ContextCompat.getColor(mContext, R.color.color_ffffff));
        }
        if (calendarBean.isCurrentDay()) {
            holder.mTvDay.setText("今");
        } else {
            holder.mTvDay.setText(String.valueOf(calendarBean.getDay()));
        }
        if (calendarBean.isEmpty()) {
            holder.mTvDay.setVisibility(View.GONE);
        } else {
            holder.mTvDay.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvDay;
        private TextView mTvRedDot;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            mTvDay = (TextView) itemView.findViewById(R.id.tv_day);
            mTvRedDot = (TextView) itemView.findViewById(R.id.tv_red_dot);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectPosition != -1) {
                        mDataList.get(selectPosition).setChecked(false);
                    }
                    selectPosition = getLayoutPosition();
                    mDataList.get(selectPosition).setChecked(true);
                   notifyDataSetChanged();
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(selectPosition);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
