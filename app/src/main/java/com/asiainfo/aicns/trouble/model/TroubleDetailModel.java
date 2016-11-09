package com.asiainfo.aicns.trouble.model;

import com.asiainfo.aicns.bean.TroubleMoreDetailBean;

import rx.Observable;

/**
 * Created by uuom on 16-11-8.
 */
public interface TroubleDetailModel {
    Observable<TroubleMoreDetailBean> getTroubleDetail(String faultId);
}
