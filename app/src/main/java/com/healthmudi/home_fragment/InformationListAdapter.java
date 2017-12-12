package com.healthmudi.home_fragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.InformationListBean;

import java.util.List;

/**
 * decription:
 * Created by tck on 2017/12/12.
 */

public class InformationListAdapter extends BasicAdapter<InformationListBean> {

    public InformationListAdapter(Context context, List<InformationListBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        ImageView mIvImageType = get(convertView, R.id.iv_image_type);
        TextView mTvTextType = get(convertView, R.id.tv_text_type);
        TextView mTvTextDate = get(convertView, R.id.tv_text_date);
        TextView mTvTextContent = get(convertView, R.id.tv_text_content);

        InformationListBean informationListBean = mDataList.get(position);

        mTvTextType.setText(informationListBean.type);
        mTvTextContent.setText(informationListBean.content);
        mTvTextDate.setText(informationListBean.date);
    }

    @Override
    protected int getContentView() {
        return R.layout.information_list_item;
    }
}
