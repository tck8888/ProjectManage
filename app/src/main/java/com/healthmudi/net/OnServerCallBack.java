package com.healthmudi.net;

import com.healthmudi.entity.HttpResult;

/**
 * T外层对象
 * V内层对象
 * Created by tck
 * Date: 2017/12/20 13：03
 */
public abstract class OnServerCallBack<T, V> extends HttpCallBack<T> {

    @Override
    public void onResolve(T t) {
        if (t instanceof HttpResult) {
            HttpResult<V> callbackData = (HttpResult) t;
            V result = callbackData.getResult();
            if (callbackData.isSuccess()) {
                onSuccess(result);
            } else {
                onFailed(callbackData.getCode(), callbackData.getMessge());
            }
        } else {
            onSuccess((V) t);
        }
    }

    @Override
    public void onFailed(int code, String mesage) {
        onFailure(code, mesage);
    }

    public abstract void onSuccess(V result);

    public abstract void onFailure(int code, String mesage);


}
