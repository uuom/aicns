package com.asiainfo.aicns.trouble.view;

import com.asiainfo.aicns.bean.TroubleBean;

import java.util.List;

/**
 * Created by uuom on 16-10-31.
 */
public interface TroubleView {

    /**
     * 显示错误Toast
     * @param msg
     */
    void showErrorMessage(String msg);

    /**
     * 设置新数据到listview
     * @param dataList
     */
    void setNewData2RecyclerView(List<TroubleBean> dataList);

    /**
     * 设置刷新按钮的显示
     * @param b
     */
    void setRefreshLayoutShow(boolean b);
}
