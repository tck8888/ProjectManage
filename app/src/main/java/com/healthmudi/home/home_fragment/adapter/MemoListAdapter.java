package com.healthmudi.home.home_fragment.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.MemoBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 13：46
 */

public class MemoListAdapter extends BasicAdapter<MemoBean> {

    public MemoListAdapter(Context context, List<MemoBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        TextView mTvSubjectsPeopleName = get(convertView, R.id.tv_subjects_people_name);
        TextView mTvWorkSatus = get(convertView, R.id.tv_work_satus);

        MemoBean memoBean = mDataList.get(position);
        mTvSubjectsPeopleName.setText(memoBean.getMemo_content());

        if (memoBean.getStatus() == 0) {
            mTvWorkSatus.setText("未完成");
            mTvWorkSatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_fffda746));
        } else {
            mTvWorkSatus.setText("已完成");
            mTvWorkSatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_corner_11dp_solid_1abc9c));
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.item_memo_list;
    }
}
