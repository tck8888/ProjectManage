package com.healthmudi.subjects_home.four.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BasicAdapter;
import com.healthmudi.bean.SubjectCodeBean;

import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/29 09：44
 */

public class SelectSubjectListAdapter extends BasicAdapter<SubjectCodeBean> {

    public SelectSubjectListAdapter(Context context, List<SubjectCodeBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onInitView(View convertView, int position) {
        TextView mTvItemValue = get(convertView, R.id.tv_item_value);
        SubjectCodeBean subjectCodeBean = mDataList.get(position);
        mTvItemValue.setText(subjectCodeBean.getSubject_code() + "  (" + subjectCodeBean.getName_py() + ")");
    }

    @Override
    protected int getContentView() {
        return R.layout.select_doc_list_item;
    }

}
