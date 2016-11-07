package com.asiainfo.aicns.bean;

/**
 * Created by uuom on 16-10-18.
 */
public class TroubleBean {

    public String troubleType;
    public String troubleTypeZHName;
    public String troubleCount;
    public String troubleColor;

    public TroubleBean(String troubleTypeZHName, String troubleType, String troubleCount, String troubleColor) {
        this.troubleType = troubleType;
        this.troubleCount = troubleCount;
        this.troubleColor = troubleColor;
        this.troubleTypeZHName = troubleTypeZHName;
    }
}
