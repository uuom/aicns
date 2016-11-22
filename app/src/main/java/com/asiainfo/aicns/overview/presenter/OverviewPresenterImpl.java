package com.asiainfo.aicns.overview.presenter;

import com.asiainfo.aicns.bean.OverviewBean;
import com.asiainfo.aicns.overview.model.OverviewModel;
import com.asiainfo.aicns.overview.model.OverviewModelImpl;
import com.asiainfo.aicns.overview.view.OverviewView;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by yangxp5 on 2016/11/22.
 */
public class OverviewPresenterImpl implements OverviewPresenter {

    private OverviewModel overviewModel;
    private OverviewView overviewView;

    public OverviewPresenterImpl(OverviewView overviewView) {
        this.overviewView = overviewView;
        overviewModel = new OverviewModelImpl();
    }

    @Override
    public void refreshOverview() {
        overviewView.isRefreshing(true);
        overviewModel.getOverviewData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OverviewBean>() {
                    @Override
                    public void onCompleted() {
                        overviewView.isRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(OverviewBean overviewBean) {
                        overviewView.updateOverviewData(overviewBean);
                    }
                });
    }
}
