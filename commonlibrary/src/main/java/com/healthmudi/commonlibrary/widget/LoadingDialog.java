package com.healthmudi.commonlibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.healthmudi.commonlibrary.R;


/**
 * Created by tck on 2017/6/24.
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.progress_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        setCanceledOnTouchOutside(false);
    }

}
