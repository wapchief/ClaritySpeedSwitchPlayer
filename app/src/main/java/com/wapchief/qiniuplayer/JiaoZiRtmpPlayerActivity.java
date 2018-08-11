package com.wapchief.qiniuplayer;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hdl.m3u8.M3U8DownloadTask;
import com.hdl.m3u8.M3U8InfoManger;
import com.hdl.m3u8.bean.M3U8;
import com.hdl.m3u8.bean.OnDownloadListener;
import com.hdl.m3u8.bean.OnM3U8InfoListener;
import com.hdl.m3u8.utils.NetSpeedUtils;
import com.wapchief.qiniuplayer.event.DanmaKuEvent;
import com.wapchief.qiniuplayer.event.DownloadEvent;
import com.wapchief.qiniuplayer.event.SpeedEvent;
import com.wapchief.qiniuplayer.system.MyDanmaKuController;
import com.wapchief.qiniuplayer.system.MyIJKMediaSystem;
import com.wapchief.qiniuplayer.views.MyJZVideoPlayerRtmpStandard;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * @author wapchief
 * @data 2018/2/4
 */

public class JiaoZiRtmpPlayerActivity extends AppCompatActivity{

    private MyJZVideoPlayerRtmpStandard mPlayerStandard;
    private Button mButton1, mButton,mButtonDownload,mButtonPlayer;
    private TextView mProgress;
    private String[] mediaName = {"普通","高清","原画"};
    private DanmakuView mDanmakuView;
    private MyDanmaKuController mDanmaKuController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaozi_rtmp);
        initView();
        initPlayerUrl(MediaUrl.URL_RTMP);
        mIJKMediaSystem = new MyIJKMediaSystem();
    }

    /**
     * 初始化播放地址
     */
    private void initPlayerUrl(String url) {
        Object[] objects = new Object[3];
        LinkedHashMap map = new LinkedHashMap();
        for (int i = 0; i < 3; i++) {
            map.put(mediaName[i], url);
        }
        objects[0] = map;
        objects[1] = false;
        objects[2] = new HashMap<>();
        ((HashMap) objects[2]).put("key", "value");
        mPlayerStandard.setUp(objects, 0, JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
    }

    private void initView() {
//        mDanmakuView = findViewById(R.id.danmaku_view);
        mPlayerStandard = findViewById(R.id.jiaozi_player);

    }

    private void initDanmaKu() {
        ViewGroup vp = (ViewGroup) (JZUtils.scanForActivity(mPlayerStandard.getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        lp.setMargins(0, sp2px(48), 0, sp2px(48));
        mDanmakuView = new DanmakuView(this);
        vp.addView(mDanmakuView, lp);
        mDanmaKuController = new MyDanmaKuController(this, mDanmakuView);
        mDanmaKuController.initDanmaKu();
        mDanmaKuController.onHide();
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
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    //IJK播放器引擎
    MyIJKMediaSystem mIJKMediaSystem;
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
        JZVideoPlayer.setMediaInterface(mIJKMediaSystem);
        //Change these two variables back
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JZVideoPlayer.setMediaInterface(mIJKMediaSystem);
        initPlayer();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
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
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    /**倍速切换*/
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostSpeed(SpeedEvent event) {
        mIJKMediaSystem.setSpeeding(event.getSpeed());
        Toast.makeText(this, "正在切换倍速:"+event.getSpeed(), Toast.LENGTH_LONG).show();
    }

    /**下载*/
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostDetail(DownloadEvent event) {
        Toast.makeText(this, "下载", Toast.LENGTH_SHORT).show();
//        startDownload(MediaUrl.URL_M3U8);
    }

    /**弹幕开关*/
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostDetail(DanmaKuEvent event) {
        if (event.isSwitch()) {
            mDanmakuView.show();
        }else {
            mDanmakuView.hide();
        }
    }

    /**
     * 获取m3u8信息
     * @param mediaUrls
     */
    public void getm3u8Info(String mediaUrls) {
        M3U8InfoManger.getInstance().getM3U8Info(mediaUrls, new OnM3U8InfoListener() {
            @Override
            public void onSuccess(M3U8 m3U8) {
//                R.e("info:"+m3U8);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Throwable errorMsg) {
//                LogUtils.e("info:"+errorMsg.toString());
            }
        });
    }


    /**
     * 下载M3u8视频
     * @param mediaUrls
     */
    String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "/m3u8/download/video/";
    M3U8DownloadTask downloadTask = new M3U8DownloadTask("1001");
    long lastLength = 0L;
    private void startDownload(final String mediaUrls) {
        downloadTask.setSaveFilePath(path + "2");
        downloadTask.download(mediaUrls, new OnDownloadListener() {
            @Override
            public void onDownloading(long itemFileSize, int totalTs, int curTs) {

            }

            //下载完成
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.setText("已下载");

                    }
                });
            }

            //下载进度回调
            @Override
            public void onProgress(final long curLength) {
                if (curLength - lastLength > 0) {
                    //下载速度
                    final String speed = NetSpeedUtils.getInstance().displayFileSize(curLength - lastLength) + "/s";
//                    LogUtils.e(downloadTask.getTaskId() + "speed = " + speed);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgress.setText("正在下载"+(curLength+"").substring(0,2)+"%");

                        }
                    });
                    lastLength = curLength;

                }
            }

            //开始下载
            @Override
            public void onStart() {
                Toast.makeText(JiaoZiRtmpPlayerActivity.this, "开始下载", Toast.LENGTH_SHORT).show();
            }

            //下载出错
            @Override
            public void onError(Throwable errorMsg) {
                Toast.makeText(JiaoZiRtmpPlayerActivity.this, errorMsg.getMessage(), Toast.LENGTH_LONG).show();

            }

        });
    }

    /**
     * 停止下载
     */
    private void stopDownload() {
        downloadTask.stop();
    }
}
