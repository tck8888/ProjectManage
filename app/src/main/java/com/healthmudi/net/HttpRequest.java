package com.healthmudi.net;

import com.google.gson.Gson;
import com.healthmudi.base.Constant;
import com.healthmudi.utils.DateUtils;
import com.healthmudi.utils.gson.GsonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.orhanobut.hawk.Hawk;

import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

/**
 * decription:
 * Created by tck on 2017/12/19.
 */

public class HttpRequest {

    private static Gson mGson;

    private static volatile HttpRequest instance;

    private HttpRequest() {
    }

    public static HttpRequest getInstance() {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                if (instance == null) {
                    instance = new HttpRequest();
                }
            }
        }
        return instance;
    }

    public void get(String url, Map<String, String> parameter, String tag, final OnServerCallBack callBack) {
        if (callBack == null) {
            callBack.onFailed(-102, "没有初始化回调接口");
            return;
        }
        if (mGson == null) {
            mGson = GsonUtils.getGson();
        }

        TreeMap<String, String> map = operateParameter(parameter);

        OkGo.<String>get(Hawk.get(Constant.KEY_BASE_URL) + url)
                .tag(tag)
                .params(map)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callBack.onFailed(response.code(), response.message());
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            callBack.onResolve(mGson.fromJson(response.body(), callBack.getType()));
                        } catch (Exception e) {
                            callBack.onFailed(-101, e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 操作参数
     *
     * @param parameter
     * @return
     */
    private TreeMap<String, String> operateParameter(Map<String, String> parameter) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        String token = (String) Hawk.get(Constant.KEY_TOKEN);
        treeMap.put("token", token);
        treeMap.put("lng", (String)Hawk.get(Constant.KEY_LONGITUDE));
        treeMap.put("lat", (String)Hawk.get(Constant.KEY_LATITUDE));
        treeMap.put("version_code", "1.0");
        treeMap.put("timestamp", DateUtils.getCurrentTimeStr());
        treeMap.put("sign", "sign");
        treeMap.put("uuid", "uuid");
        treeMap.put("system", "android");

        if (parameter != null) {
            treeMap.putAll(parameter);
        }
        return treeMap;
    }

    public void post(String url, Map<String, String> parameter, String tag, final OnServerCallBack callBack) {
        if (callBack == null) {
            callBack.onFailed(-102, "没有初始化回调接口");
            return;
        }
        if (mGson == null) {
            mGson = GsonUtils.getGson();
        }

        TreeMap<String, String> map = operateParameter(parameter);

        JSONObject jsonObject = new JSONObject(map);
        OkGo.<String>post(Hawk.get(Constant.KEY_BASE_URL) + url)
                .tag(tag)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callBack.onFailed(response.code(), response.message());
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            callBack.onResolve(mGson.fromJson(response.body(), callBack.getType()));
                        } catch (Exception e) {
                            callBack.onFailed(-101, e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
    }

}
