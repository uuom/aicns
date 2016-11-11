package com.asiainfo.aicns.trouble.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.asiainfo.aicns.R;
import com.asiainfo.aicns.trouble.event.TroubleLevelChangeEvent;
import com.asiainfo.aicns.trouble.presenter.TroubleChartPresenter;
import com.asiainfo.aicns.trouble.presenter.TroubleChartPresenterImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;


public class TroubleChartFragment extends Fragment implements TroubleChartView{

    private static final int OBJ_START_LOAD_PAGE = 0;
    private static final int OBJ_PAGE_LOAD_FINISHED = 1;

    WebView mWebView;
    ContentLoadingProgressBar progress;


    private Integer troubleLevel;
    private boolean initFinished = false;

    private TroubleChartPresenter troubleChartPresenter;

    public static TroubleChartFragment newInstance(Integer troubleLevel){
        TroubleChartFragment troubleChartFragment = new TroubleChartFragment();
        Bundle args = new Bundle();
        args.putSerializable("troubleLevel", troubleLevel);
        troubleChartFragment.setArguments(args);
        return troubleChartFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Serializable troubleLevel = this.getArguments().getSerializable("troubleLevel");
        if (troubleLevel != null){
            this.troubleLevel = (Integer) troubleLevel;
        }
        EventBus.getDefault().register(this);
        troubleChartPresenter = new TroubleChartPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trouble_chart, container, false);
        mWebView = (WebView) view.findViewById(R.id.webView);
        progress = (ContentLoadingProgressBar) view.findViewById(R.id.progress);

        setWebView();
        mWebView.loadUrl("file:///android_asset/troubleChart.html");
        while (initFinished){
            troubleChartPresenter.refreshChart(troubleLevel);
        }

        return view;
    }


    private void setWebView() {

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
                super.onPageStarted(webView, url, bitmap);
                Message msg = new Message();
                msg.what = OBJ_START_LOAD_PAGE;
                handler.sendMessage(msg);
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);

                if (url.contains("troubleChart.html")){
                    initFinished = true;    //加载页面完成
                }

                Message msg = new Message();
                msg.what = OBJ_PAGE_LOAD_FINISHED;
                handler.sendMessage(msg);
            }
        });

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case OBJ_START_LOAD_PAGE:
                    mWebView.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
                    break;
                case OBJ_PAGE_LOAD_FINISHED:
                    mWebView.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                    break;
            }
        }
    };


    @Override
    public void hideProgress() {
        mWebView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    int width;
    int height;

    @Override
    public void setData2WebView(String jsonData) {

        WindowManager wm = this.getActivity().getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        mWebView.loadUrl("javascript:createChart(" + width*0.38 + ",'"+ jsonData +"');");
    }

    @Override
    public void showProgress() {
        mWebView.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //event
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTroubleLevelChangeEvent(TroubleLevelChangeEvent event){
        this.troubleLevel = event.troubleLevel;
        troubleChartPresenter.refreshChart(event.troubleLevel);
    }
}
