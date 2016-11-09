package com.asiainfo.aicns.trouble.presenter;

import com.asiainfo.aicns.bean.TroubleMoreDetailBean;
import com.asiainfo.aicns.trouble.model.TroubleDetailModel;
import com.asiainfo.aicns.trouble.model.TroubleDetailModelImpl;
import com.asiainfo.aicns.trouble.view.TroubleDetailView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by uuom on 16-11-8.
 */
public class TroubleDetailPresenterImpl implements TroubleDetailPresenter {

    private TroubleDetailView troubleDetailView;
    private TroubleDetailModel troubleDetailModel;

    public TroubleDetailPresenterImpl(TroubleDetailView troubleDetailView) {
        this.troubleDetailView = troubleDetailView;
        troubleDetailModel = new TroubleDetailModelImpl();
    }

    @Override
    public void refreshTroubleDetail(String faultId) {
        troubleDetailView.setRefreshLayout(true);
        troubleDetailModel.getTroubleDetail(faultId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TroubleMoreDetailBean>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        troubleDetailView.setRefreshLayout(false);
                    }

                    @Override
                    public void onNext(TroubleMoreDetailBean troubleMoreDetailBean) {
                        troubleDetailView.setTroubleDetail2View(troubleMoreDetailBean);
                        troubleDetailView.setRefreshLayout(false);
                    }
                });
    }
}
