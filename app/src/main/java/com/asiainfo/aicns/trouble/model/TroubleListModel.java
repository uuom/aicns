package com.asiainfo.aicns.trouble.model;

import com.asiainfo.aicns.bean.TroubleDetailBean;

import java.util.List;

import rx.Observable;

/**
 * Created by uuom on 16-11-3.
 */
public interface TroubleListModel {

    /**
     * 获取故障数据
     * @param troubleType
     * @param page
     * @return
     */
    Observable<List<TroubleDetailBean>> getTroubleListData(Integer troubleType, int page);
}
