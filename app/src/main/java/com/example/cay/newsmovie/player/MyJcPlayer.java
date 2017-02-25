package com.example.cay.newsmovie.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cay.newsmovie.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Cay-chen on 2017/2/25.
 */

public class MyJcPlayer extends JCVideoPlayerStandard {
    public TextView mTime;
    public TextView mBatt;
    public ImageView mImgBatt;

    public MyJcPlayer(Context context) {
        super(context);
    }

    public MyJcPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void init(Context context) {
        super.init(context);
        mTime = (TextView) findViewById(R.id.mediacontroller_time);
        mBatt = (TextView) findViewById(R.id.mediacontroller_Battery);
        mImgBatt = (ImageView) findViewById(R.id.mediacontroller_imgBattery);

    }


    @Override
    public int getLayoutId() {
        return R.layout.my_jcplayer;
    }



    @Override
    public void setUp(String url, int screen, Object... objects) {
        //制全屏
        FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        super.setUp(url, screen, objects);
    }
}
