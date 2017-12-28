package com.healthmudi.subjects_home.four.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseDialogFragment;
import com.healthmudi.view.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by tck
 * Date: 2017/12/28 14：48
 */

public class SelectSubjectDilaog extends BaseDialogFragment {

    private SmartRefreshLayout mRefreshLayout;
    private EmptyView mEmptyLayout;
    private ListView mListView;

    @Override
    protected void initData(@Nullable Bundle arguments) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_select_subject;
    }

    @Override
    protected void initView(View view) {
        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        mEmptyLayout = (EmptyView) view.findViewById(R.id.empty_layout);
        mListView = (ListView) view.findViewById(R.id.list_view);
        View headView = View.inflate(getContext(), R.layout.head_view_layout2, null);
        TextView te = (TextView) headView.findViewById(R.id.tv_head_title);
        te.setText("请选择受试者编号(可多选)");
        mListView.addHeaderView(headView);

        tvTitle.setText("受试者编号");
        ivCheckMark.setVisibility(View.VISIBLE);

        ivCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
