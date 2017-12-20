package com.healthmudi;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.healthmudi.base.BaseActivity;
import com.healthmudi.base.Constant;
import com.healthmudi.bean.WebViewBean;


public class ProjectWebViewActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private WebView mWebView;
    private WebViewBean mWebViewBean;


    @Override
    public int getLayoutId() {
        return R.layout.activity_project_web_view_layout;
    }

    @Override
    public void initData() {
        super.initData();
        try {
            mWebViewBean = (WebViewBean) getIntent().getSerializableExtra(Constant.KEY_WEBVIEW_BEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        super.initView();
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mWebView = (WebView) findViewById(R.id.web_view);
        WebSettings mWebSettings = mWebView.getSettings();
        //设置自适应屏幕，两者合用
        mWebSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
        mWebSettings.setBuiltInZoomControls(true);//设置内置的缩放控件。若为false，则该WebView不可缩放
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存

    }

    @Override
    public void setViewData() {
        super.setViewData();
        if (mWebViewBean != null) {
            mTvTitle.setText(mWebViewBean.getTitle());
            mWebView.loadUrl(mWebViewBean.getUrl());
            mWebView.setWebViewClient(new WebViewClient() {
                //复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
                //该方法传回了错误码，根据错误类型可以进行不同的错误分类处理
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            });
        }


    }

    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.iv_arrow_left_black).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left_black:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }


}
