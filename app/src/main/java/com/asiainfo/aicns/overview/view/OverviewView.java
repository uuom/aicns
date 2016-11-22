package com.asiainfo.aicns.overview.view;

import com.asiainfo.aicns.bean.OverviewBean;

/**
 * Created by yangxp5 on 2016/11/22.
 */
public interface OverviewView {
    void isRefreshing(boolean b);

    void updateOverviewData(OverviewBean overviewBean);
}
