package com.shouzhan.design.datasource.http;

import com.shouzhan.design.utils.NewPrefs;
import com.shouzhan.design.utils.PrefConstants;

import java.io.BufferedReader;
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
public class CommonRequestHandler implements RequestHandler {

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
        try {
            Charset charset = Charset.forName("UTF-8");
            ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
            InputStreamReader jsonReader = new InputStreamReader(responseBody.byteStream(), charset);
            BufferedReader reader = new BufferedReader(jsonReader);
            StringBuilder sbJson = new StringBuilder();
            String line = reader.readLine();
            do {
                sbJson.append(line);
                line = reader.readLine();
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String generationSign() {
        return null;
    }

}
