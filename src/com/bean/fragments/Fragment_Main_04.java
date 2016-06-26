package com.bean.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.bean.yihuanton.R;
import com.bean.adapters.AnimAdapterUtil;
import com.bean.yihuanton.WebViewActivity;
import com.bean.utils.Contant;
import com.bean.utils.ToastUtil;
public class Fragment_Main_04 extends Fragment {

    ProgressBar pb;
    private WebView webView;
    private WebSettings webSettings;

    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录

    boolean blockLoadingNetworkImage = false;

    String url = Contant.URL_ACTION;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_04, null);
        initView(view);
        return view;
    }

    public void initView(View view) {
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

            Intent intent = new Intent();
            intent.putExtra("url", url);
            intent.setClass(getActivity(), WebViewActivity.class);
            startActivity(intent);
            AnimAdapterUtil.anim_translate_next(getActivity());

            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            ToastUtil.showToast(getActivity(), "网页加载错误！");
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
}
