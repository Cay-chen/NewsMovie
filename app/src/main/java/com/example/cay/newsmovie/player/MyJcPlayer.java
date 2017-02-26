package com.example.cay.newsmovie.player;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.http.RxBus.RxBus;
import com.example.cay.newsmovie.http.RxBus.RxCodeConstants;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Cay-chen on 2017/2/25.
 */

public class MyJcPlayer extends JCVideoPlayerStandard {
    private static final String TAG = "Cay";
    private TextView mTime;
    private TextView mBatt;
    private ImageView mImgBatt;

    public MyJcPlayer(Context context) {
        super(context);
      //  initRxBus();
        Log.i(TAG, "MyJcPlayer: 1");

    }

    public MyJcPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        //initRxBus();
        Log.i(TAG, "MyJcPlayer:2 ");

    }
    @Override
    public void init(Context context) {
        super.init(context);
        Log.i(TAG, "init: ");
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
       /* if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            mTime.setVisibility(View.VISIBLE);
            mBatt.setVisibility(View.VISIBLE);
            mImgBatt.setVisibility(View.VISIBLE);
        } else {
            mTime.setVisibility(View.INVISIBLE);
            mBatt.setVisibility(View.INVISIBLE);
            mImgBatt.setVisibility(View.INVISIBLE);

        }*/
    }

    public void setTime(String time) {
        Log.i(TAG, "setTime: "+time);

        if (time != null) {
            mTime.setText(time);
        }
    }

    //显示电量，
    public void setBattery(String stringBattery){
        Log.i(TAG, "setBattery: "+stringBattery);
        if(mBatt != null && mImgBatt != null){
            mBatt.setText( stringBattery + "%");
            int battery = Integer.valueOf(stringBattery);
            if(battery < 5)mImgBatt.setImageDrawable(getResources().getDrawable(R.drawable.battery_0));
            if(battery < 35 && battery >= 5)mImgBatt.setImageDrawable(getResources().getDrawable(R.drawable.battery_1));
            if(battery < 60 && battery >=35)mImgBatt.setImageDrawable(getResources().getDrawable(R.drawable.battery_2));
            if(battery < 85 && battery >= 60)mImgBatt.setImageDrawable(getResources().getDrawable(R.drawable.battery_3));
            if(battery >= 85 )mImgBatt.setImageDrawable(getResources().getDrawable(R.drawable.battery_4));
        }
    }

   /* private void initRxBus() {
        RxBus.getDefault().toObservable(RxCodeConstants.JUMP_BATT, String.class)
                .subscribe(new Observer() {
                               @Override
                               public void onSubscribe(Disposable d) {
                               }
                               @Override
                               public void onNext(Object value) {
                                   setBattery((String) value);
                               }
                               @Override
                               public void onError(Throwable e) {

                               }
                               @Override
                               public void onComplete() {
                               }
                           }

                );


        RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TIME, String.class)
                .subscribe(new Observer() {
                               @Override
                               public void onSubscribe(Disposable d) {
                               }
                               @Override
                               public void onNext(Object value) {
                                    setTime((String) value);
                               }
                               @Override
                               public void onError(Throwable e) {

                               }
                               @Override
                               public void onComplete() {
                               }
                           }

                );

    }*/
}
