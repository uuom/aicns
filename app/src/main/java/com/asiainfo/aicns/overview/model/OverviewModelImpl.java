package com.asiainfo.aicns.overview.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asiainfo.aicns.App;
import com.asiainfo.aicns.bean.OverviewBean;
import com.asiainfo.aicns.common.api.Api;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by yangxp5 on 2016/11/22.
 */
public class OverviewModelImpl implements  OverviewModel {

    @Override
    public Observable<OverviewBean> getOverviewData() {
        return Observable.create(new Observable.OnSubscribe<OverviewBean>() {
            @Override
            public void call(final Subscriber<? super OverviewBean> subscriber) {
                String url = Api.REQUEST_BASE_URL + Api.GET_DEV_AND_LINK_PERCENT;
                App.getOkHttpUtil().get(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try{
                            OverviewBean ob = JSON.parseObject(response.body().string(), OverviewBean.class);
                            subscriber.onNext(ob);
                            subscriber.onCompleted();
                        }catch (Exception e){
                            subscriber.onError(e);
                        }
                    }
                });
            }
        });
    }
}
