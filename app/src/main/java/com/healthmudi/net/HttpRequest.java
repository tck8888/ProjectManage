package com.healthmudi.net;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.Map;

/**
 * decription:
 * Created by tck on 2017/12/19.
 */

public class HttpRequest {


    public void get(String url, Map<String, String> parameter, String tag, final CallBack callBack) {

        OkGo.<String>get(url)
                .tag(tag)
                .params(parameter)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callBack.onError(response.code());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        callBack.onSuccess(response.body());
                    }
                });
    }
}
