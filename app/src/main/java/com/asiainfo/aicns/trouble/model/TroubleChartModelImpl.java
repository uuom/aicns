package com.asiainfo.aicns.trouble.model;

import com.asiainfo.aicns.App;
import com.asiainfo.aicns.common.api.Api;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by uuom on 16-11-2.
 */
public class TroubleChartModelImpl implements TroubleChartModel {


    @Override
    public Observable<String> getProvinceTroubleData(final Integer troubleLevel) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                String url = "";
                if (troubleLevel == null){
                    url = Api.REQUEST_BASE_URL + Api.GET_TROUBLE_CHART_DATA_URL;
                }else{
                    url = Api.REQUEST_BASE_URL + Api.GET_TROUBLE_CHART_DATA_URL + "&troubleLevel="+troubleLevel;
                }
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
                                String data = jo.getJSONArray("data").toString();
                                subscriber.onNext(data);
                                subscriber.onCompleted();
                            }
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        });
    }
}
