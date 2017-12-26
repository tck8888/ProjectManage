package com.healthmudi.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmudi.R;

/**
 * Created by tck
 * Date: 2017/12/16 11：52
 */

public abstract class BaseDialogFragment extends DialogFragment {

    private ImageView ivArrowLeftBlack;
    public TextView tvTitle;
    public ImageView ivCircularExclamationMark;
    public ImageView ivCheckMark;
    private ViewStub viewStub;

    private View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments != null) {
            initData(arguments);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
    }

    /**
     * 3.Fargment创建本身的视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.common_dialog_fragment_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivArrowLeftBlack = (ImageView) view.findViewById(R.id.iv_arrow_left_black);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ivCircularExclamationMark = (ImageView) view.findViewById(R.id.iv_circular_exclamation_mark);
        ivCheckMark = (ImageView) view.findViewById(R.id.iv_check_mark);
        viewStub = (ViewStub) view.findViewById(R.id.view_stub);
        viewStub.setLayoutResource(getLayoutId());
        if (viewStub != null) {
            viewStub.inflate();
        }
        initView(view);
        setViewData(view);
        setListener(view);
    }


    protected abstract void initData(@Nullable Bundle arguments);

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    public void setViewData(View view) {

    }

    public void setListener(View view) {
        ivArrowLeftBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
