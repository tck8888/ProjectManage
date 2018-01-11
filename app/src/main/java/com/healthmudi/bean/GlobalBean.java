package com.healthmudi.bean;

/**
 * Created by tck
 * Date: 2018/01/11 18：38
 */

public class GlobalBean {

    private static volatile GlobalBean instance;

    private GlobalBean() {
    }

    public static GlobalBean getInstance() {
        if (instance == null) {
            synchronized (GlobalBean.class) {
                if (instance == null) {
                    instance = new GlobalBean();
                }
            }
        }
        return instance;
    }


}
