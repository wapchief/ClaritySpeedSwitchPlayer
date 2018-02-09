package com.wapchief.qiniuplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;

import static com.wapchief.qiniuplayer.MediaUrl.URL_RTMP;

/**
 * Created by wapchief on 2018/1/18.
 */

public class QiNiuPlayerActivity extends AppCompatActivity implements
        PLMediaPlayer.OnPreparedListener,
        PLMediaPlayer.OnInfoListener,
        PLMediaPlayer.OnCompletionListener,
        PLMediaPlayer.OnVideoSizeChangedListener,
        PLMediaPlayer.OnErrorListener,
        View.OnClickListener{

    private static final String TAG = "MainActivity";
    //控件
    PLVideoView mPLVideoView;
    //控制器
    MediaController mMediaController;
    //播放器对象
    PLMediaPlayer mMediaPlayer;

    //
    LinearLayout playerBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mPLVideoView =(PLVideoView)findViewById(R.id.player);
        playerBt = findViewById(R.id.player_root);
        playerBt.setOnClickListener(this);
        mMediaController = new MediaController(this);
        //关联播放器
        mPLVideoView.setMediaController(mMediaController);
        mPLVideoView.setVideoPath(URL_RTMP);
        //播放初始化
//        mMediaPlayer = new PLMediaPlayer(this);
//        mMediaPlayer.setOnPreparedListener(this);
//        mMediaPlayer.setOnInfoListener(this);
//        mMediaPlayer.setOnCompletionListener(this);
//        mMediaPlayer.setOnVideoSizeChangedListener(this);
//        mMediaPlayer.setOnErrorListener(this);
        //设置播放地址
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPLVideoView.stopPlayback();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPLVideoView.stopPlayback();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.player_root:
                Toast.makeText(this,"播放",Toast.LENGTH_SHORT).show();
                mPLVideoView.start();
                break;
        }
    }
    @Override
    public void onCompletion(PLMediaPlayer plMediaPlayer) {

    }

    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
        return false;
    }

    @Override
    public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(PLMediaPlayer plMediaPlayer, int i) {

    }

    @Override
    public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int i, int i1) {

    }
}
