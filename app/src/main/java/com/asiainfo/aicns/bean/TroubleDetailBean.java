package com.asiainfo.aicns.bean;

/**
 * Created by uuom on 16-11-3.
 */
public class TroubleDetailBean {

    String troubleId;   //故障id

    String troubleLevelCode;    //故障级别code
    String troubleLevel;    //故障级别名称
    String troubleLevelColor;   //故障级别颜色

    String troubleTypeCode; //故障类型code
    String troubleType; //故障类型名称

    String troubleTime; //发生时间

    String troubleDeviceName;   //故障设备名称
    String troubleDescription;  //故障描述

    String domainId;    //所属域id
    String domainName;  //所属域名称

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getTroubleDescription() {
        return troubleDescription;
    }

    public void setTroubleDescription(String troubleDescription) {
        this.troubleDescription = troubleDescription;
    }

    public String getTroubleDeviceName() {
        return troubleDeviceName;
    }

    public void setTroubleDeviceName(String troubleDeviceName) {
        this.troubleDeviceName = troubleDeviceName;
    }

    public String getTroubleId() {
        return troubleId;
    }

    public void setTroubleId(String troubleId) {
        this.troubleId = troubleId;
    }

    public String getTroubleLevel() {
        return troubleLevel;
    }

    public void setTroubleLevel(String troubleLevel) {
        this.troubleLevel = troubleLevel;
    }

    public String getTroubleLevelCode() {
        return troubleLevelCode;
    }

    public void setTroubleLevelCode(String troubleLevelCode) {
        this.troubleLevelCode = troubleLevelCode;
    }

    public String getTroubleLevelColor() {
        return troubleLevelColor;
    }

    public void setTroubleLevelColor(String troubleLevelColor) {
        this.troubleLevelColor = troubleLevelColor;
    }

    public String getTroubleTime() {
        return troubleTime;
    }

    public void setTroubleTime(String troubleTime) {
        this.troubleTime = troubleTime;
    }

    public String getTroubleType() {
        return troubleType;
    }

    public void setTroubleType(String troubleType) {
        this.troubleType = troubleType;
    }

    public String getTroubleTypeCode() {
        return troubleTypeCode;
    }

    public void setTroubleTypeCode(String troubleTypeCode) {
        this.troubleTypeCode = troubleTypeCode;
    }
}