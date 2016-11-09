package com.asiainfo.aicns.common.api;

/**
 * Created by uuom on 16-10-27.
 */
public class Api {

    public static String REQUEST_BASE_URL = "http://120.52.120.228:6699";
//    public static String REQUEST_BASE_URL = "http://10.0.2.2:8080";

    public static String LOGIN_URL = "/NetXpert/common/mobileAction.do?actionType=doLogin";
    public static String GET_TROUBLE_COUNT_URL = "/NetXpert/account/indexpageAction.do?actionType=getTroubleTable";

    public static String CHECK_APP_UPDATE_URL = "/NetXpert/common/mobileAction.do?actionType=checkAppUpdate";
    public static String NEW_APP_URL = "http://fir.im/v37j";

    public static String GET_TROUBLE_DETAIL_LIST_URL= "/NetXpert/common/mobileAction.do?actionType=getTroubleDetailList";
    public static String GET_TROUBLE_DETAIL_URL= "/NetXpert/common/mobileAction.do?actionType=getTroubleDetail";
}
