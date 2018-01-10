package com.healthmudi.utils;

import android.content.Context;

import com.baidu.location.BDLocation;

/**
 * Created by dengdayi
 * Date: 2016/10/31 15：41
 * Gps管理类
 */

public class GpsManager {
    private static GpsManager instance = null;
    /**
     * 是否上传经纬度
     */
    private boolean isUploadLocation;

    private BDLocation mBdLocation;
    //private NearbyPresenter mNearbyPresenter;

    private double longitude = DEFAULT_VALUE, latitude = DEFAULT_VALUE;
    //经纬度获取错误时的数据
    public static final double DEFAULT_VALUE = 0;

    /**
     * 单列获取实例
     *
     * @return
     */
    public static GpsManager getInstance(Context context) {
        if (instance == null) {
            synchronized (GpsManager.class) {
                if (instance == null) {
                    instance = new GpsManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param ctx
     */
    private GpsManager(Context ctx) {
        //mNearbyPresenter = new NearbyPresenter(ctx);
    }

    /**
     * 上传经纬度
     */
    public void updateLocation(double longitude, double latitude) {
        // 上传 经纬度
        if (longitude == DEFAULT_VALUE && latitude == DEFAULT_VALUE) {
            return;
        }
       /* mNearbyPresenter.locationUpdate(longitude, latitude, new ResponseCallBack<String>() {
            @Override
            public void onDataSuccess(int code, String message, String data) {
                super.onDataSuccess(code, message, data);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                setUploadLocation(false);
            }
        });*/
    }

    public boolean isUploadLocation() {
        return isUploadLocation;
    }

    public void setUploadLocation(boolean uploadLocation) {
        isUploadLocation = uploadLocation;
    }

    public BDLocation getBdLocation() {
        return mBdLocation;
    }

    public void setBdLocation(BDLocation mBdLocation) {
        this.mBdLocation = mBdLocation;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
