package com.example.cay.newsmovie.player;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.cay.newsmovie.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Cay-chen on 2017/2/25.
 */

public class MyJcPlayer extends JCVideoPlayerStandard {
    public MyJcPlayer(Context context) {
        super(context);
    }

    public MyJcPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void init(Context context) {
        super.init(context);


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
