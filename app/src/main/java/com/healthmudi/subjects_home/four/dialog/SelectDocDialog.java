package com.healthmudi.subjects_home.four.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;

import com.healthmudi.R;
import com.healthmudi.base.BaseDialogFragment;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.commonlibrary.widget.AutoListView;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.four.adapter.SelectDocListAdapter;
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
 * decription:
 * Created by tck on 2017/12/28.
 */

public class SelectDocDialog extends BaseDialogFragment implements OnRefreshListener {


    private SmartRefreshLayout mRefreshLayout;
    private AutoListView mListView;
    private EmptyView mEmptyLayout;

    private Map<String, String> map = new HashMap<>();
    private Map<Integer, String> selectString = new TreeMap<>();
    private List<String> mStringList = new ArrayList<>();
    private String tag = "SelectDocDialog";
    private SelectDocListAdapter mAdapter;

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
        mAdapter = new SelectDocListAdapter(getContext(), mStringList);
        mListView.setAdapter(mAdapter);
        tvTitle.setText("伦理递交");
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
                    StringBuffer sb = new StringBuffer();
                    for (String str : selectString.values()) {
                        sb.append(str).append(",");
                    }
                    EventBus.getDefault().post(new MessageEvent<String>(MessageEvent.KEY_SELECT_DOC_SUCCESS, sb.delete(sb.length() - 1, sb.length()).toString()));
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
                    selectString.put(position, mStringList.get(position));
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
        HttpRequest.getInstance().get(HttpUrlList.PROJECT_CONFIG_EC_DOC_URL, map, tag, new OnServerCallBack<HttpResult<List<String>>, List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                if (!ListUtil.isEmpty(mStringList)) {
                    mStringList.clear();
                }
                mStringList.addAll(result);
                if (ListUtil.isEmpty(mStringList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(int code, String mesage) {
                if (ListUtil.isEmpty(mStringList)) {
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
