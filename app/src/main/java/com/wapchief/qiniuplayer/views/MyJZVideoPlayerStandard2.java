package com.wapchief.qiniuplayer.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * @author wapchief
 * @date 2018/7/20
 */

public class MyJZVideoPlayerStandard2 extends JZVideoPlayerStandard {

    //倍速
    TextView video_speed;
    //下载
    TextView video_download;
    //音频
    TextView audio;
    //    MediaController mMediaController;
    //倍速
    float mFloat = 1;
    //缩略图
    String thumbUrl;
    //视频类型
    int classType = 0;
    //视频在目录中的索引
    int classIndex = 0;
    //    LinearLayout retryRoot;
    private Context mContext;
    public MyJZVideoPlayerStandard2(Context context) {
        super(context);
        mContext = context;
    }

    public MyJZVideoPlayerStandard2(Context context, AttributeSet attrs) {
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
        backButton.setVisibility(View.GONE);
//         mDetailClassPalyer.mRetryBtn.setBackgroundResource(R.drawable.transparent1px);
        //加载失败的布局
        mRetryLayout.setVisibility(View.GONE);
        //播放按钮
        startButton.setVisibility(View.GONE);
        //禁止点击缩略图播放
        thumbImageView.setFocusable(false);
        thumbImageView.setEnabled(false);
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
        try {
            super.onClick(v);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        int i = v.getId();
        if (i == R.id.video_speed) {
//            ToastUtils.showShort("切换倍速");
            video_speed.setText(resolveTypeUI(mFloat) + "X");
            mFloat = resolveTypeUI(mFloat);
            EventBus.getDefault().post(new SpeedEvent(mFloat));
            //更新播放器
            onStatePreparingChangingUrl(0, getCurrentPositionWhenPlaying());
        }else if (i == R.id.video_download) {
            EventBus.getDefault().post(new DownloadEvent(true));
        }
    }

    /*开始播放*/
    @Override
    public void startVideo() {
        super.startVideo();
//        LogUtils.e("开始播放" + getCourseId() + "," + getTaskId());
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
        isFinish = true;
        errorNum = 0;
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();

    }


    @Override
    public void setUp(Object[] dataSourceObjects, int defaultUrlMapIndex, int screen, Object... objects) {
        super.setUp(dataSourceObjects, defaultUrlMapIndex, screen, objects);
        //如果是全屏才显示相关按钮
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            video_speed.setVisibility(VISIBLE);
            batteryTimeLayout.setVisibility(VISIBLE);
            videoCurrentTime.setVisibility(VISIBLE);
            batteryLevel.setVisibility(VISIBLE);
            video_download.setVisibility(GONE);

        } else if (currentScreen == SCREEN_WINDOW_NORMAL) {
            video_speed.setVisibility(GONE);
            batteryTimeLayout.setVisibility(GONE);
            videoCurrentTime.setVisibility(GONE);
            batteryLevel.setVisibility(GONE);

        } else if (currentScreen == SCREEN_WINDOW_TINY) {
            video_speed.setVisibility(GONE);
            batteryTimeLayout.setVisibility(GONE);
            videoCurrentTime.setVisibility(GONE);
            batteryLevel.setVisibility(GONE);
        }
        if (objects.length > 0) {
            Object[] datas = objects;
            try {
                setCourseId(datas[0].toString());
                setTaskId(datas[1].toString());
                isDonwload =(Boolean)datas[2];
                classIndex = (Integer) datas[3];
//                LogUtils.e("courseidobject"+datas[0].toString()+","+datas[1].toString());
            }catch (Exception e){

            }
        }
        titleTextView.setText("");
    }
    //是否为离线视频
    private boolean isDonwload = false;

    @Override
    public void showWifiDialog() {
        super.showWifiDialog();
//        final MyDialogCancel myDialogCancel = new MyDialogCancel(getContext());
//        //判断是否设置了使用流量播放
//        if (mHelper.getWIFIPlayer() == 0) {
//            myDialogCancel.dialogshow("", "使用流量播放会产生费用，请先在设置里打开开关", "去设置", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    clearFloatScreen();
//                    getContext().startActivity(new Intent(getContext(), SettingActivity.class));
//                }
//            }, new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    clearFloatScreen();
//                }
//            });
//        } else if (mHelper.getWIFIPlayer() == 1) {
//            myDialogCancel.dialogshow("注意", "使用流量播放会产生费用，是否继续？", "继续", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    myDialogCancel.dialogDismiss();
//                    onEvent(JZUserActionStandard.ON_CLICK_START_WIFIDIALOG);
//                    startVideo();
//                    WIFI_TIP_DIALOG_SHOWED = true;
//                }
//            }, new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    clearFloatScreen();
//                }
//            });
//        }
    }

    /*
                * 进度、当前时长、总时长
                * */
    long isFinishTime = 0;
    public static boolean isFinish = true;
    ScheduledExecutorService executorService= Executors.newSingleThreadScheduledExecutor();;
    @Override
    public void setProgressAndText(int progress, long position, long duration) {
        super.setProgressAndText(progress, position, duration);
        if (progress != 0) bottomProgressBar.setProgress(progress);
        //以分钟为单位
        long thisTime = position / 1000 / 60;
        long totalTime = duration / 1000 / 60;
//        LogUtils.e(progress+","+thisTime+","+totalTime);
        if (thisTime * 60L > 0) {
            if (thisTime + 1 > totalTime) {
                //最后一分钟调用结束事件
                isFinishTime = thisTime;
                if (position / 1000 % 20 == 0 && isFinish) {
//                    LogUtils.e(position + "," + (position / 1000) + "," + (thisTime * 60l % 20l));
                    //再判断是否能被20整除
                    classSee("finish", (thisTime * 60L) + "");
                }
            } else {
                if (position / 1000 % 20 == 0) {
//                    LogUtils.e(position + "," + (position / 1000) + "," + (thisTime * 60l % 20l));
                    //再判断是否能被20整除
                    classSee("doing", (thisTime * 60L) + "");
                }
            }
        }
//        if (isFinish) {
//            if ((duration / 1000 - position / 1000) < 60) {
//                //60秒倒计时设为完成
//                if (position / 1000 % 10 == 0) {
//                    executorService.scheduleWithFixedDelay(new Runnable() {
//                        @Override
//                        public void run() {
//                            LogUtils.e("courseIdrun:"+getCourseId()+","+getTaskId());
//                            classSee("finish", thisTime + "");
//                        }
//                    }, 0, 10, TimeUnit.SECONDS);
//                }
//            }
//        }


    }


    /*播放、暂停按钮状态*/
    @Override
    public void updateStartImage() {
//        super.updateStartImage();
        if (currentState == CURRENT_STATE_PLAYING) {
            startButton.setVisibility(VISIBLE);
//            startButton.setImageResource(R.drawable.icon_player_stop);
            replayTextView.setVisibility(INVISIBLE);
        } else if (currentState == CURRENT_STATE_ERROR) {
            startButton.setVisibility(INVISIBLE);
            replayTextView.setVisibility(INVISIBLE);
        } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
            startButton.setVisibility(VISIBLE);
//            startButton.setImageResource(R.drawable.icon_play_pressed);
            replayTextView.setVisibility(VISIBLE);
        } else {
//            startButton.setImageResource(R.drawable.icon_play_pressed);
            replayTextView.setVisibility(INVISIBLE);
        }
//        startButton.setVisibility(INVISIBLE);
    }


    //播放完成
    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        classSee("finish", isFinishTime * 60L + "");
//        LogUtils.e(getClassIndex()+"");
//        EventBus.getDefault().post(new PlayerOverEvent(getClassIndex()));
    }


    //观看事件
    String courseId = "";
    String taskId = "";
    //重复请求的错误次数
    private int errorNum = 0;
    public void classSee(String eventName, String watchTime) {
//        LogUtils.e("courseId:"+getCourseId()+","+getTaskId()+","+eventName+","+watchTime+","+ TimeUtils.getNowMills()/1000);
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
//        mVideo.setSpeedPlaying(speed, true);
    }

//    public static void startWindowscreen(Context context, Class _class, String url, Object... objects) {
//        LinkedHashMap map = new LinkedHashMap();
//        map.put(URL_KEY_DEFAULT, url);
//        Object[] dataSourceObjects = new Object[1];
//        dataSourceObjects[0] = map;
//        startWindowScreen(context, _class, dataSourceObjects, 0, objects);
//    }
//
//    public static void startWindowScreen(Context context, Class _class, Object[] dataSourceObjects, int defaultUrlMapIndex, Object... objects) {
//        hideSupportActionBar(context);
//        JZUtils.setRequestedOrientation(context, FULLSCREEN_ORIENTATION);
//        ViewGroup vp = (ViewGroup) (JZUtils.scanForActivity(context))
//                .findViewById(Window.ID_ANDROID_CONTENT);
//        View old = vp.findViewById(cn.jzvd.R.id.jz_fullscreen_id);
//        if (old != null) {
//            vp.removeView(old);
//        }
//        try {
//            Constructor<JZVideoPlayer> constructor = _class.getConstructor(Context.class);
//            final JZVideoPlayer jzVideoPlayer = constructor.newInstance(context);
//            jzVideoPlayer.setId(cn.jzvd.R.id.jz_fullscreen_id);
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            vp.addView(jzVideoPlayer, lp);
////            final Animation ra = AnimationUtils.loadAnimation(context, R.anim.start_fullscreen);
////            jzVideoPlayer.setAnimation(ra);
//            jzVideoPlayer.setUp(dataSourceObjects, defaultUrlMapIndex, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, objects);
//            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
//            jzVideoPlayer.startButton.performClick();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
    }

    //进入preparing状态，正在初始化视频
    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
    }

    //进入播放状态
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
        return R.layout.player_video2;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(int classIndx) {
        this.classIndex = classIndx;
    }
}

