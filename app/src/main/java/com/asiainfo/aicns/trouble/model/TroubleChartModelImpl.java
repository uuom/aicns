package com.asiainfo.aicns.trouble.model;

import com.asiainfo.aicns.bean.ProvinceTrouble;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by uuom on 16-11-2.
 */
public class TroubleChartModelImpl implements TroubleChartModel {


    @Override
    public Observable<List<ProvinceTrouble>> getProvinceTroubleData(Integer troubleType) {
        return Observable.create(new Observable.OnSubscribe<List<ProvinceTrouble>>() {
            @Override
            public void call(Subscriber<? super List<ProvinceTrouble>> subscriber) {

                //
                List<ProvinceTrouble> dataList = new ArrayList<ProvinceTrouble>();
                subscriber.onNext(dataList);
                subscriber.onCompleted();
            }
        });
    }
}
