package com.healthmudi.base;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.entity.CommonItemBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/11/29 13：06
 */

public class CommonItemAdapter extends BasicAdapter<CommonItemBean> {

    public CommonItemAdapter(Context context, List<CommonItemBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        TextView mTvItemValue = get(convertView, R.id.tv_item_value);
        View mItemDivider = get(convertView, R.id.item_divider);
        CommonItemBean commonItemBean = mDataList.get(position);
        mTvItemValue.setText(commonItemBean.data.toString());
        if (position % 2 == 0) {
            mItemDivider.setVisibility(View.VISIBLE);
        } else {
            mItemDivider.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.common_list_item;
    }

}
