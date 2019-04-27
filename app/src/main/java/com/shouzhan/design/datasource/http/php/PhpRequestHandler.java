package com.shouzhan.design.datasource.http.php;

import android.text.TextUtils;

import com.shouzhan.design.datasource.http.RequestHandler;
import com.shouzhan.design.datasource.http.RequestType;
import com.shouzhan.design.utils.HttpConstants;
import com.shouzhan.design.utils.MD5Utils;
import com.shouzhan.design.utils.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import me.jessyan.autosize.utils.LogUtils;
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
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        ResponseBody responseBody = null;
        try {
            Charset charset = Charset.forName("UTF-8");
            responseBody = response.peekBody(Long.MAX_VALUE);
            inputStreamReader = new InputStreamReader(responseBody.byteStream(), charset);
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            String line = bufferedReader.readLine();
            do {
                builder.append(line);
                line = bufferedReader.readLine();
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (responseBody != null) {
                    responseBody.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
