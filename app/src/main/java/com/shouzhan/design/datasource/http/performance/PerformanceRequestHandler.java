package com.shouzhan.design.datasource.http.performance;

import com.shouzhan.design.datasource.http.RequestHandler;
import com.shouzhan.design.utils.NewPrefs;
import com.shouzhan.design.utils.PrefConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author danbin
 * @version CommonRequestHandler.java, v 0.1 2019-03-25 下午4:49 danbin
 */
public class PerformanceRequestHandler implements RequestHandler {

    @Override
    public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        String accessToken = NewPrefs.getStringPreference(PrefConstants.ACCESS_TOKEN, "");
        builder.header("accessToken", accessToken);
        Request newRequest = builder.method(original.method(), original.body())
                .build();
        return newRequest;
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


}
