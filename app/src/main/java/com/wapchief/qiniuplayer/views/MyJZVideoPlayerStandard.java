package com.wapchief.qiniuplayer.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wapchief.qiniuplayer.R;
import com.wapchief.qiniuplayer.event.DownloadEvent;
import com.wapchief.qiniuplayer.event.SpeedEvent;

import org.greenrobot.eventbus.EventBus;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by wapchief on 2018/1/20.
 */
public class MyJZVideoPlayerStandard extends JZVideoPlayerStandard {

    //倍速
    TextView video_speed;
    //下载
    TextView video_download;
    //音频
    TextView audio;
    //    MediaController mMediaController;
    //倍速
    float mFloat = 1;
    private Context mContext;
    public MyJZVideoPlayerStandard(Context context) {
        super(context);
        mContext = context;
    }

    public MyJZVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init(Context context) {
        super.init(context);
        video_speed = (TextView) findViewById(R.id.video_speed);
        video_download = (TextView) findViewById(R.id.video_download);
        startButton = (ImageView) findViewById(R.id.start);
        backButton = (ImageView) findViewById(R.id.back);
        mRetryLayout = (LinearLayout) findViewById(R.id.retry_layout);
        thumbImageView = (ImageView) findViewById(R.id.thumb);
        clarity = (TextView) findViewById(R.id.clarity);
        bottomProgressBar = (ProgressBar) findViewById(R.id.bottom_progress);
        batteryTimeLayout = (LinearLayout) findViewById(R.id.battery_time_layout);
        //电量
        batteryLevel = (ImageView) findViewById(R.id.battery_level);
        videoCurrentTime = (TextView) findViewById(R.id.video_current_time);
        audio = (TextView) findViewById(R.id.audio);

        video_speed.setOnClickListener(this);
        video_download.setOnClickListener(this);
        startButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        thumbImageView.setOnClickListener(this);
        clarity.setOnClickListener(this);

        startButton.setVisibility(INVISIBLE);
        backButton.setVisibility(View.VISIBLE);
//         mDetailClassPalyer.mRetryBtn.setBackgroundResource(R.drawable.transparent1px);
        //加载失败的布局
        mRetryLayout.setVisibility(View.VISIBLE);
        //播放按钮
        startButton.setVisibility(View.VISIBLE);
        //禁止点击缩略图播放
//        thumbImageView.setFocusable(false);
//        thumbImageView.setEnabled(false);
//         mDetailClassPalyer
//         mDetailClassPalyer.
        //隐藏电池电量
//        batteryTimeLayout.setVisibility(View.GONE);
//        batteryLevel.setVisibility(View.GONE);
        //清晰度
        clarity.setVisibility(View.VISIBLE);
        bottomProgressBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        bottomProgressBar.setVisibility(View.GONE);
//        retryRoot = (LinearLayout) findViewById(cn.jzvd.R.id.retry_layout);
//        retryRoot.setVisibility(INVISIBLE);


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.video_speed) {
            // 切换倍速
            video_speed.setText(resolveTypeUI(mFloat) + "X");
            mFloat = resolveTypeUI(mFloat);
            EventBus.getDefault().post(new SpeedEvent(mFloat));

            // 更新播放状态
            onStatePreparingChangingUrl(0, getCurrentPositionWhenPlaying());
        }else if (i == R.id.video_download) {
            // 下载
            EventBus.getDefault().post(new DownloadEvent(true));
        }
    }

    /**开始播放*/
    @Override
    public void startVideo() {
        super.startVideo();

    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();

    }


    @Override
    public void setUp(Object[] dataSourceObjects, int defaultUrlMapIndex, int screen, Object... objects) {
        super.setUp(dataSourceObjects, defaultUrlMapIndex, screen, objects);
        //如果是全屏才显示相关按钮
        Log.e("data========:", dataSourceObjects.length+"");
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            video_speed.setVisibility(VISIBLE);
            batteryTimeLayout.setVisibility(VISIBLE);
            videoCurrentTime.setVisibility(VISIBLE);
            batteryLevel.setVisibility(VISIBLE);

        } else if (currentScreen == SCREEN_WINDOW_NORMAL) {
            video_speed.setVisibility(GONE);
            batteryTimeLayout.setVisibility(GONE);
            videoCurrentTime.setVisibility(GONE);
            batteryLevel.setVisibility(GONE);
            video_download.setVisibility(GONE);

        } else if (currentScreen == SCREEN_WINDOW_TINY) {
            video_speed.setVisibility(GONE);
            batteryTimeLayout.setVisibility(GONE);
            videoCurrentTime.setVisibility(GONE);
            batteryLevel.setVisibility(GONE);
            video_download.setVisibility(GONE);
        }

    }


    @Override
    public void setProgressAndText(int progress, long position, long duration) {
        super.setProgressAndText(progress, position, duration);
    }


    /**播放、暂停按钮状态*/
    @Override
    public void updateStartImage() {
        super.updateStartImage();
//        startButton.setVisibility(INVISIBLE);
    }



    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();

    }


    /*显示倍速比例*/

    public static float resolveTypeUI(float speed) {
        if (speed == 1) {
            speed = 1.25f;
        } else if (speed == 1.25f) {
            speed = 1.5f;
        } else if (speed == 1.5f) {
            speed = 2f;
        } else if (speed == 2f) {
            speed = 1f;
        }
        return speed;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
    }

    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
    }

    @Override
    public void onStateError() {
        super.onStateError();
    }

    @Override
    public void onInfo(int what, int extra) {
        super.onInfo(what, extra);
    }

    @Override
    public void onError(int what, int extra) {
        super.onError(what, extra);
    }

    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
    }

    @Override
    public void startWindowTiny() {
        super.startWindowTiny();
    }

    @Override
    public int getLayoutId() {
        return R.layout.jiaozi_player_video;
    }


}
