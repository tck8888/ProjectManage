package com.healthmudi.subjects_home.one.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by tck
 * Date: 2018/01/03 16：31
 */

public class KeyWordAdapter extends TagAdapter<String> {

    private Context mContext;

    public KeyWordAdapter(List<String> datas, Context context) {
        super(datas);
        mContext = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView tagName = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tag_item, parent, false);
        tagName.setText(s);
        return tagName;
    }
}
