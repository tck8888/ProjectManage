package com.healthmudi.net;

/**
 * decription:
 * Created by tck on 2017/12/19.
 */

public interface CallBack {

    void onSuccess(String msg);

    void onError(int code);
}
