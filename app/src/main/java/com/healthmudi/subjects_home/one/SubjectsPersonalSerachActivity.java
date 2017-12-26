package com.healthmudi.subjects_home.one;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.view.EmptyView;
import com.healthmudi.view.LoadingDialog;
import com.lzy.okgo.OkGo;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashMap;
import java.util.Map;

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
    private ListView mListView;

    private Map<String, String> map = new HashMap<>();
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
        mListView = (ListView) findViewById(R.id.list_view);
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
                        serach(keyword);
                    } else {
                        Toast.makeText(SubjectsPersonalSerachActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }
        });
    }

    private void serach(String keyword) {
        map.put("keyword", keyword);
        LoadingDialog.getInstance(this).show();
        HttpRequest.getInstance().post(HttpUrlList.PROJECT_SUBJECT_SEARCH_URL, map, tag, new OnServerCallBack() {
            @Override
            public void onSuccess(Object result) {
                LoadingDialog.getInstance(SubjectsPersonalSerachActivity.this).hidden();
            }

            @Override
            public void onFailure(int code, String mesage) {
                LoadingDialog.getInstance(SubjectsPersonalSerachActivity.this).hidden();
                if (!TextUtils.isEmpty(mesage)) {
                    Toast.makeText(SubjectsPersonalSerachActivity.this, mesage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle_search:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
