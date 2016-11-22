package com.asiainfo.aicns.trouble.presenter;

import android.util.Log;
import android.view.View;

import com.asiainfo.aicns.R;
import com.asiainfo.aicns.bean.TroubleDetailBean;
import com.asiainfo.aicns.common.constant.Constant;
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
    public void refreshTroubleListData(Integer troubleLevel) {
        troubleListView.showRefreshLayout();
        int page = 1;
        troubleListView.setCurrentPage(page);
        troubleListModel.getTroubleListData(troubleLevel,page)
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
                        troubleListView.setData2RecyclerView(datas);
                        troubleListView.hideRefreshLayout();
                    }
                });
    }

    @Override
    public void addTroubleListData(int currentPage, Integer troubleLevel) {
        int page = currentPage+1;
        troubleListView.setCurrentPage(page);
        troubleListModel.getTroubleListData(troubleLevel,page)
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

    @Override
    public void onTroubleSortClick(View view, Integer sort, Integer orderBy) {
        int resultSort = sort;
        int resultOrderBy = orderBy;
        switch (view.getId()){
            case R.id.troubleLevelSort:
                if (sort == Constant.SORT_TROUBLE_LEVEL){
                    resultOrderBy = (orderBy==Constant.ORDER_BY_DESC ? Constant.ORDER_BY_ASC : Constant.ORDER_BY_DESC);
                }else{
                    resultSort = Constant.SORT_TROUBLE_LEVEL;
                    resultOrderBy = Constant.ORDER_BY_DESC;
                }
                break;
            case R.id.troubleTimeSort:
                if (sort == Constant.SORT_TROUBLE_TIME){
                    resultOrderBy = (orderBy==Constant.ORDER_BY_DESC ? Constant.ORDER_BY_ASC : Constant.ORDER_BY_DESC);
                }else{
                    resultSort = Constant.SORT_TROUBLE_TIME;
                    resultOrderBy = Constant.ORDER_BY_DESC;
                }
                break;
        }
        troubleListView.updateTroubleSortImage(resultSort, resultOrderBy);
    }
}
