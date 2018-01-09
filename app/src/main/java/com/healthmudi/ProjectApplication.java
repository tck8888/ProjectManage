package com.healthmudi;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

import com.healthmudi.service.LocationService;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.orhanobut.hawk.Hawk;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by tck
 * Date: 2017/12/18 15：03
 */

public class ProjectApplication extends Application {

    public static Context mContext;

    public LocationService locationService;
    public Vibrator mVibrator;


    @Override
    public void onCreate() {
        super.onCreate();

        okgoInit();
        mContext = this;

        Hawk.init(this).build();

        initMap();
    }

    private void initMap() {

        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);

    }

    private void okgoInit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("com.healthmudi");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        OkGo.getInstance()
                .init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);
    }

}
