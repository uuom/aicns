package com.asiainfo.aicns.trouble.event;

/**
 * Created by yangxp5 on 2016/11/18.
 */
public class TroubleChartInitFinishedEvent {

    public static String dataStr;

    public TroubleChartInitFinishedEvent(String dataStr) {
        this.dataStr = dataStr;
    }
}
