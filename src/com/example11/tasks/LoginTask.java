package com.example11.tasks;

import android.os.AsyncTask;
import com.example11.utils.Contant;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chendi on 2016/6/1.
 */
public class LoginTask extends AsyncTask<Integer,Integer,String>{

    String url = Contant.URL_LOGIN;
    HttpPost httpPost;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        httpPost = new HttpPost(url);
    }

    @Override
    protected String doInBackground(Integer... params) {

        String result= null;

        List<NameValuePair> items = new ArrayList<NameValuePair>();
        items.add(new BasicNameValuePair("username", "test"));
        items.add(new BasicNameValuePair("password", "test"));

        HttpResponse httpResponse = null;
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(items, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                 result = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

}
