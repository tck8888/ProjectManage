package com.healthmudi.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;

/**
 * Created by tck
 * Date: 2017/12/20 14：30
 */

public class EmptyView extends FrameLayout {
    /**
     * 空布局
     */
    private View emptyView;
    private ImageView mIvEmptyIcon;
    private TextView mTvEmptyData;

    public EmptyView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        emptyView = inflater.inflate(R.layout.loading_empty, this, false);
        mIvEmptyIcon = (ImageView) emptyView.findViewById(R.id.iv_empty_icon);
        mTvEmptyData = (TextView) emptyView.findViewById(R.id.tv_empty_data);

        addView(emptyView);

        emptyView.setVisibility(View.GONE);
    }

    /**
     * 显示空布局
     */
    public void showEmptyView() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.setVisibility(View.GONE);
        }
        emptyView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示主布局
     */
    public void showContentView() {
        removeView(emptyView);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setEmptyIcon(@DrawableRes int iconId) {
        if (mIvEmptyIcon != null) {
            mIvEmptyIcon.setImageResource(iconId);
        }
    }

    public void setEmptyText(@Nullable String msg) {
        if (mTvEmptyData != null) {
            if (!TextUtils.isEmpty(msg)) {
                mTvEmptyData.setText(msg);
            }
        }
    }
}
