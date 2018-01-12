package com.healthmudi.subjects_home.four.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;

import com.healthmudi.R;
import com.healthmudi.base.BaseDialogFragment;
import com.healthmudi.base.Constant;
import com.healthmudi.base.ProjectManageHttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.bean.SubjectCodeBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.four.adapter.SelectSubjectListAdapter;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.EmptyView;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tck
 * Date: 2017/12/28 14：48
 */

public class SelectSubjectDilaog extends BaseDialogFragment implements OnRefreshListener {


    private SmartRefreshLayout mRefreshLayout;
    private AutoListView mListView;
    private EmptyView mEmptyLayout;

    private Map<String, String> map = new HashMap<>();
    private TreeMap<Integer, SubjectCodeBean> selectString = new TreeMap<>();
    private List<SubjectCodeBean> mSubjectCodeBeanList = new ArrayList<>();
    private String tag = "SelectSubjectDilaog";
    private SelectSubjectListAdapter mAdapter;

    @Override
    protected void initData(@Nullable Bundle arguments) {
        ProjectListBean projectListBean = Hawk.get(Constant.KEY_PROJECT_LIST_BEAN);
        map.put("project_id", String.valueOf(projectListBean.getProject_id()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_select_doc;
    }

    @Override
    protected void initView(View view) {
        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        mEmptyLayout = (EmptyView) view.findViewById(R.id.empty_layout);
        mListView = (AutoListView) view.findViewById(R.id.auto_list_view);
        mAdapter = new SelectSubjectListAdapter(getContext(), mSubjectCodeBeanList);
        mListView.setAdapter(mAdapter);
        tvTitle.setText("受试者编号");
        ivCheckMark.setVisibility(View.VISIBLE);
    }

    @Override
    public void setListener(View view) {
        super.setListener(view);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.autoRefresh();

        ivCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectString.isEmpty()) {

                    EventBus.getDefault().post(new MessageEvent<TreeMap<Integer, SubjectCodeBean>>(MessageEvent.KEY_SELECT_SUBJECT_SUCCESS, selectString));
                }
                dismiss();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray checkedItemPositions = mListView.getCheckedItemPositions();
                boolean b = checkedItemPositions.get(position);
                if (b) {
                    selectString.put(position, mSubjectCodeBeanList.get(position));
                } else {
                    selectString.remove(position);
                }
            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().get(ProjectManageHttpUrlList.PROJECT_SUBJECT_CODES_URL, map, tag, new OnServerCallBack<HttpResult<List<SubjectCodeBean>>, List<SubjectCodeBean>>() {
            @Override
            public void onSuccess(List<SubjectCodeBean> result) {
                if (!ListUtil.isEmpty(mSubjectCodeBeanList)) {
                    mSubjectCodeBeanList.clear();
                }
                mSubjectCodeBeanList.addAll(result);
                if (ListUtil.isEmpty(mSubjectCodeBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(int code, String mesage) {
                if (ListUtil.isEmpty(mSubjectCodeBeanList)) {
                    mEmptyLayout.showEmptyView();
                    mEmptyLayout.setEmptyText(mesage);
                } else {
                    mEmptyLayout.showContentView();
                }
                mRefreshLayout.finishRefresh();
            }
        });
    }

}
