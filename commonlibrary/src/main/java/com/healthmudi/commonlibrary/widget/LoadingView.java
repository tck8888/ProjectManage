package com.healthmudi.commonlibrary.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import com.healthmudi.commonlibrary.R;


/**
 * Created by tck on 17/2/16.
 */
public class LoadingView extends FrameLayout {

    private View mLoadingIndicator;

    public LoadingView(Context context) {
        super(context);
        initView(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void startAnimating() {
        setVisibility(VISIBLE);
        mLoadingIndicator.setVisibility(VISIBLE);
        mLoadingIndicator.clearAnimation();
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5F,  Animation.RELATIVE_TO_SELF, 0.5F);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setFillAfter(true);
        mLoadingIndicator.startAnimation(animation);
    }

    public void stopAnimating() {
        mLoadingIndicator.clearAnimation();
        mLoadingIndicator.setVisibility(GONE);
        setVisibility(GONE);
    }

    private void initView(Context context) {
        inflate(context, R.layout.view_loading, this);
        setBackgroundResource(R.color.black_overlay);
        mLoadingIndicator = findViewById(R.id.indicator);
        setClickable(true);
    }
}
