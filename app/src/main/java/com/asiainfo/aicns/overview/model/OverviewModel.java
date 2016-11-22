package com.asiainfo.aicns.overview.model;

import com.asiainfo.aicns.bean.OverviewBean;

import rx.Observable;

/**
 * Created by yangxp5 on 2016/11/22.
 */
public interface OverviewModel {
    Observable<OverviewBean> getOverviewData();
}
