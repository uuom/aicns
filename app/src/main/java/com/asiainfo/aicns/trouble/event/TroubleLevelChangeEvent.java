package com.asiainfo.aicns.trouble.event;

/**
 * Created by uuom on 16-11-7.
 */
public class TroubleLevelChangeEvent {

    public static Integer troubleLevel;

    public TroubleLevelChangeEvent(Integer troubleLevel) {
        this.troubleLevel = troubleLevel;
    }
}
