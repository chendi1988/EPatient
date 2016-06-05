package com.example11.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example11.utils.Contant.COMPANYID;

/**
 * Created by chendi on 2016/6/3.
 */
public class HttpPostUtil {
    static HttpPost httpRequest;

    public static String getPostJsonResult(String url, Map<String, String> map, String action) {
        String result = null;
        String err = "";

        httpRequest = new HttpPost(url);
        //            JSONObject jsObject = new JSONObject();
//            jsObject.put("CompanyID", COMPANYID);
//            jsObject.put("action", action);
//
//            for (Map.Entry<String, Object> entity : map.entrySet()) {
//                jsObject.put(entity.getKey(), entity.getValue());
//            }
//
//            // 发出HTTP request
//            httpRequest
//                    .setEntity(new StringEntity(jsObject.toString(), "UTF-8"));

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();

        paramList.add(new BasicNameValuePair("CompanyID", COMPANYID));
        paramList.add(new BasicNameValuePair("action", action));

        for (Map.Entry<String, String> entity : map.entrySet()) {
            paramList.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
        }

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

            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
                    paramList);

            httpRequest.setEntity(requestHttpEntity);

            httpResponse = client.execute(httpRequest);

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
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (result == null) {
            resultMap.put("result", "null");
            resultMap.put("err", err);
        } else {
            resultMap.put("result", result);
        }

        return result;
    }
}
