package com.healthmudi.subjects_home.three.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.PunchClockSelectLocationListBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/16 13：21
 */

public class PunchClockSelectLocationAdapter extends BasicAdapter<PunchClockSelectLocationListBean> {

    public PunchClockSelectLocationAdapter(Context context, List<PunchClockSelectLocationListBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        TextView hospitalName = (TextView) get(convertView, R.id.tv_sign_hospital_name);
        TextView hospitalDistance = (TextView) get(convertView, R.id.tv_sign_hospital_distance);
        TextView hospitalLocation = (TextView) get(convertView, R.id.tv_sign_hospital_location);
        ImageView ivStateSelected = (ImageView) get(convertView, R.id.iv_state_selected);

        PunchClockSelectLocationListBean punchClockSelectLocationListBean = mDataList.get(position);
        hospitalName.setText(punchClockSelectLocationListBean.getHospitalName());
        hospitalDistance.setText(punchClockSelectLocationListBean.getDistance());
        hospitalLocation.setText(punchClockSelectLocationListBean.getLocation());

        if (punchClockSelectLocationListBean.isSelected()) {
            ivStateSelected.setVisibility(View.VISIBLE);
        } else {
            ivStateSelected.setVisibility(View.GONE);
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.punch_clock_select_location_item;
    }
}
