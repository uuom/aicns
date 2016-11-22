package com.asiainfo.aicns.trouble.presenter;

import android.view.View;

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

    /**
     * 故障列表排序
     * @param view
     * @param sort
     * @param orderBy
     */
    void onTroubleSortClick(View view, Integer sort, Integer orderBy);
}
