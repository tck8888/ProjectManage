package com.healthmudi.subjects_home.home_fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmudi.R;
import com.healthmudi.base.BaseFragment1;
import com.healthmudi.base.Constant;
import com.healthmudi.base.HttpUrlList;
import com.healthmudi.bean.MessageEvent;
import com.healthmudi.bean.ProjectListBean;
import com.healthmudi.bean.PunchClockSelectLocationListBean;
import com.healthmudi.net.HttpRequest;
import com.healthmudi.net.OnServerCallBack;
import com.healthmudi.subjects_home.three.PunchClockSelectLocationActivity;
import com.healthmudi.subjects_home.three.SignHistoryActivity;
import com.healthmudi.utils.CommonUtils;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.GpsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

/**
 * decription:签到
 * Created by tck on 2017/12/9.
 */

public class SignFragment extends BaseFragment1 implements View.OnClickListener {

    private String mProject_id;
    private TextView mTvSignProjectName;
    private TextView mTvSignDate;
    private ImageView mIvSignHeadPortrait;
    private TextView mTvSignName;
    private View mLlSignNormal;
    private View mRlOpenGps;
    private TextView mTvSignHospitalName;
    private TextView mTvSignHospitalLocation;
    private TextView mTvPunchTheClockState;
    private TextView mTvTvPunchTheClockTime;
    private ImageView mIvSignBottomBg;
    private RelativeLayout mFlClockIn;

    private TextView mTvArriveTime;
    private TextView mTvArriveLocation;
    private LinearLayout mLlLayout1;
    private TextView mTvLeaveTime;
    private TextView mTvLeaveAddress;
    private View mLine1;
    private ImageView mIvLeave;


    private Map<String, String> map = new HashMap<>();
    private ProjectListBean mProjectListBean;
    private String tag = "SignFragment";
    private String type = "0";//0签到打卡 1离开打卡

    public static SignFragment newInstance(ProjectListBean projectListBean) {
        SignFragment signFragment = new SignFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_PROJECT_LIST_BEAN, projectListBean);
        signFragment.setArguments(bundle);
        return signFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign;
    }

    @Override
    protected void initData(@Nullable Bundle arguments) {
        mProjectListBean = (ProjectListBean) arguments.getSerializable(Constant.KEY_PROJECT_LIST_BEAN);
        mProject_id = String.valueOf(mProjectListBean.getProject_id());
        map.put("project_id", mProject_id);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initView(@Nullable View view) {

        mTvSignProjectName = (TextView) view.findViewById(R.id.tv_sign_project_name);
        mTvSignDate = (TextView) view.findViewById(R.id.tv_sign_date);
        mIvSignHeadPortrait = (ImageView) view.findViewById(R.id.iv_sign_head_portrait);
        mTvSignName = (TextView) view.findViewById(R.id.tv_sign_name);
        mTvSignHospitalName = (TextView) view.findViewById(R.id.tv_sign_hospital_name);
        mTvSignHospitalLocation = (TextView) view.findViewById(R.id.tv_sign_hospital_location);
        mTvPunchTheClockState = (TextView) view.findViewById(R.id.tv_punch_the_clock_state);
        mTvTvPunchTheClockTime = (TextView) view.findViewById(R.id.tv_tv_punch_the_clock_time);
        mFlClockIn = (RelativeLayout) view.findViewById(R.id.fl_clock_in);

        mIvSignBottomBg = (ImageView) view.findViewById(R.id.iv_sign_bottom_bg);
        mIvSignBottomBg.setImageBitmap(drawableToBitmap(R.mipmap.icon_sign_bottom_bg, 181));

        mRlOpenGps = view.findViewById(R.id.rl_open_gps);
        mLlSignNormal = view.findViewById(R.id.ll_sign_normal);

        mTvArriveTime = (TextView) view.findViewById(R.id.tv_arrive_time);
        mTvArriveLocation = (TextView) view.findViewById(R.id.tv_arrive_location);
        mLlLayout1 = (LinearLayout) view.findViewById(R.id.ll_layout1);
        mTvLeaveTime = (TextView) view.findViewById(R.id.tv_leave_time);
        mTvLeaveAddress = (TextView) view.findViewById(R.id.tv_leave_address);
        mLine1 = (View) view.findViewById(R.id.line1);
        mIvLeave = (ImageView) view.findViewById(R.id.iv_leave);


        //判断是否要打开GPS
        showNormalView(GpsUtils.isOpenGPS(getContext()));

    }


    public void showNormalView(boolean state) {
        if (state) {
            mLlSignNormal.setVisibility(View.VISIBLE);
            mRlOpenGps.setVisibility(View.GONE);
        } else {
            mLlSignNormal.setVisibility(View.GONE);
            mRlOpenGps.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setListener(@Nullable View view) {
        super.setListener(view);
        view.findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
        view.findViewById(R.id.iv_sign_history).setOnClickListener(this);
        view.findViewById(R.id.fl_select_location).setOnClickListener(this);
        view.findViewById(R.id.btn_open_gps).setOnClickListener(this);
        mFlClockIn.setOnClickListener(this);
    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mProjectListBean != null) {
            mTvSignProjectName.setText(mProjectListBean.getProject_name());
            if (mProjectListBean.getArrive_time() == 0) {//是否为签到打卡，还是离开打卡
                type = "0";
                mTvArriveTime.setText("到达打卡");
                mLlLayout1.setVisibility(View.GONE);
                mIvLeave.setVisibility(View.GONE);
                mLine1.setVisibility(View.GONE);
            } else {
                type = "1";
                mTvArriveTime.setText("到达打卡时间   " + DateUtils.getFormatTime4(mProjectListBean.getArrive_time()));
                mTvArriveLocation.setText(mProjectListBean.getArrive_site_name());
                mTvArriveLocation.setText(mProjectListBean.getArrive_site_name());
                if (mProjectListBean.getLeave_time() == 0) {
                    mLlLayout1.setVisibility(View.GONE);
                    mIvLeave.setVisibility(View.GONE);
                    mLine1.setVisibility(View.GONE);
                } else {
                    mLlLayout1.setVisibility(View.VISIBLE);
                    mIvLeave.setVisibility(View.VISIBLE);
                    mLine1.setVisibility(View.VISIBLE);
                    mTvLeaveTime.setText("离开打卡时间   " + DateUtils.getFormatTime4(mProjectListBean.getLeave_time()));
                    mTvLeaveAddress.setText(mProjectListBean.getLeave_site_name());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                activityFinish();
                break;
            case R.id.iv_sign_history:
                openActivity(SignHistoryActivity.class);
                break;
            case R.id.fl_select_location:
                openActivity(PunchClockSelectLocationActivity.class);
                break;
            //打卡
            case R.id.fl_clock_in:
                clockIn();
                break;
            //打开gps
            case R.id.btn_open_gps:
                GpsUtils.startLocationSettings(getActivity(), Constant.RQ_ACTION_LOCATION_SETTINGS);
                break;
        }
    }

    private void clockIn() {
        HttpRequest.getInstance().post(HttpUrlList.PROJECT_CLOCK_IN_URL, map, tag, new OnServerCallBack() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onFailure(int code, String mesage) {

            }
        });
    }

    public void openActivity(Class clzz) {
        if (clzz != null) {
            Intent intent = new Intent(getContext(), clzz);
            intent.putExtra(Constant.KEY_PROJECT_ID, mProject_id);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackInfo(MessageEvent event) {
        if (event != null) {
            if (event.getTag().equals(MessageEvent.KEY_LOCATION_SETTINGS)) {
                showNormalView(true);
            } else if (event.getTag().equals(MessageEvent.KEY_LOCATION_PERMISSIONS)) {
                if ((Boolean) event.getData()) {//权限申请成功

                } else {

                }
            } else if (event.getTag().equals(MessageEvent.KEY_PUNCH_CLOCK_SELECT_LOCATION_SUCCESS)) {//选择打卡地址
                PunchClockSelectLocationListBean data = (PunchClockSelectLocationListBean) event.getData();
                map.put("site_id", String.valueOf(data.getSite_id()));
                mTvSignHospitalName.setText(data.getSite_name());
                mTvSignHospitalLocation.setText(data.getSite_affectiveDistance() + "米范围内打卡");

                mTvPunchTheClockState.setText("需要进入打卡范围才能打卡噢~");
            }
        }
    }

    /**
     * 修改图片的尺寸
     *
     * @param iconId
     * @return
     */
    public Bitmap drawableToBitmap(@DrawableRes int iconId, int size) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeResource(getResources(), iconId);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            //设置想要的大小
            int newWidth = getResources().getDisplayMetrics().widthPixels;//屏幕的宽度
            int newHeight = CommonUtils.dipToPx(getContext(), size);
            //计算压缩的比率
            float scaleWidth = ((float) newWidth) / width;
            //获取想要缩放的matrix
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleWidth);
            //获取新的bitmap
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
