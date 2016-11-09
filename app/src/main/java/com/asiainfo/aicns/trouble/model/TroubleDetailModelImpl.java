package com.asiainfo.aicns.trouble.model;

import com.alibaba.fastjson.JSON;
import com.asiainfo.aicns.App;
import com.asiainfo.aicns.bean.TroubleMoreDetailBean;
import com.asiainfo.aicns.common.api.Api;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by uuom on 16-11-8.
 */
public class TroubleDetailModelImpl implements TroubleDetailModel {

    @Override
    public Observable<TroubleMoreDetailBean> getTroubleDetail(final String faultId) {
        return Observable.create(new Observable.OnSubscribe<TroubleMoreDetailBean>() {
            @Override
            public void call(final Subscriber<? super TroubleMoreDetailBean>subscriber) {
                String url = Api.REQUEST_BASE_URL + Api.GET_TROUBLE_DETAIL_URL + "&faultId="+faultId;
                App.getOkHttpUtil().get(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String json = response.body().string();
                            JSONObject jo = new JSONObject(json);
                            int code = jo.getInt("code");
                            if (code == 1){
                                TroubleMoreDetailBean tmdb = JSON.parseObject(jo.getJSONObject("data").toString(), TroubleMoreDetailBean.class);
                                subscriber.onNext(tmdb);
                            }
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                    }
                });
            }
        });
    }
}
