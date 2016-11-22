package com.asiainfo.aicns.trouble.presenter;

import android.util.Log;

import com.asiainfo.aicns.trouble.model.TroubleChartModel;
import com.asiainfo.aicns.trouble.model.TroubleChartModelImpl;
import com.asiainfo.aicns.trouble.view.TroubleChartView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by uuom on 16-11-2.
 */
public class TroubleChartPresenterImpl implements TroubleChartPresenter {

    private TroubleChartView troubleChartView;
    private TroubleChartModel troubleChartModel;

    public TroubleChartPresenterImpl(TroubleChartView troubleChartView) {
        this.troubleChartView = troubleChartView;
        troubleChartModel = new TroubleChartModelImpl();
    }

    @Override
    public void refreshChart(Integer troubleLevel) {
        troubleChartView.showProgress();
        troubleChartModel.getProvinceTroubleData(troubleLevel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
//                        troubleChartView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(String jsonData) {
                        troubleChartView.setData2WebView(jsonData);
                    }
                });
    }
}
