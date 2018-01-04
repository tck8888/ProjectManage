package com.healthmudi.subjects_home.one;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.home_fragment.adapter.SubjectsPersonalListAdapter;
import com.healthmudi.subjects_home.one.dialog.GroupBasicInformationDialog;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.EmptyView;
import com.healthmudi.view.custom_popupwindow.EasyPopup;
import com.healthmudi.view.custom_popupwindow.HorizontalGravity;
import com.healthmudi.view.custom_popupwindow.VerticalGravity;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * decription:受试者入组列表
 * Created by tck on 2017/12/9.
 */

public class SubjectsPersonalListActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {

    private TextView mTvTitle;
    private ImageView mIvaddSubjects;
    private SmartRefreshLayout mRefreshLayout;
    private ListView mListView;
    private EmptyView mEmptyLayout;

    private EasyPopup mPopup;

    private List<SubjectsPersonalListBean> mSubjectsPersonalListBeanList = new ArrayList<>();
    private SubjectsPersonalListAdapter mAdapter;
    private SubjectsListBean.SubjectsBean mSubjectsBean;
    private Map<String, String> map = new HashMap<>();

    private String tag = "SubjectsPersonalListActivity";


    @Override
    public int getLayoutId() {
        return R.layout.activity_subjects_personal;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            mSubjectsBean = (SubjectsListBean.SubjectsBean) getIntent().getSerializableExtra(Constant.KEY_SUBJECTS_BEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("project_id", String.valueOf(mSubjectsBean.getProject_id()));
        map.put("subject_id", String.valueOf(mSubjectsBean.getSubject_id()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initView() {
        super.initView();
        initPop();


        mTvTitle = (TextView) findViewById(R.id.tv_title);

        mIvaddSubjects = (ImageView) findViewById(R.id.iv_add_subjects);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        mEmptyLayout = (EmptyView) findViewById(R.id.empty_layout);
        mListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new SubjectsPersonalListAdapter(this, mSubjectsPersonalListBeanList);
        mListView.setAdapter(mAdapter);
    }

    private void initPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.subjects_personal_selector, null);

        view.findViewById(R.id.fl_unplanned_interview).setOnClickListener(this);
        view.findViewById(R.id.fl_research_end_visit).setOnClickListener(this);
        mPopup = new EasyPopup(this)
                .setContentView(view)
                .setAnimationStyle(R.style.QQPopAnim)
                .setFocusAndOutsideEnable(true)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0)
                .createPopup();

    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mSubjectsBean != null) {
            //结束时间与结束理由不为空，代表研究结束
            if (mSubjectsBean.getEnd_date() != null
                    && !TextUtils.isEmpty(mSubjectsBean.getEnd_date())
                    && mSubjectsBean.getEnd_reason() != null
                    && !TextUtils.isEmpty(mSubjectsBean.getEnd_reason())) {
                mIvaddSubjects.setVisibility(View.GONE);
            } else {
                mIvaddSubjects.setVisibility(View.VISIBLE);
                mIvaddSubjects.setOnClickListener(this);
                mTvTitle.setText(mSubjectsBean.getSubject_code() + "  (" + mSubjectsBean.getName_py() + ")");
            }
        }
    }

    @Override
    public void setListener() {
        super.setListener();

        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubjectsPersonalListBean subjectsPersonalListBean = mSubjectsPersonalListBeanList.get(position);
                openDialog(subjectsPersonalListBean);
            }
        });
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.autoRefresh();
    }

    public void openDialog(SubjectsPersonalListBean subjectsPersonalListBean) {

        switch (subjectsPersonalListBean.getVisit_type()) {
            case 1:
                GroupBasicInformationDialog.newInstance(subjectsPersonalListBean, mSubjectsBean)
                        .show(getSupportFragmentManager(), "GroupBasicInformationDialog");
                break;
            case 2:
                openActivity(RegularVisitsActivity.class,mSubjectsBean,subjectsPersonalListBean);
                break;
            case 3:
                break;
            case 4:
                break;
        }

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getData();
    }

    private void getData() {
        HttpRequest.getInstance().get(HttpUrlList.PROJECT_VISIT_LIST_URL, map, tag, new OnServerCallBack<HttpResult<List<SubjectsPersonalListBean>>, List<SubjectsPersonalListBean>>() {
            @Override
            public void onSuccess(List<SubjectsPersonalListBean> result) {
                updateUI(result);
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }


            @Override
            public void onFailure(int code, String mesage) {
                if (ListUtil.isEmpty(mSubjectsPersonalListBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }
                mRefreshLayout.finishRefresh();
            }
        });
    }

    /**
     * decription:更新UI
     */
    private void updateUI(List<SubjectsPersonalListBean> result) {


        if (!ListUtil.isEmpty(mSubjectsPersonalListBeanList)) {
            mSubjectsPersonalListBeanList.clear();
        }
        mSubjectsPersonalListBeanList.addAll(result);
        //判断是都显示空界面
        if (ListUtil.isEmpty(mSubjectsPersonalListBeanList)) {
            mEmptyLayout.showEmptyView();
        } else {
            mEmptyLayout.showContentView();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.iv_add_subjects:
                showPop(v);
                break;
            //计划外访式
            case R.id.fl_unplanned_interview:
                openActivity(PlannedInterviewActivity.class,mSubjectsBean,null);
                break;
            //研究结束访视
            case R.id.fl_research_end_visit:
                openActivity(ResearchEndVisitActivity.class,mSubjectsBean,null);
                break;
        }
    }

    private void showPop(View view) {
        mPopup.showAtAnchorView(view, VerticalGravity.BELOW, HorizontalGravity.LEFT, DensityUtil.dp2px(40), DensityUtil.dp2px(5));
    }


    public void openActivity(Class clazz, SubjectsListBean.SubjectsBean subjectsBean, SubjectsPersonalListBean subjectsPersonalListBean) {
        mPopup.dismiss();
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            if (subjectsBean != null) {
                intent.putExtra(Constant.KEY_SUBJECTS_BEAN, subjectsBean);
            }
            if (subjectsPersonalListBean != null) {
                intent.putExtra(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN, subjectsPersonalListBean);
            }
            startActivity(intent);
            // 进入动画
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackInfo(MessageEvent event) {
        if (event != null) {
            if (event.getTag().equals(MessageEvent.KEY_RESEARCH_END_VISIT_SUCCESS)
                    || event.getTag().equals(MessageEvent.KEY_PLANNED_INTERVIEW_SUCCESS)) {
                mRefreshLayout.autoRefresh();
            }
            //访试结束回调隐藏添加按钮
            if (event.getTag().equals(MessageEvent.KEY_RESEARCH_END_VISIT_SUCCESS)) {
                mIvaddSubjects.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
            OkGo.getInstance().cancelTag(tag);
            mPopup.dismiss();
            mPopup = null;
            mIvaddSubjects = null;
            mRefreshLayout = null;
            mListView = null;
            mEmptyLayout = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
