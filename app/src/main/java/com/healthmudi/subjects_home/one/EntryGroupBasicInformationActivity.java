package com.healthmudi.subjects_home.one;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.SubjectsPersonalListBean;
import com.healthmudi.subjects_home.one.fragment.EntryGroupBasicInformationDetailFragment;
import com.healthmudi.subjects_home.one.fragment.EntryGroupBasicInformationUpdateFragment;

/**
 * decription:入组基本信息
 * Created by tck on 2017/12/10.
 */

public class EntryGroupBasicInformationActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mIvCheckMark;


    private SubjectsPersonalListBean mSubjectsPersonalListBean;

    private EntryGroupBasicInformationUpdateFragment mEntryGroupBasicInformationUpdateFragment;
    private EntryGroupBasicInformationDetailFragment mEntryGroupBasicInformationDetailFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_entry_group_basic_information;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            mSubjectsPersonalListBean = (SubjectsPersonalListBean) getIntent().getSerializableExtra(Constant.KEY_SUBJECTS_PERSONAL_LIST_BEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initView() {
        super.initView();
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvCheckMark = (ImageView) findViewById(R.id.iv_check_mark);
        ImageView image = (ImageView) findViewById(R.id.iv_arrow_left_black);
        image.setImageResource(R.mipmap.arrow_left_black);
        mTvTitle.setText("入组基本信息");
    }

    @Override
    public void setViewData() {
        super.setViewData();
       /* if (mProjectListBean != null) {*/
            mIvCheckMark.setVisibility(View.VISIBLE);
            mIvCheckMark.setOnClickListener(this);

            mEntryGroupBasicInformationUpdateFragment = EntryGroupBasicInformationUpdateFragment.newInstance(mSubjectsPersonalListBean);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container, mEntryGroupBasicInformationUpdateFragment, "EntryGroupBasicInformationUpdateFragment")
                    .commit();
       /* } else {
            mEntryGroupBasicInformationDetailFragment = EntryGroupBasicInformationDetailFragment.newInstance(mSubjectsBean, mSubjectsPersonalListBean);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container, mEntryGroupBasicInformationDetailFragment, "EntryGroupBasicInformationDetailFragment")
                    .commit();
            mIvCheckMark.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        findViewById(R.id.iv_circular_exclamation_mark).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        hideSoftKeyBord();
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                if (mEntryGroupBasicInformationUpdateFragment != null) {
                    mEntryGroupBasicInformationUpdateFragment.onKeyDownBack();
                } else {
                    activityFinish();
                }
                break;
            //注意事项
            case R.id.iv_circular_exclamation_mark:
                Intent intent = new Intent(this, PlannedInterviewMattersNeedingAttentionActivity.class);
                intent.putExtra(Constant.KEY_INFOMATION, MessageEvent.KEY_ENTRY_GROUP_BASIC_INFORMATION_ADD_SUCCESS);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            //提交数据
            case R.id.iv_check_mark:
                if (mEntryGroupBasicInformationUpdateFragment != null) {
                    mEntryGroupBasicInformationUpdateFragment.submitData();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mTvTitle = null;
            mIvCheckMark = null;
            mSubjectsPersonalListBean = null;
            mEntryGroupBasicInformationUpdateFragment = null;
            mEntryGroupBasicInformationDetailFragment = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 捕获返回键的方法1
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mEntryGroupBasicInformationUpdateFragment != null && keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            mEntryGroupBasicInformationUpdateFragment.onKeyDownBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
