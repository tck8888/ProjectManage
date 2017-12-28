package com.healthmudi.subjects_home.four.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;

import java.util.List;

/**
 * decription:
 * Created by tck on 2017/12/28.
 */

public class SelectDocListAdapter extends BasicAdapter<String> {

    public SelectDocListAdapter(Context context, List<String> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        TextView mTvItemValue = get(convertView, R.id.tv_item_value);
        String s = mDataList.get(position);
        mTvItemValue.setText(s);
    }

    @Override
    protected int getContentView() {
        return R.layout.select_doc_list_item;
    }
}
