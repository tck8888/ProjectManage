package com.healthmudi.subjects_home.one.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.bean.ItemsBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/26 16：38
 */

public class CategoryListAdapter extends TagAdapter<ItemsBean> {

    private Context mContext;

    public CategoryListAdapter(List<ItemsBean> datas) {
        super(datas);
    }

    public CategoryListAdapter(Context context, List<ItemsBean> datas) {
        super(datas);
        mContext = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, ItemsBean itemsBean) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.visit_category_list_item, parent, false);

        ImageView mIvCategoryName = (ImageView) view.findViewById(R.id.iv_category_name);
        TextView mTvCategoryName = (TextView) view.findViewById(R.id.tv_category_name);
        if (TextUtils.isEmpty(itemsBean.getItem_en())) {
            mTvCategoryName.setText(itemsBean.getItem_cn());
        } else if (TextUtils.isEmpty(itemsBean.getItem_cn())) {
            mTvCategoryName.setText(itemsBean.getItem_en());
        } else if (!TextUtils.isEmpty(itemsBean.getItem_cn()) && !TextUtils.isEmpty(itemsBean.getItem_en())) {
            mTvCategoryName.setText(itemsBean.getItem_en() + "(" + itemsBean.getItem_cn() + ")");
        }
        if (itemsBean.isSelected()) {
            mIvCategoryName.setImageResource(R.drawable.icon_rectangle_selected);
        } else {
            mIvCategoryName.setImageResource(R.drawable.icon_rectangle_unselected);
        }


        return view;
    }
}
