package com.wapchief.qiniuplayer.event;

/**
 * @author wapchief
 * @date 2018/3/20
 */

public class DanmaKuEvent {
    boolean isSwitch;

    public DanmaKuEvent(boolean isSwitch) {
        this.isSwitch = isSwitch;
    }

    public boolean isSwitch() {
        return isSwitch;
    }

    public void setSwitch(boolean aSwitch) {
        isSwitch = aSwitch;
    }
}
