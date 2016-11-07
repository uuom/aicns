package com.asiainfo.aicns.trouble.model;

import com.asiainfo.aicns.bean.TroubleBean;

import java.util.List;

import rx.Observable;

/**
 * Created by uuom on 16-10-31.
 */
public interface TroubleModel {
    Observable<List<TroubleBean>> getTroubleData();
}
