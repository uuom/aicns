package com.asiainfo.aicns.bean;

/**
 * Created by uuom on 16-11-2.
 */
public class ProvinceTrouble {

    public String domainCode;
    public String domainName;
    public int troubleCount;

    public ProvinceTrouble() {
    }

    public ProvinceTrouble(int troubleCount, String domainName) {
        this.troubleCount = troubleCount;
        this.domainName = domainName;
    }

    public ProvinceTrouble(String domainCode, int troubleCount, String domainName) {
        this.domainCode = domainCode;
        this.troubleCount = troubleCount;
        this.domainName = domainName;
    }
}
