package com.wapchief.qiniuplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.wapchief.qiniuplayer.views.SampleControlVideo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wapchief on 2018/1/18.
 */

public class GSYVideoPlayerActivity extends GSYBaseActivityDetail{

    private SampleControlVideo mVideo;
    private Button changeSpeed;
    private String url = "https://res.exexm.com/cw_145225549855002";
    private float speed = 1;

    OrientationUtils mOrientationUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsy);
        initView();

        changeSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveTypeUI();
            }
        });
    }

    private void initView() {
        mVideo = findViewById(R.id.detail_player);
        changeSpeed = findViewById(R.id.change_speed);

        String source1 = MediaUrl.URL_M3U8;
        VideoModel model = new VideoModel(source1, "普通");
        VideoModel mode2 = new VideoModel(source1, "标清");
        VideoModel mode3 = new VideoModel(source1, "高清");
        List<VideoModel> models = new ArrayList<>();
        models.add(model);
        models.add(mode2);
        models.add(mode3);

        mVideo.setUp(models, true, "");

        mVideo.getBackButton().setVisibility(View.VISIBLE);

    }

    /*初始化播放器*/
    @Override
    public void initVideo() {
        super.initVideo();
        if (getGSYVideoPlayer().getFullscreenButton() != null) {
            getGSYVideoPlayer().getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getGSYVideoPlayer().startWindowFullscreen(GSYVideoPlayerActivity.this, true, true);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public GSYBaseVideoPlayer getGSYVideoPlayer() {
        return mVideo;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        ImageView imageView = new ImageView(this);

        return new GSYVideoOptionBuilder().setUrl(MediaUrl.URL_M3U8)
                .setCacheWithPlay(true)
                .setVideoTitle("1")
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setSeekRatio(1);
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return false;
    }

    /*显示倍速比例*/
    private void resolveTypeUI() {
        if (speed == 1) {
            speed = 1.5f;
        } else if (speed == 1.5f) {
            speed = 2f;
        } else if (speed == 2f) {
            speed = 0.5f;
        }else if (speed == 0.5f) {
            speed = 0.25f;
        }else if (speed == 0.25f) {
            speed = 1;
        }
        changeSpeed.setText("倍速" + speed);
        mVideo.setSpeedPlaying(speed, true);

    }
}
