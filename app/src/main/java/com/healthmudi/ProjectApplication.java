package com.healthmudi;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * Created by tck
 * Date: 2017/12/18 15：03
 */

public class ProjectApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
    }
}
