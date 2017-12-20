package com.healthmudi.net;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 回调的抽象基类
 * Created by tck
 * Date: 2017/12/20 12：55
 */

public abstract class HttpCallBack<T> {

    /**
     * Java中所有类型的公共高级接口。
     * 包括:
     * 原始类型(对应Class)、
     * 参数化类型(对应ParameterizedType)、
     * 数组类型(对应GenericArrayType)、
     * 类型变量(对应TypeVariable)、
     * 基本类型(对应Class)
     */
    private Type mGenericSuperclass;

    public HttpCallBack() {
        Type genericSuperclass = getClass().getGenericSuperclass();//获得带有泛型的父类
        if (genericSuperclass instanceof ParameterizedType) {//参数化类型，即泛型
            //得到泛型的第一个参数T的类型，赋值给当前泛型类型的成员变量genericSuperclass
            mGenericSuperclass = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            mGenericSuperclass = Object.class;
        }
    }

    /**
     * 用来解析接口返回的json数据，在子类中需要重写它来进行具体解析
     * @param t
     */
    public abstract void onResolve(T t);

    /**
     * 错误回调
     * @param code
     * @param message
     */
    public abstract void onFailed(int code, String message);

    public Type getType() {
        return mGenericSuperclass;
    }
}
