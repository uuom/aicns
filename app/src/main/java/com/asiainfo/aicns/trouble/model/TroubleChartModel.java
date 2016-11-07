package com.asiainfo.aicns.trouble.model;

import com.asiainfo.aicns.bean.ProvinceTrouble;

import java.util.List;

import rx.Observable;

/**
 * Created by uuom on 16-11-2.
 */
public interface TroubleChartModel {
    Observable<List<ProvinceTrouble>> getProvinceTroubleData(Integer troubleType);
}
