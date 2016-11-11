package com.asiainfo.aicns.trouble.presenter;

/**
 * Created by uuom on 16-11-3.
 */
public interface TroubleListPresenter {

    /**
     * 刷新故障列表
     * @param troubleLevel
     */
    void refreshTroubleListData(Integer troubleLevel);

    /**
     * 故障列表加载更多数据
     * @param i
     * @param troubleLevel
     */
    void addTroubleListData(int i, Integer troubleLevel);
}
