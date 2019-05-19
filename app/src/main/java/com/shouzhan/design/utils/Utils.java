package com.shouzhan.design.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okio.Buffer;

/**
 * @author danbin
 * @version Utils.java, v 0.1 2019-03-26 上午12:30 danbin
 */
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    /**
     * 获取常规POST请求参数
     *
     * @param body
     */
    public static String bodyToString(RequestBody body) {
        Buffer buffer = new Buffer();
        try {
            body.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.readUtf8();
    }

    /**
     * 格式化JSONString
     */
    public static String toPrettyFormat(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String prettyJson = gson.toJson(json);
        prettyJson = prettyJson
                .replace("%2C", ",");
        return "{}".equals(prettyJson) ? "" : prettyJson;
    }

    /**
     * Int数组转换成String数组
     *
     * @param intArray
     * @return
     */
    public static List<String> intTransformStringArray(int[] intArray) {
        if (intArray == null) {
            return null;
        }
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < intArray.length; i++) {
            stringList.add(String.valueOf(intArray[i]));
        }
        return stringList;
    }


}

