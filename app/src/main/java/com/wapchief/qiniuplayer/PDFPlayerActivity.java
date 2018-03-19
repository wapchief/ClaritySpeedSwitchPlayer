package com.wapchief.qiniuplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

/**
 * @author wapchief
 * @date 2018/3/7
 */

public class PDFPlayerActivity extends AppCompatActivity implements DownloadFile.Listener {

    private String TAG = "PDFPlayerActivity";
    private RemotePDFViewPager mWebView;
    private PDFPagerAdapter adapter;
    private String mUrl = "http://ese1a1b2c8d5xn.pri.qiqiuyun.net/course-activity-413/20180307101410-h48w9qtklog0goc4/1d02580e4e1f4435_pdf?e=1521015992&token=ExRD5wolmUnwwITVeSEXDQXizfxTRp7vnaMKJbO-:U5RQhZwEyHoxfz0V-euxcLFfHiI=";
    private RelativeLayout pdf_root;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        initView();
        initData();
    }

    private void initData() {
        final DownloadFile.Listener listener = this;
        mWebView = new RemotePDFViewPager(this, mUrl, listener);
        mWebView.setId(R.id.pdf_web);

    }

    private void initView() {
        pdf_root = (RelativeLayout) findViewById(R.id.remote_pdf_root);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        mWebView.setAdapter(adapter);
        updateLayout();
    }

    @Override
    public void onFailure(Exception e) {
        Log.e(TAG, "onFailure:" + e.toString());
    }

    /*更新视图*/
    private void updateLayout() {
        pdf_root.removeAllViewsInLayout();
        pdf_root.addView(mWebView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onProgressUpdate(int progress, int total) {
        Log.e(TAG, "progress:" + progress+",total:"+total);
    }


}
