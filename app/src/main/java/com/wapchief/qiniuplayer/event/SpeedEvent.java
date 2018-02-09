package com.wapchief.qiniuplayer.event;

/**
 * @author wapchief
 * @data 2018/2/8
 */

public class SpeedEvent {

    public SpeedEvent(float speed) {
        this.speed = speed;
    }

    float speed;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
