package com.healthmudi.subjects_home.one;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.bean.SubjectsListBean;
import com.healthmudi.entity.HttpResult;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.one.adapter.KeyWordAdapter;
import com.healthmudi.subjects_home.one.adapter.SubjectsPersonalSerachListAdapter;
import com.healthmudi.utils.ListUtil;
import com.healthmudi.view.EmptyView;
import com.healthmudi.view.LoadingDialog;
import com.lzy.okgo.OkGo;
import com.orhanobut.hawk.Hawk;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * decription:
 * Created by tck on 2017/12/10.
 */

public class SubjectsPersonalSerachActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtSearchKeyword;
    private TextView mTvCancleSearch;
    private TagFlowLayout mTagFlowlayout;
    private TextView mTvEmptyView;
    private EmptyView mEmptyLayout;
    private ExpandableListView mExpandableListView;

    private List<SubjectsListBean> mSubjectsListBeanList = new ArrayList<>();
    private SubjectsPersonalSerachListAdapter mAdapter;
    private KeyWordAdapter mTagAdapter;

    private Map<String, String> map = new HashMap<>();
    private List<String> mKeyWords = new ArrayList<>();

    private String tag = "SubjectsPersonalSerachActivity";


    @Override
    public int getLayoutId() {
        return R.layout.activity_subjects_personal_search;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            ProjectListBean projectListBean = (ProjectListBean) getIntent().getSerializableExtra(Constant.KEY_PROJECT_LIST_BEAN);
            map.put("project_id", String.valueOf(projectListBean.getProject_id()));
            List<String> strings = (List<String>) Hawk.get(Constant.KEY_SUBJECT_KEYWORD);
            if (!ListUtil.isEmpty(strings)) {
                mKeyWords.addAll(strings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        super.initView();
        mEtSearchKeyword = (EditText) findViewById(R.id.et_search_keyword);
        mTvCancleSearch = (TextView) findViewById(R.id.tv_cancle_search);
        mTagFlowlayout = (TagFlowLayout) findViewById(R.id.tag_flowlayout);
        mTvEmptyView = (TextView) findViewById(R.id.tv_empty_view);
        mEmptyLayout = (EmptyView) findViewById(R.id.empty_layout);
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        mAdapter = new SubjectsPersonalSerachListAdapter(this, mSubjectsListBeanList);
        mExpandableListView.setAdapter(mAdapter);
        mTagAdapter = new KeyWordAdapter(mKeyWords, this);
        mTagFlowlayout.setAdapter(mTagAdapter);

        mEmptyLayout.showEmptyView();
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (ListUtil.isEmpty(mKeyWords)) {
            mTvEmptyView.setVisibility(View.VISIBLE);
        } else {
            mTvEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setListener() {
        super.setListener();
        mTvCancleSearch.setOnClickListener(this);
        mEtSearchKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String keyword = mEtSearchKeyword.getText().toString().trim();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(keyword)) {
                        // 先隐藏键盘
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(SubjectsPersonalSerachActivity.this
                                                .getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                        serach(keyword, false);
                    } else {
                        Toast.makeText(SubjectsPersonalSerachActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });


        mTagFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String s = mKeyWords.get(position);
                serach(s, true);
                return true;
            }
        });


        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SubjectsListBean.SubjectsBean subjectsBean = mSubjectsListBeanList.get(groupPosition).getSubjects().get(childPosition);
                Intent intent = new Intent(SubjectsPersonalSerachActivity.this, SubjectsPersonalActivity.class);
                intent.putExtra(Constant.KEY_SUBJECTS_BEAN, subjectsBean);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                return false;
            }
        });
    }

    /**
     * decription:搜索
     */
    private void serach(final String keyword, final boolean statu) {
        map.put("keyword", keyword);

        LoadingDialog.getInstance(this).show();
        HttpRequest.getInstance().get(HttpUrlList.PROJECT_SUBJECT_SEARCH_URL, map, tag, new OnServerCallBack<HttpResult<List<SubjectsListBean>>, List<SubjectsListBean>>() {
            @Override
            public void onSuccess(List<SubjectsListBean> result) {
                LoadingDialog.getInstance(SubjectsPersonalSerachActivity.this).hidden();
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
                saveKeyWord(keyword, statu);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(SubjectsPersonalSerachActivity.this).hidden();
                if (ListUtil.isEmpty(mSubjectsListBeanList)) {
                    mEmptyLayout.showEmptyView();
                } else {
                    mEmptyLayout.showContentView();
                }
                Toast.makeText(SubjectsPersonalSerachActivity.this, "稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Set<String> set = new HashSet<>();

    /**
     * decription:保存关键字
     */
    private void saveKeyWord(String keyword, boolean statu) {
        if (!statu) {
            set.add(keyword);
            set.addAll(mKeyWords);
            mKeyWords.clear();
            mKeyWords.addAll(set);
            set.clear();
            Hawk.put(Constant.KEY_SUBJECT_KEYWORD, mKeyWords);
            mTagAdapter.notifyDataChanged();
            mTvEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle_search:
                activityFinish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            OkGo.getInstance().cancelTag(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
