package com.healthmudi.subjects_home.three;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.SignHistoryListBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/16 10：23
 */

public class SignHistoryAdapter extends BasicAdapter<SignHistoryListBean> {

    public SignHistoryAdapter(Context context, List<SignHistoryListBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        TextView tvSignHospitalName = get(convertView, R.id.tv_sign_hospital_name);
        TextView tvSignHospitalTime = get(convertView, R.id.tv_sign_hospital_time);
        View line1 = get(convertView, R.id.line1);
        if (position == mDataList.size() - 1) {
            line1.setVisibility(View.GONE);
        }
        SignHistoryListBean signHistoryListBean = mDataList.get(position);
        tvSignHospitalName.setText(signHistoryListBean.getHospitalName());
        tvSignHospitalTime.setText(signHistoryListBean.getHospitalTime());
    }

    @Override
    protected int getContentView() {
        return R.layout.sign_history_item;
    }
}
