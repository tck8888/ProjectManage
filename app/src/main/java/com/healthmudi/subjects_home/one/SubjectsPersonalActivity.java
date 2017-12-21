package com.healthmudi.subjects_home.one;

import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.subjects_home.home_fragment.adapter.SubjectsPersonalListAdapter;
import com.healthmudi.view.EmptyView;
import com.healthmudi.view.custom_popupwindow.EasyPopup;
import com.healthmudi.view.custom_popupwindow.HorizontalGravity;
import com.healthmudi.view.custom_popupwindow.VerticalGravity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * decription:受试者入组列表
 * Created by tck on 2017/12/9.
 */

public class SubjectsPersonalActivity extends BaseActivity implements View.OnClickListener {

    private EasyPopup mPopup;
    private ImageView mIvaddSubjects;
    private SmartRefreshLayout mRefreshLayout;
    private ListView mListView;
    private EmptyView mEmptyLayout;

    private List<SubjectsPersonalListBean> mSubjectsPersonalListBeanList = new ArrayList<>();
    private SubjectsPersonalListAdapter mAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_subjects_personal;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();
        initPop();
        mIvaddSubjects = (ImageView) findViewById(R.id.iv_add_subjects);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        mEmptyLayout = (EmptyView) findViewById(R.id.empty_layout);
        mListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new SubjectsPersonalListAdapter(this, mSubjectsPersonalListBeanList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
        super.setListener();
        mIvaddSubjects.setOnClickListener(this);
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubjectsPersonalListBean subjectsPersonalListBean = mSubjectsPersonalListBeanList.get(position);
                if (subjectsPersonalListBean.type.equals("top")) {
                    openActivity(EntryGroupBasicInformationActivity.class);
                } else if (subjectsPersonalListBean.type.equals("center")) {
                    openActivity(RegularVisitsActivity.class);
                }
            }
        });
    }

    public void openActivity(Class clazz) {
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
            case R.id.iv_add_subjects:
                showPop(v);
                break;
            //计划外访式
            case R.id.fl_unplanned_interview:
                Intent intent = new Intent(this, PlannedInterviewActivity.class);
                startActivity(intent);
                break;
            //研究结束访视
            case R.id.fl_research_end_visit:
                Intent intent1 = new Intent(this, ResearchEndVisitActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void showPop(View view) {
        mPopup.showAtAnchorView(view, VerticalGravity.BELOW, HorizontalGravity.LEFT, dp2px(40), dp2px(5));
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
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
