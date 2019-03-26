package com.shouzhan.design.datasource.http.loan;

import com.shouzhan.design.datasource.http.RequestHandler;
import com.shouzhan.design.datasource.http.RequestType;
import com.shouzhan.design.utils.HttpConstants;
import com.shouzhan.design.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author danbin
 * @version LoanRequestHandler.java, v 0.1 2019-03-25 下午4:49 danbin
 * TODO 明确验签位置
 */
public class LoanRequestHandler implements RequestHandler {

    @Override
    public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
        String method = request.method();
        if (StringUtils.equals(method, RequestType.POST.name())) {
            return rebuildPostRequest(request);
        }
        return request;
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

    private Request rebuildPostRequest(Request request) {
        RequestBody oldRequestBody = request.body();
        assert oldRequestBody != null;
        try {
            String prettyFormat = Utils.toPrettyFormat(Utils.bodyToString(oldRequestBody));
            JSONObject jsonObject;
            if (StringUtils.isEmpty(prettyFormat)) {
                jsonObject = new JSONObject();
            } else {
                jsonObject = new JSONObject(prettyFormat);
            }
            JSONObject params = new JSONObject();
            params.put(HttpConstants.RQ_APP_ID, HttpConstants.APP_ID);
            String method = request.url().toString().replace(HttpConstants.JAVA_LOAN_HOST, "");
            params.put(HttpConstants.RQ_METHOD, method);
            params.put(HttpConstants.RQ_VERSION, "1.0.0");
            params.put(HttpConstants.RQ_SIGN, "");
            params.put(HttpConstants.RQ_CONTENT, jsonObject);
            return request.newBuilder()
                    .url(HttpConstants.JAVA_LOAN_HOST + "app/gateway")
                    .post(RequestBody.create(oldRequestBody.contentType(), params.toString().replace("\\/", "/")))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

}
