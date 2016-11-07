package com.asiainfo.aicns.trouble.presenter;

import com.asiainfo.aicns.bean.ProvinceTrouble;
import com.asiainfo.aicns.trouble.model.TroubleChartModel;
import com.asiainfo.aicns.trouble.model.TroubleChartModelImpl;
import com.asiainfo.aicns.trouble.view.TroubleChartView;

import java.util.List;

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
    public void initChart(Integer troubleType) {
        troubleChartModel.getProvinceTroubleData(troubleType)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ProvinceTrouble>>() {
                    @Override
                    public void onCompleted() {
                        troubleChartView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ProvinceTrouble> provinceTroubles) {

                        String data = "[{\"name\": \"北京\",\"value\": 18}," +
                                "{\"name\": \"河北\",\"value\": 11}," +
                                "{\"name\": \"河南\",\"value\": 12}," +
                                "{\"name\": \"内蒙古\",\"value\": 15}," +
                                "{\"name\": \"黑龙江\",\"value\": 11}," +
                                "{\"name\": \"贵州\",\"value\": 10}," +
                                "{\"name\": \"新疆\",\"value\": 2}," +
                                "{\"name\": \"西藏\",\"value\": 15}," +
                                "{\"name\": \"陕西\",\"value\": 20}," +
                                "{\"name\": \"香港\",\"value\": 4}]";

                        troubleChartView.setData2WebView(data);
                    }
                });
    }
}
