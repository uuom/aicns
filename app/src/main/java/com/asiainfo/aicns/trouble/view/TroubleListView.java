package com.asiainfo.aicns.trouble.view;

import com.asiainfo.aicns.bean.TroubleDetailBean;

import java.util.List;

/**
 * Created by uuom on 16-11-3.
 */
public interface TroubleListView {
    void showRefreshLayout();

    void hideRefreshLayout();

    void setData2RecyclerView(List<TroubleDetailBean> datas);

    void addData2RecyclerView(List<TroubleDetailBean> datas);
}
