package com.healthmudi.entity;

import java.io.Serializable;

/**
 * Created by tck
 * Date: 2017/11/29 12：58
 */

public class CommonItemBean<T> implements Serializable {

    public T data;

    public CommonItemBean() {
    }

    public CommonItemBean(T data) {
        this.data = data;
    }

}

