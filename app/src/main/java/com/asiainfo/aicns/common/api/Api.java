package com.asiainfo.aicns.common.api;

/**
 * Created by uuom on 16-10-27.
 */
public class Api {

    public static String REQUEST_BASE_URL = "http://120.52.120.228:6699";
//    public static String REQUEST_BASE_URL = "http://10.1.249.6:6677";
//    public static String REQUEST_BASE_URL = "http://10.0.2.2:8080";

    public static String LOGIN_URL = "/NetXpert/common/mobileAction.do?actionType=doLogin";
    public static String CHECK_APP_UPDATE_URL = "/NetXpert/common/mobileAction.do?actionType=checkAppUpdate";

    //故障监测
    public static String GET_TROUBLE_COUNT_URL = "/NetXpert/account/indexpageAction.do?actionType=getTroubleTable";
    public static String GET_TROUBLE_DETAIL_LIST_URL= "/NetXpert/common/troubleAction.do?actionType=getTroubleDetailList";
    public static String GET_TROUBLE_DETAIL_URL= "/NetXpert/common/troubleAction.do?actionType=getTroubleDetail";
    public static String GET_TROUBLE_CHART_DATA_URL= "/NetXpert/common/troubleAction.do?actionType=getTroubleChartData";

    //概况监测
    public static String GET_DEV_AND_LINK_PERCENT = "/NetXpert/account/indexpageAction.do?actionType=getDevAndLinkPerc";
}
