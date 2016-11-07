package com.asiainfo.aicns.trouble.presenter;

import com.asiainfo.aicns.bean.TroubleDetailBean;
import com.asiainfo.aicns.trouble.model.TroubleListModel;
import com.asiainfo.aicns.trouble.model.TroubleListModelImpl;
import com.asiainfo.aicns.trouble.view.TroubleListView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by uuom on 16-11-3.
 */
public class TroubleListPresenterImpl implements TroubleListPresenter {

    private TroubleListView troubleListView;
    private TroubleListModel troubleListModel;

    public TroubleListPresenterImpl(TroubleListView troubleListView) {
        this.troubleListView = troubleListView;
        troubleListModel = new TroubleListModelImpl();
    }

    @Override
    public void refreshTroubleListData(Integer troubleType) {
        troubleListView.showRefreshLayout();
        int page = 1;
        troubleListModel.getTroubleListData(troubleType,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TroubleDetailBean>>() {
                    @Override
                    public void onCompleted() {
                        troubleListView.hideRefreshLayout();
                    }

                    @Override
                    public void onError(Throwable e) {
                        troubleListView.hideRefreshLayout();
                    }

                    @Override
                    public void onNext(List<TroubleDetailBean> datas) {
                        troubleListView.setData2RecyclerView(datas);
                    }
                });
    }

    @Override
    public void addTroubleListData(int page, Integer troubleType) {
        troubleListModel.getTroubleListData(troubleType,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TroubleDetailBean>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        troubleListView.hideRefreshLayout();
                    }

                    @Override
                    public void onNext(List<TroubleDetailBean> datas) {
                        troubleListView.addData2RecyclerView(datas);
                    }
                });
    }
}
