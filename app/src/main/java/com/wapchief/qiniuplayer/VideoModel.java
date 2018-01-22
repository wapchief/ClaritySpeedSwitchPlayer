package com.wapchief.qiniuplayer;

/**
 * Created by wapchief on 2018/1/19.
 */

public class VideoModel {
    public String url;
    public String name;

    public VideoModel(String name, String url) {
        this.url = url;
        this.name = name;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
