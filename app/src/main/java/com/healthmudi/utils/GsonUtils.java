package com.healthmudi.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by tck
 * Date: 2017/12/28 11：14
 */

public class GsonUtils {

    public  static Gson buildGson() {
        Gson  gson = new GsonBuilder()
                .registerTypeAdapter(String.class, new StringTypeAdapter())
                .create();
        return gson;
    }

}
