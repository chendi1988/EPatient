package com.example11.myapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example11.adapters.AnimAdapterUtil;

/**
 * Created by chendi on 2016/6/11.
 */
public class WebViewActivity extends Activity{

    ProgressBar pb;
    private WebView webView;
    private WebSettings webSettings;

    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录

    boolean blockLoadingNetworkImage = false;

    String url= "http://www.baidu.com";

    TextView titlemsg;

    Button go_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);

        titlemsg = (TextView) findViewById(R.id.titlemsg);
        titlemsg.setText("Title" + getIntent().getIntExtra("pos",0));

        initView();
    }

    public void initView(){

        go_back = (Button) findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pb = (ProgressBar) findViewById(R.id.progress);
        webView = (WebView) findViewById(R.id.web);

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        webView.getSettings().setBlockNetworkImage(true);
        blockLoadingNetworkImage = true;

        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置缓存模式
        // 开启DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        // 开启database storage API功能
        webSettings.setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath()
                + APP_CACHE_DIRNAME;
        Log.i("cachePath", cacheDirPath);
        // 设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath); // API 19 deprecated
        // 设置Application caches缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        // 开启Application Cache功能
        webSettings.setAppCacheEnabled(true);

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());

        webView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            webView.getSettings().setBlockNetworkImage(true);
            blockLoadingNetworkImage = true;

            if (!pb.isShown()) {
                pb.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (pb.isShown()) {
                pb.setVisibility(View.GONE);
            }

            if (blockLoadingNetworkImage) {
                webView.getSettings().setBlockNetworkImage(false);
                blockLoadingNetworkImage = false;
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //    return super.shouldOverrideUrlLoading(view, url);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            //  Toast.makeText(this,"网页加载错误！",0).show();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            pb.setProgress(newProgress);
            if (newProgress >= 100 && pb.isShown()) {
                pb.setVisibility(View.GONE);

                if (blockLoadingNetworkImage) {
                    webView.getSettings().setBlockNetworkImage(false);
                    blockLoadingNetworkImage = false;
                }
            }

        }
    }


    @Override
    public void onBackPressed() {

            if (webView.canGoBack()) {
                webView.goBack();
            }else{
                super.onBackPressed();
                AnimAdapterUtil.anim_translate_back(this);
            }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.destroy();
    }
}
