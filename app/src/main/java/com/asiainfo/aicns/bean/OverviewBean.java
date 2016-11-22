package com.asiainfo.aicns.bean;

/**
 * Created by yangxp5 on 2016/11/22.
 */
public class OverviewBean {

    private int allDev;
    private int abnormalDev;

    private int allLink;
    private int abnormalLink;

    public int getAllDev() {
        return allDev;
    }

    public void setAllDev(int allDev) {
        this.allDev = allDev;
    }

    public int getAbnormalDev() {
        return abnormalDev;
    }

    public void setAbnormalDev(int abnormalDev) {
        this.abnormalDev = abnormalDev;
    }

    public int getAllLink() {
        return allLink;
    }

    public void setAllLink(int allLink) {
        this.allLink = allLink;
    }

    public int getAbnormalLink() {
        return abnormalLink;
    }

    public void setAbnormalLink(int abnormalLink) {
        this.abnormalLink = abnormalLink;
    }
}
