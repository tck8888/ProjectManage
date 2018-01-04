package com.healthmudi.subjects_home.one.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.ItemsBean;
import com.healthmudi.bean.VisitContentBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/26 16：25
 */

public class VisitContentAdapter extends BasicAdapter<VisitContentBean> {

    public VisitContentAdapter(Context context, List<VisitContentBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        ImageView mIvParentName = get(convertView, R.id.iv_parent_name);
        TextView mTvParentName = get(convertView, R.id.tv_parent_name);
        View llSelectAll = get(convertView, R.id.ll_select_all);
        TagFlowLayout mTagFlowlayout = get(convertView, R.id.tag_flowlayout);

        final VisitContentBean visitContentBean = mDataList.get(position);

        mTvParentName.setText(visitContentBean.getCategory());
        llSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visitContentBean.isSelected()) {
                    visitContentBean.setSelected(false);
                } else {
                    visitContentBean.setSelected(true);
                }
                setSubState(visitContentBean);
                notifyDataSetChanged();
            }
        });
        if (visitContentBean.isSelected()) {
            mIvParentName.setImageResource(R.drawable.icon_rectangle_selected);
        } else {
            mIvParentName.setImageResource(R.drawable.icon_rectangle_unselected);

        }
        final CategoryListAdapter categoryListAdapter = new CategoryListAdapter(mContext, visitContentBean.getItems());
        mTagFlowlayout.setAdapter(categoryListAdapter);

        mTagFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (visitContentBean.getItems().get(position).isSelected()) {
                    visitContentBean.getItems().get(position).setSelected(false);
                } else {
                    visitContentBean.getItems().get(position).setSelected(true);
                }
                visitContentBean.setSelected(checkState(visitContentBean));
                notifyDataSetChanged();
                return false;
            }
        });
    }

    private boolean checkState(VisitContentBean visitContentBean) {
        for (ItemsBean itemsBean : visitContentBean.getItems()) {
            if (!itemsBean.isSelected()) {
                return false;
            }
        }
        return true;
    }

    private void setSubState(VisitContentBean visitContentBean) {
        for (ItemsBean itemsBean : visitContentBean.getItems()) {
            itemsBean.setSelected(visitContentBean.isSelected());
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.visit_content_item;
    }
}
