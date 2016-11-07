package com.asiainfo.aicns.trouble.model;

import com.asiainfo.aicns.App;
import com.asiainfo.aicns.bean.TroubleBean;
import com.asiainfo.aicns.common.api.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by uuom on 16-10-31.
 */
public class TroubleModelImpl implements TroubleModel {

    @Override
    public Observable<List<TroubleBean>> getTroubleData() {

        Observable<List<TroubleBean>> result = Observable.create(new Observable.OnSubscribe<List<TroubleBean>>(){

            @Override
            public void call(final Subscriber<? super List<TroubleBean>> subscriber) {
                App.getOkHttpUtil().get(Api.REQUEST_BASE_URL + Api.GET_TROUBLE_COUNT_URL, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String resp = response.body().string();
                        List<TroubleBean> dataList = new ArrayList<TroubleBean>();
                        try {
                            JSONObject jsonObject = new JSONObject(resp);

                            int totalCount = jsonObject.getInt("totalCount");
                            dataList.add(new TroubleBean("所有告警",null,totalCount+"","#757575"));

                            JSONArray tsList = jsonObject.getJSONArray("tsList");
                            List<TroubleBean> tempList = new ArrayList<TroubleBean>();
                            for (int i = 0; i < tsList.length(); i++) {
                                JSONObject tsJsonObject = tsList.getJSONObject(i);
                                tempList.add(new TroubleBean(tsJsonObject.getString("zhname"),tsJsonObject.getInt("code")+"",tsJsonObject.getInt("count")+"",tsJsonObject.getString("color")));
                            }
                            Collections.reverse(tempList);
                            dataList.addAll(tempList);
                            subscriber.onNext(dataList);
                            subscriber.onCompleted();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                    }
                });
            }
        });

        return result;
    }
}
