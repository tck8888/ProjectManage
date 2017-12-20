package com.healthmudi.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tck
 * Date: 2017/12/20 10：32
 */

public class HttpResult<T> implements Serializable {

    @SerializedName("code")
    private int code;
    @SerializedName("messge")
    private String messge;
    @SerializedName("result")
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return this.code == SUCCESS;
    }

    private static final int SUCCESS = 0;
    private static final int JSON_RESOLVE_ERROR = -101;
}
