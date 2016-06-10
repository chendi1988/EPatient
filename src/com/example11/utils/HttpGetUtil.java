package com.example11.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * Created by chendi on 2016/6/3.
 */
public class HttpGetUtil {
    static HttpGet httpGet;

    public static String getGetJsonResult(String url) {
        String result = null;
        String err = "";

        httpGet = new HttpGet(url);

        HttpClient client = new DefaultHttpClient();

        // 请求超时
        client.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        // 读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                5000);
        // 取得HTTP response
        HttpResponse httpResponse = null;
        try {

            httpResponse = client.execute(httpGet);


            // 若状态码为200 ok
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 取出回应字串
                result = EntityUtils.toString(httpResponse.getEntity());

            } else {
                err = httpResponse.getStatusLine().getStatusCode()
                        + "";
            }
        } catch (Exception e) {
            err = e.getMessage();
        }

        return result;
    }
}
