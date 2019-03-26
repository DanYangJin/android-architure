package com.shouzhan.design.datasource.http.php;

import android.text.TextUtils;

import com.shouzhan.design.datasource.http.RequestHandler;
import com.shouzhan.design.datasource.http.RequestType;
import com.shouzhan.design.utils.HttpConstants;
import com.shouzhan.design.utils.MD5Utils;
import com.shouzhan.design.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import me.jessyan.autosize.utils.LogUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author danbin
 * @version PhpRequestHandler.java, v 0.1 2019-03-25 下午4:49 danbin
 */
public class PhpRequestHandler implements RequestHandler {

    @Override
    public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
        String method = request.method();
        Request newRequest;
        if (RequestType.POST.name().equals(method)) {
            newRequest = rebuildPostRequest(request);
        } else if (RequestType.GET.name().equals(method)) {
            newRequest = rebuildGetRequest(request);
        } else {
            newRequest = request;
        }
        return newRequest.newBuilder()
                .header(HttpConstants.HEADER_CONTENT_TYPE, HttpConstants.STRING_CONTENT_TYPE)
                .header(HttpConstants.HEADER_ACCEPT_CODING, HttpConstants.ACCEPT_CODING)
                .header(HttpConstants.HEADER_ACCESS_TOKEN, "")
                .method(newRequest.method(), newRequest.body())
                .build();
    }

    @Override
    public Response onAfterRequest(Response response, Interceptor.Chain chain) {
        try {
            Charset charset = Charset.forName("UTF-8");
            ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
            InputStreamReader jsonReader = new InputStreamReader(responseBody.byteStream(), charset);
            BufferedReader reader = new BufferedReader(jsonReader);
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            do {
                builder.append(line);
                line = reader.readLine();
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 对post请求添加统一参数
     */
    private Request rebuildPostRequest(Request request) {
        RequestBody oldRequestBody = request.body();
        assert oldRequestBody != null;
        try {
            JSONObject jsonObject;
            String prettyFormat = Utils.toPrettyFormat(Utils.bodyToString(oldRequestBody));
            if (TextUtils.isEmpty(prettyFormat)) {
                jsonObject = new JSONObject();
            } else {
                jsonObject = new JSONObject(prettyFormat);
            }
            jsonObject.put(HttpConstants.RQ_SIGN, getSign(jsonObject));
            return request.newBuilder()
                    .post(RequestBody.create(oldRequestBody.contentType(), jsonObject.toString().replace("\\/", "/")))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    /**
     * 对get请求做统一参数处理
     */
    private Request rebuildGetRequest(Request request) {
        String url = request.url().toString();
        JSONObject jsonObject = new JSONObject();
        int lastIndex = url.lastIndexOf("?");
        if (lastIndex != -1) {
            String[] paramList = url.substring(lastIndex + 1, url.length()).split("&");
            for (String s : paramList) {
                String[] params = s.split("=");
                try {
                    if (Utils.isNumeric(params[1])) {
                        jsonObject.put(params[0], Integer.parseInt(params[1]));
                    } else {
                        jsonObject.put(params[0], params[1]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        LogUtils.e("rebuildGetRequest: " + jsonObject.toString());
        HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter(HttpConstants.RQ_SIGN, getSign(jsonObject))
                .build();
        return request.newBuilder().url(httpUrl).build();
    }


    /**
     * 获取请求验签
     */
    public String getSign(JSONObject jsonObject) {
        StringBuilder builder = new StringBuilder();
        builder.append(Utils.toPrettyFormat(jsonObject.toString()))
                .append("352de3dd930f4f7c6e9eaf8a4365cd36")
                .append(HttpConstants.KEY);
        LogUtils.e("common_before_sign:" + builder.toString());
        return MD5Utils.getStringMD5(builder.toString());
    }


}
