package com.healthmudi.subjects_home.home_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.base.ProjectManageHttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.home_fragment.adapter.SubjectsListAdapter;
import com.healthmudi.subjects_home.one.EntryGroupBasicInformationActivity;
import com.healthmudi.subjects_home.one.SubjectsPersonalListActivity;
import com.healthmudi.subjects_home.one.SubjectsPersonalSerachActivity;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.EmptyView;
import com.healthmudi.view.IosDialog;
import com.healthmudi.view.LoadingDialog;
import com.lzy.okgo.OkGo;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * decription:受试者fragment
 * Created by tck on 2017/12/9.
 */

public class SubjectsFragment extends BaseFragment1 implements View.OnClickListener, OnRefreshListener {

    private ExpandableListView mExpandableListView;
    private SmartRefreshLayout mRefreshLayout;
    private EmptyView mEmptyLayout;

    private SubjectsListAdapter mAdapter;

    private List<SubjectsListBean> mSubjectsListBeanList = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();
    private Map<String, String> mapDelete = new HashMap<>();

    private String tag = "SubjectsFragment";
    private String mProject_id;
    private ProjectListBean mProjectListBean;

    private IosDialog mIosDialog;

    public static SubjectsFragment newInstance(ProjectListBean projectListBean) {
        SubjectsFragment subjectsFragment = new SubjectsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_PROJECT_LIST_BEAN, projectListBean);
        subjectsFragment.setArguments(bundle);
        return subjectsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_subjects;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mProjectListBean = (ProjectListBean) arguments.getSerializable(Constant.KEY_PROJECT_LIST_BEAN);
        mProject_id = String.valueOf(mProjectListBean.getProject_id());
        map.put("project_id", mProject_id);
        mapDelete.put("project_id", mProject_id);
    }

    @Override
    protected void initView(@Nullable View view) {
        mEmptyLayout = (EmptyView) view.findViewById(R.id.empty_layout);
        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.expandable_list_view);
        mExpandableListView.setGroupIndicator(null);
        mAdapter = new SubjectsListAdapter(getContext(), mSubjectsListBeanList);
        mExpandableListView.setAdapter(mAdapter);

        initDialog();

        if (mProjectListBean != null) {
            TextView title = (TextView) view.findViewById(R.id.tv_title);
            title.setText(mProjectListBean.getProject_name());
        }
    }

    public void initDialog() {
        mIosDialog = new IosDialog.Builder(getContext())
                .setTitle("提示")
                .setTitleColor(getResources().getColor(R.color.color_464c5b))
                .setMessage("是否确定删除")
                .setPositiveButton("确认", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        deleteData();
                        mIosDialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        mIosDialog.dismiss();
                    }
                })
                .setPositiveButtonColor(getResources().getColor(R.color.color_1abc9c))
                .setNegativeButtonColor(getResources().getColor(R.color.color_464c5b))
                .setDialogCanceledOnTouchOutside(true)
                .build();
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);

        view.findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        view.findViewById(R.id.iv_subjects_search).setOnClickListener(this);
        view.findViewById(R.id.iv_add_subjects).setOnClickListener(this);

        mAdapter.setOnItemClick(new SubjectsListAdapter.OnItemClick() {
            @Override
            public void click(int groupPosition, int childPosition, String type) {
                SubjectsListBean.SubjectsBean subjectsBean = mSubjectsListBeanList.get(groupPosition).getSubjects().get(childPosition);
                if ("delete".equals(type)) {
                    showDialog(subjectsBean);
                } else {
                    Hawk.put(Constant.KEY_SUBJECTS_BEAN, subjectsBean);
                    openActivity(SubjectsPersonalListActivity.class, null, subjectsBean);
                }

            }
        });
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.autoRefresh();
    }

    private void showDialog(@Nullable SubjectsListBean.SubjectsBean subjectsBean) {
        mIosDialog.show();
        mapDelete.put("site_id", String.valueOf(subjectsBean.getSite_id()));
        mapDelete.put("subject_id", String.valueOf(subjectsBean.getSubject_id()));
    }

    /**
     * 删除数据
     */
    private void deleteData() {
        LoadingDialog.getInstance(getContext()).show();
        HttpRequest.getInstance().post(ProjectManageHttpUrlList.PROJECT_SUBJECT_DEL_URL, mapDelete, tag, new OnServerCallBack<HttpResult<Object>, Object>() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(getContext()).hidden();
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                //删除成功自动刷新界面
                mRefreshLayout.autoRefresh();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(getContext()).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(getContext(), mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().get(ProjectManageHttpUrlList.PROJECT_SUBJECT_LIST_URL, map, tag, new OnServerCallBack<HttpResult<List<SubjectsListBean>>, List<SubjectsListBean>>() {
            @Override
            public void onSuccess(List<SubjectsListBean> result) {
                if (!ListUtil.isEmpty(mSubjectsListBeanList)) {
                    mSubjectsListBeanList.clear();
                }
                mSubjectsListBeanList.addAll(result);
                if (ListUtil.isEmpty(mSubjectsListBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                    //默认展开第一个
                    mExpandableListView.expandGroup(0);
                }

                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(int code, String mesage) {
                if (ListUtil.isEmpty(mSubjectsListBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }
                mRefreshLayout.finishRefresh();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                getActivity().finish();
                break;
            case R.id.iv_subjects_search:
                openActivity(SubjectsPersonalSerachActivity.class, mProjectListBean, null);
                break;
            case R.id.iv_add_subjects:
                openActivity(EntryGroupBasicInformationActivity.class, mProjectListBean, null);
                break;
        }
    }

    /**
     * description:跳转activity
     *
     * @param projectListBean 项目bean
     * @param subjectsBean    受试者bean
     */
    public void openActivity(Class clazz, ProjectListBean projectListBean, SubjectsListBean.SubjectsBean subjectsBean) {
        if (clazz != null) {
            Intent intent = new Intent(getContext(), clazz);
            if (projectListBean != null) {
                intent.putExtra(Constant.KEY_PROJECT_LIST_BEAN, mProjectListBean);
            }

            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackInfo(MessageEvent event) {
        if (event != null) {
            if (event.getTag().equals(MessageEvent.KEY_ENTRY_GROUP_BASIC_INFORMATION_ADD_SUCCESS)) {
                mRefreshLayout.autoRefresh();
            } else if (event.getTag().equals(MessageEvent.KEY_ENTRY_GROUP_BASIC_INFORMATION_UPDATE_SUCCESS)) {
                mRefreshLayout.autoRefresh();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
            mEmptyLayout = null;
            OkGo.getInstance().cancelTag(tag);
            mExpandableListView = null;
            mRefreshLayout = null;
            mAdapter = null;
            mapDelete.clear();
            mapDelete = null;
            map.clear();
            map = null;
            mSubjectsListBeanList.clear();
            mSubjectsListBeanList = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
