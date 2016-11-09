package com.asiainfo.aicns.trouble.view;

import com.asiainfo.aicns.bean.TroubleMoreDetailBean;

import java.util.Map;

/**
 * Created by uuom on 16-11-8.
 */
public interface TroubleDetailView {
    void setRefreshLayout(boolean b);

    void setTroubleDetail2View(TroubleMoreDetailBean dataMap);
}
