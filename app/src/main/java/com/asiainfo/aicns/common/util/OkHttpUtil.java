package com.asiainfo.aicns.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.asiainfo.aicns.common.api.Api;
import com.asiainfo.aicns.common.constant.Constant;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by uuom on 16-10-28.
 */
public class OkHttpUtil {

    private OkHttpClient mOkHttpClient;
    private Context context;

    public OkHttpUtil(Context context) {
        this.context = context;
    }

    public void init(){
        if (mOkHttpClient == null){
            mOkHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {}

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookieList = new ArrayList<Cookie>();
                            if (!url.toString().contains(Api.LOGIN_URL)){
                                Log.d("OkHttpUtil","为请求添加coockie");
                                SharedPreferences spf = context.getSharedPreferences(Constant.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
                                cookieList.add(new Cookie.Builder()
                                        .name("JSESSIONID")
                                        .value(spf.getString(Constant.SHARED_PREFERENCES_NAME_JSESSIONID,""))
                                        .domain("NetXpert")
                                        .build());
                            }

                            return cookieList;
                        }
                    }).build();
        }
    }


    public void get(String url, Callback callback){
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        Log.d("OkHttpUtil", "request url: " + url);
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    public void post(String url, Callback callback, String... params){


        //TODO
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(callback);
    }
}
