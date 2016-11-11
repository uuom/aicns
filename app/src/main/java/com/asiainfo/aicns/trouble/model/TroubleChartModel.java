package com.asiainfo.aicns.trouble.model;

import rx.Observable;

/**
 * Created by uuom on 16-11-2.
 */
public interface TroubleChartModel {
    Observable<String> getProvinceTroubleData(Integer troubleLevel);
}
