package com.healthmudi.subjects_home.four;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.SiteApproveListBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/27 09：46
 */

public class SiteApproveListAdapter extends BasicAdapter<SiteApproveListBean> {

    public SiteApproveListAdapter(Context context, List<SiteApproveListBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        TextView mTvItemName = get(convertView, R.id.tv_item_name);
        ImageView mIvImageType = get(convertView, R.id.iv_image_type);

        SiteApproveListBean siteApproveListBean = mDataList.get(position);
        mTvItemName.setText(siteApproveListBean.getName());
        if (siteApproveListBean.isSeleted()) {
            mIvImageType.setVisibility(View.VISIBLE);
        } else {
            mIvImageType.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.site_approve_list_item;
    }
}
