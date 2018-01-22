package com.wapchief.qiniuplayer;

import android.app.Application;

import com.vondear.rxtools.RxTool;

/**
 * Created by wapchief on 2018/1/18.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);

    }
}
