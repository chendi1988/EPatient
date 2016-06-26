package com.bean.utils;

import org.apache.http.client.methods.HttpGet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * Created by chendi on 2016/6/3.
 */
public class HttpGetUtil {
    static HttpGet httpGet;

    public static String getGetJsonResult(String url,String param) {
        String result = null;
        String err = "";

        try {
            StringBuilder buf = new StringBuilder(url);
            buf.append("?");
            buf.append("param="+ URLEncoder.encode(param.toString(),"UTF-8").toString());
            URL url1 = new URL(buf.toString());
            HttpURLConnection conn = (HttpURLConnection)url1.openConnection();
            conn.setRequestMethod("GET");
            if(conn.getResponseCode()==200){
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


//        httpGet = new HttpGet(url);
//
//        HttpClient client = new DefaultHttpClient();
//
//        // 请求超时
//        client.getParams().setParameter(
//                CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
//        // 读取超时
//        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
//                5000);
////         取得HTTP response
//        HttpResponse httpResponse = null;
//        try {
//
//            httpResponse = client.execute(httpGet);
//
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

        return result;
    }
}
