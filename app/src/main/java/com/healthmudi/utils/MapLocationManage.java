package com.healthmudi.utils;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.healthmudi.base.Constant;
import com.orhanobut.hawk.Hawk;

/**
 * Author: dengdayi
 * Date: 2016/11/29 14:26
 * Description:地图定位管理类
 * 导入BaiduBS_Android.jar
 */
public class MapLocationManage {
    private LocationClient mLocationClient = null;
    /**
     * 发起定位请求的间隔,默认为1000
     * 可选，系统默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
     */
    public int spanValue = 10 * 1000;

    private Context mContext;

    private static MapLocationManage instance;

    /**
     * 单列获取实例
     *
     * @return
     */
    public static MapLocationManage getInstance(Context context) {
        if (instance == null) {
            synchronized (MapLocationManage.class) {
                if (instance == null) {
                    instance = new MapLocationManage(context);
                }
            }
        }
        return instance;
    }


    private MapLocationManage(Context mContext) {
        this.mContext = mContext;
        mLocationClient = new LocationClient(mContext);
        initLocation();
        mLocationClient.registerLocationListener(locationListener);
    }

    /**
     * 初始化定位的一些配置参数，
     * 注：有些配置不需要，根据实际情况在去掉
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(spanValue);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
//        option.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
//        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 设置定位间隔时间，
     *
     * @param spanValue：秒为单位
     */
    public void setSpanValue(int spanValue) {
        this.spanValue = spanValue * 1000;
    }

    /**
     * 启动定位
     */
    public void startLocation() {
        //启动定位
        if (mLocationClient != null) {
            mLocationClient.start();
        }
    }

    /**
     * 请求定位
     */
    public void requestLocation() {
        //启动定位
        if (mLocationClient != null) {
            mLocationClient.requestLocation();
        }
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient = null;//释放LocationClient内存
        }
    }

    /**
     * 定位监听
     */
    private BDLocationListener locationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            GpsManager manager = GpsManager.getInstance(mContext);
//            Log.e("TAG", "bdLocation==== " + bdLocation);
            //LocType  ::   BDLocation.TypeServerError  :服务端网络定位失败
            //LocType  ::   BDLocation.TypeGpsLocation  :GPS定位结果
            //LocType  ::   BDLocation.TypeNetWorkLocation  :网络定位结果
            //LocType  ::   BDLocation.TypeOffLineLocation  :离线定位结果
            //LocType  ::   BDLocation.TypeServerError  :服务端网络定位失败
            //LocType  ::   BDLocation.TypeNetWorkException  :网络不同导致定位失败，请检查网络是否通畅
            //LocType  ::   BDLocation.TypeCriteriaException  :无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机
            if (bdLocation != null && bdLocation.getLocType() != BDLocation.TypeServerError) {
                if (bdLocation.getLocType() == BDLocation.TypeServerCheckKeyError
                        || bdLocation.getLocType() == BDLocation.TypeServerDecryptError
                        || bdLocation.getLocType() == BDLocation.TypeNetWorkException
                        || bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                    manager.setLatitude(GpsManager.DEFAULT_VALUE);
                    manager.setLongitude(GpsManager.DEFAULT_VALUE);
                    Hawk.put(Constant.KEY_LONGITUDE, String.valueOf(GpsManager.DEFAULT_VALUE));
                    Hawk.put(Constant.KEY_LATITUDE, String.valueOf(GpsManager.DEFAULT_VALUE));
                } else {
                    manager.setBdLocation(bdLocation);
                    manager.setLatitude(bdLocation.getLatitude());
                    manager.setLongitude(bdLocation.getLongitude());

                    Hawk.put(Constant.KEY_LONGITUDE, String.valueOf(bdLocation.getLongitude()));
                    Hawk.put(Constant.KEY_LATITUDE, String.valueOf(bdLocation.getLatitude()));

                    if (manager.isUploadLocation()) {
                        manager.updateLocation(bdLocation.getLongitude(), bdLocation.getLatitude());
                    }
                }
            } else {
                manager.setLatitude(GpsManager.DEFAULT_VALUE);
                manager.setLongitude(GpsManager.DEFAULT_VALUE);
                Hawk.put(Constant.KEY_LONGITUDE, String.valueOf(GpsManager.DEFAULT_VALUE));
                Hawk.put(Constant.KEY_LATITUDE, String.valueOf(GpsManager.DEFAULT_VALUE));
            }
        }
    };
}
