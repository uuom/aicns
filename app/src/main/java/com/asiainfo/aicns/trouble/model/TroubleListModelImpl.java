package com.asiainfo.aicns.trouble.model;

import com.alibaba.fastjson.JSON;
import com.asiainfo.aicns.App;
import com.asiainfo.aicns.bean.TroubleDetailBean;
import com.asiainfo.aicns.common.api.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by uuom on 16-11-3.
 */
public class TroubleListModelImpl implements TroubleListModel {

    @Override
    public Observable<List<TroubleDetailBean>> getTroubleListData(final Integer troubleType, final int page) {

        return Observable.create(new Observable.OnSubscribe<List<TroubleDetailBean>>() {
            @Override
            public void call(final Subscriber<? super List<TroubleDetailBean>> subscriber) {
                String url = Api.REQUEST_BASE_URL + Api.GET_TROUBLE_DETAIL_LIST_URL + "&page="+page;
                if (troubleType != null){
                    url += "&faultType="+troubleType;
                }
                App.getOkHttpUtil().get(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            List<TroubleDetailBean> dataList = new ArrayList<TroubleDetailBean>();
                            String json = response.body().string();
                            JSONObject jo = new JSONObject(json);
                            int code = jo.getInt("code");
                            if(code == 1){
                                JSONArray jsonArr = jo.getJSONArray("data");
                                for (int i = 0; i < jsonArr.length(); i++) {
                                    String jsonObj = jsonArr.getString(i);
                                    TroubleDetailBean tdb = JSON.parseObject(jsonObj, TroubleDetailBean.class);
                                    dataList.add(tdb);
                                }

                                subscriber.onNext(dataList);
                                subscriber.onCompleted();
                            }else {
                                subscriber.onError(new Exception("获取不到数据"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                    }
                });
            }
        });

    }
}
