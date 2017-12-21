package com.healthmudi.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import com.healthmudi.R;


public class LoadingDialog extends Dialog {
    private static LoadingDialog instance;
    private Context context;

    public LoadingDialog(Context context) {
        super(context, R.style.ProgressHUD);
        this.context = context;
        this.setCancelable(false);
    }

    public static LoadingDialog getInstance(Context context) {
        if (instance == null) {
            instance = new LoadingDialog(context);
        }
        return instance;
    }
    @Override
    public void show() {
        if (context == null) {
            return;
        }
        if (!((Activity) context).isFinishing()) {
            super.show();
        } else {
            instance = null;
        }
    }


    public void hidden() {
        if (context == null) {
            return;
        }
        Activity activity = ((Activity) context);
        if (!activity.isFinishing() && instance != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (instance.isShowing()) {
                        instance.dismiss();
                    }
                    instance = null;
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setOnKeyListener(keyListener);
    }

    OnKeyListener keyListener = new OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
            if (arg1 == KeyEvent.KEYCODE_BACK) {
                return true;
            }
            return false;
        }
    };
}
