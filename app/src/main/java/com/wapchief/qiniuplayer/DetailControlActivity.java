package com.wapchief.qiniuplayer;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYVideoGifSaveListener;
import com.shuyu.gsyvideoplayer.listener.GSYVideoShotListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.FileUtils;
import com.shuyu.gsyvideoplayer.utils.GifCreateHelper;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.wapchief.qiniuplayer.views.SampleControlVideo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * sampleVideo支持全屏与非全屏切换的清晰度，旋转，镜像等功能.
 * Activity可以继承GSYBaseActivityDetail实现详情模式的页面
 * 或者参考DetailPlayer、DetailListPlayer实现
 * <p>
 * Created by guoshuyu on 2017/6/18.
 */

public class DetailControlActivity extends GSYVideoPlayerActivity {

    NestedScrollView postDetailNestedScroll;

    SampleControlVideo detailPlayer;

    RelativeLayout activityDetailPlayer;

    Button changeSpeed;


    Button jump;

    Button shot;

    Button startGif;

    Button stopGif;

    View loadingView;

    private String url = "https://res.exexm.com/cw_145225549855002";

    private GifCreateHelper mGifCreateHelper;

    private float speed = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_control);
        initView();

        resolveNormalVideoUI();

//        initVideoBuilderMode();

        initGifHelper();

        detailPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                //if (orientationUtils != null) {
                //配合下方的onConfigurationChanged
                //orientationUtils.setEnable(!lock);
                //}
            }
        });

        changeSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveTypeUI();
            }
        });


        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailControlActivity.this, DetailControlActivity.class);
                startActivity(intent);
                //startActivity(new Intent(DetailControlActivity.this, MainActivity.class));
            }
        });

        shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shotImage(v);
            }
        });


        startGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGif();
            }
        });

        stopGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopGif();
            }
        });

        loadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing
            }
        });
    }

    private void initView() {
        postDetailNestedScroll = findViewById(R.id.post_detail_nested_scroll);
        detailPlayer = findViewById(R.id.detail_player);
        activityDetailPlayer = findViewById(R.id.activity_detail_player);
        changeSpeed = findViewById(R.id.change_speed);
        jump = findViewById(R.id.jump);
        shot = findViewById(R.id.shot);
        startGif = findViewById(R.id.start_gif);
        stopGif = findViewById(R.id.stop_gif);
        loadingView = findViewById(R.id.loadingView);

        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        VideoModel model = new VideoModel( "普通",source1);
        VideoModel mode2 = new VideoModel("标清", source1);
        VideoModel mode3 = new VideoModel("高清", source1);
        List<VideoModel> models = new ArrayList<>();
        models.add(model);
        models.add(mode2);
        models.add(mode3);

        detailPlayer.setUp(models, true, "");
    }

    @Override
    public GSYBaseVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //内置封面可参考SampleCoverVideo
        ImageView imageView = new ImageView(this);
        loadCover(imageView, url);
        return new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(url)
                .setCacheWithPlay(true)
                .setVideoTitle(" ")
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(true)//打开动画
                .setNeedLockFull(true)
                .setSeekRatio(1);
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGifCreateHelper.cancelTask();
    }


    /*******************************竖屏全屏开始************************************/

    @Override
    public void initVideo() {
        super.initVideo();
        //重载后实现点击，不横屏
        if (getGSYVideoPlayer().getFullscreenButton() != null) {
            getGSYVideoPlayer().getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                    getGSYVideoPlayer().startWindowFullscreen(DetailControlActivity.this, true, true);
                }
            });
        }
    }


    /**
     * 是否启动旋转横屏，true表示启动
     *
     * @return true
     */
    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    //重载后关闭重力旋转
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientationUtils.setEnable(false);
    }

    //重载后不做任何事情，实现竖屏全屏
    @Override
    public void onQuitFullscreen(String url, Object... objects) {
        super.onQuitFullscreen(url, objects);
    }

    /*******************************竖屏全屏结束************************************/

    private void initGifHelper() {
        mGifCreateHelper = new GifCreateHelper(detailPlayer, new GSYVideoGifSaveListener() {
            @Override
            public void result(boolean success, File file) {
                detailPlayer.post(new Runnable() {
                    @Override
                    public void run() {
                        loadingView.setVisibility(View.GONE);
                        Toast.makeText(detailPlayer.getContext(), "创建成功", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void process(int curPosition, int total) {
                Debuger.printfError(" current " + curPosition + " total " + total);
            }
        });
    }


    /**
     * 开始gif截图
     */
    private void startGif() {
        //开始缓存各个帧
        mGifCreateHelper.startGif(new File(FileUtils.getPath()));

    }

    /**
     * 生成gif
     */
    private void stopGif() {
        loadingView.setVisibility(View.VISIBLE);
        mGifCreateHelper.stopGif(new File(FileUtils.getPath(), "GSY-Z-" + System.currentTimeMillis() + ".gif"));
    }


    /**
     * 视频截图
     */
    private void shotImage(final View v) {
        //获取截图
        detailPlayer.taskShotPic(new GSYVideoShotListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                if (bitmap != null) {
                    try {
                        saveBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        showToast("save fail ");
                        e.printStackTrace();
                        return;
                    }
                    showToast("save success ");
                } else {
                    showToast("get bitmap fail ");
                }
            }
        });

    }


    private void loadCover(ImageView imageView, String url) {

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_launcher);

        Glide.with(this.getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                .error(R.mipmap.ic_launcher)
                                .placeholder(R.mipmap.ic_launcher))
                .load(url)
                .into(imageView);
    }

    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);
    }

    /**
     * 显示比例
     * 注意，GSYVideoType.setShowType是全局静态生效，除非重启APP。
     */
    private void resolveTypeUI() {
        if (speed == 1) {
            speed = 1.5f;
        } else if (speed == 1.5f) {
            speed = 2f;
        } else if (speed == 2) {
            speed = 0.5f;
        } else if (speed == 0.5f) {
            speed = 0.25f;
        } else if (speed == 0.25f) {
            speed = 1;
        }
        changeSpeed.setText("播放速度：" + speed);
        detailPlayer.setSpeedPlaying(speed, true);
    }


    private void showToast(final String tip) {
        detailPlayer.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DetailControlActivity.this, tip, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void saveBitmap(Bitmap bitmap) throws FileNotFoundException {
        if (bitmap != null) {
            File file = new File(FileUtils.getPath(), "GSY-" + System.currentTimeMillis() + ".jpg");
            OutputStream outputStream;
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            bitmap.recycle();
        }
    }

}
