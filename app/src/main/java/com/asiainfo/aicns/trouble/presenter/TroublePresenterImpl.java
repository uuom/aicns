package com.asiainfo.aicns.trouble.presenter;

import android.util.Log;

import com.asiainfo.aicns.bean.TroubleBean;
import com.asiainfo.aicns.trouble.model.TroubleModel;
import com.asiainfo.aicns.trouble.model.TroubleModelImpl;
import com.asiainfo.aicns.trouble.view.TroubleView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by uuom on 16-10-31.
 */
public class TroublePresenterImpl implements TroublePresenter {

    private TroubleView troubleView;
    private TroubleModel troubleModel;

    public TroublePresenterImpl(TroubleView troubleView) {
        this.troubleView = troubleView;
        troubleModel = new TroubleModelImpl();
    }

    @Override
    public void refreshTroubleData() {
        troubleView.setRefreshLayoutShow(true);
        troubleModel.getTroubleData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TroubleBean>>() {
                    @Override
                    public void onCompleted() {
                        troubleView.setRefreshLayoutShow(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        troubleView.showErrorMessage("网络请求异常");
                        Log.e("TroublePresenterImpl", e.toString());
                    }

                    @Override
                    public void onNext(List<TroubleBean> troubleBeen) {
                        troubleView.setNewData2RecyclerView(troubleBeen);
                    }
                });
    }
}
