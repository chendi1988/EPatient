package com.example11.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example11.base.BackHandledFragment;
import com.example11.myapp.R;

/**
 * Created by chendi on 2016/5/22.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WebViewFragment extends BackHandledFragment {

    ProgressBar pb;
    private WebView webView;
    private WebSettings webSettings;

    private String url = "http://123.206.62.86/articleList.aspx?id=426";
    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录

    boolean blockLoadingNetworkImage = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_webview, null);
        pb = (ProgressBar) view.findViewById(R.id.progress);
        webView = (WebView) view.findViewById(R.id.web);

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
        String cacheDirPath = getActivity().getFilesDir().getAbsolutePath()
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

        return view;
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
    public boolean onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
            Log.v("webView.goBack()", "webView.goBack()");
            return true;

        } else {
            Log.v("Conversatio退出", "Conversatio退出");
            return false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webView.removeAllViews();
        webView.destroy();
    }
}