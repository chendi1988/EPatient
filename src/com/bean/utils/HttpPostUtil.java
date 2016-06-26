package com.bean.utils;

import org.apache.http.client.methods.HttpPost;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Map;

/**
 * Created by chendi on 2016/6/3.
 */
public class HttpPostUtil {
    static HttpPost httpRequest;

    public static String getPostJsonResult(String url, Map<String, String> map, String action) {
        String result = null;
        String err = "";
        String token = "";

        StringBuffer param = new StringBuffer();
        for (Map.Entry<String, String> entity : map.entrySet()) {

            if (entity.getKey().equals("token")) {
                token = entity.getValue();
            } else {
                param.append("\"");
                param.append(entity.getKey());
                param.append("\"");
                param.append(":");
                param.append("\"");
                param.append(entity.getValue());
                param.append("\"");
                param.append(",");
            }
        }

        try {

            String item = "";

            if (param.toString().length() != 0) {
                item = "{\"main\":{" + param.toString().substring(0, param.length() - 1) + "}" + ",\"extend\":{\"version\":\"1.0\",\"terminal\":\"1\",\"token\":\"" + token + "\"}}";
            } else {
                item = "{\"main\":{" + "}" + ",\"extend\":{\"version\":\"1.0\",\"terminal\":\"1\",\"token\":" + token + "\"}}";
            }


            StringBuilder buf = new StringBuilder(url);
            buf.append("?");
            buf.append("param=" + URLEncoder.encode(item.toString(), "UTF-8").toString());
            URL url1 = new URL(buf.toString());
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();

                // 创建字节输出流对象
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    os.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                os.close();
                // 返回字符串
                result = new String(os.toByteArray());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        String token = "";
//
//        StringBuffer param = new StringBuffer();
//        for (Map.Entry<String, String> entity : map.entrySet()) {
//
//            if (entity.getKey().equals("token")) {
//                token = entity.getValue();
//            } else {
//                param.append("\"");
//                param.append(entity.getKey());
//                param.append("\"");
//                param.append(":");
//                param.append("\"");
//                param.append(entity.getValue());
//                param.append("\"");
//                param.append(",");
//            }
//        }
//
//        if (param != null && param.toString().length() > 2) {
//            param.deleteCharAt(param.length() - 1);
//        }
//
//        // 使用GET方法发送请求,需要把参数加在URL后面，用？连接，参数之间用&分隔
//        String baseURL = url + "?param={\"main\":{" + param + "}" + ",\"extend\":{\"version\":\"1.0\",\"terminal\":\"1\",\"token\":" + token + "\"}}";
//
//        // 生成请求对象
//        HttpGet httpGet = new HttpGet(baseURL);
//
//        HttpClient client = new DefaultHttpClient();
//
//        // 请求超时
//        client.getParams().setParameter(
//                CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
//        // 读取超时
//        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
//                5000);
//        // 取得HTTP response
//        HttpResponse httpResponse = null;
//        try {
//
//
//            httpResponse = client.execute(httpGet);
//
//            // 若状态码为200 ok
//            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                // 取出回应字串
//                result = EntityUtils.toString(httpResponse.getEntity());
//
//            } else {
//                err = httpResponse.getStatusLine().getStatusCode()
//                        + "";
//            }
//        } catch (Exception e) {
//            err = e.getMessage();
//        }
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        if (result == null) {
//            resultMap.put("result", "null");
//            resultMap.put("err", err);
//        } else {
//            resultMap.put("result", result);
//        }

        return result;
    }
}
