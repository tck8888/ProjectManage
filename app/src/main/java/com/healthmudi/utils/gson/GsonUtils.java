package com.healthmudi.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tck
 * Date: 2017/12/28 11：14
 */

public class GsonUtils {

    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .registerTypeAdapter(String.class, new StringTypeAdapter())
                .registerTypeAdapter(Long.class, new LongTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())
                .registerTypeAdapter(Double.class, new DoubleTypeAdapter())
                .create();
    }


    public static Gson getGson() {
        return GSON;
    }

    public static <T> List<T> jsonToArray(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(json).getAsJsonArray();
        for (JsonElement jsonElement : jsonArray) {
            list.add(GSON.fromJson(jsonElement, cls));
        }
        return list;
    }


}
