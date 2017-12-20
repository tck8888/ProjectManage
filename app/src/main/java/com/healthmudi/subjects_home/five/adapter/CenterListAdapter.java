package com.healthmudi.subjects_home.five.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.CenterListBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/18 16：17
 */

public class CenterListAdapter extends BasicAdapter<CenterListBean> {

    public CenterListAdapter(Context context, List<CenterListBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        TextView mTvCenterName = get(convertView, R.id.tv_center_name);
        ImageView mIvImageType = get(convertView, R.id.iv_image_type);

        CenterListBean centerListBean = mDataList.get(position);
        mTvCenterName.setText(centerListBean.getCenterName());
        if (centerListBean.isChecked()) {
            mIvImageType.setVisibility(View.VISIBLE);
        } else {
            mIvImageType.setVisibility(View.GONE);
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.center_list_item;
    }
}
