package com.wapchief.qiniuplayer;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wapchief.qiniuplayer.event.DownloadEvent;
import com.wapchief.qiniuplayer.event.SpeedEvent;
import com.wapchief.qiniuplayer.system.MyIJKMediaSystem;
import com.wapchief.qiniuplayer.system.MyJZMediaSystem;
import com.wapchief.qiniuplayer.views.MyJZVideoPlayerStandard;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.jzvd.JZVideoPlayer;

import static com.wapchief.qiniuplayer.MediaUrl.URL_RTMP;

/**
 * @author wapchief
 * @data 2018/2/4
 */

public class JiaoZiPlayerActivity extends AppCompatActivity{

    private MyJZVideoPlayerStandard mPlayerStandard;
    private Button mButton1, mButton;
    private String[] mediaName = {"普通","高清","原画"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaozi);
        initView();
        initPlayerUrl();
        mJZMediaSystem = new MyJZMediaSystem();
        mIJKMediaSystem = new MyIJKMediaSystem();
    }

    /**
     * 初始化播放地址
     */
    private void initPlayerUrl() {
        Object[] objects = new Object[3];
        LinkedHashMap map = new LinkedHashMap();
        for (int i = 0; i < 3; i++) {
            map.put(mediaName[i], URL_RTMP);
        }
        objects[0] = map;
        objects[1] = false;
        objects[2] = new HashMap<>();
        ((HashMap) objects[2]).put("key", "value");
        mPlayerStandard.setUp(objects, 0, JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
    }

    private void initView() {
        mPlayerStandard = findViewById(R.id.jiaozi_player);
        mButton = findViewById(R.id.jiaozi_bt);
        mButton.setVisibility(View.GONE);
        //切换到默认JZ引擎
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JZVideoPlayer.setMediaInterface(mJZMediaSystem);
            }
        });
        mButton1 = findViewById(R.id.ijk_bt);
        mButton1.setVisibility(View.GONE);
        //切换到Ijk引擎
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JZVideoPlayer.setMediaInterface(mIJKMediaSystem);
            }
        });
    }

    /**
     * 设置屏幕方向
     */
    private void initPlayer() {
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();

    }

    //系统播放器引擎
    MyJZMediaSystem mJZMediaSystem;
    MyIJKMediaSystem mIJKMediaSystem;
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
        JZVideoPlayer.setMediaInterface(mIJKMediaSystem);
        //Change these two variables back
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    protected void onResume() {
        super.onResume();
        JZVideoPlayer.setMediaInterface(mIJKMediaSystem);
        initPlayer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册消息总线
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**倍速切换*/
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostSpeed(SpeedEvent event) {
        mJZMediaSystem.setSpeeding(event.getSpeed());
        mIJKMediaSystem.setSpeeding(event.getSpeed());
        Toast.makeText(this, "正在切换倍速:"+event.getSpeed(), Toast.LENGTH_LONG).show();
    }

    /**下载*/
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostDetail(DownloadEvent event) {
        Toast.makeText(this, "下载", Toast.LENGTH_SHORT).show();
    }

}
