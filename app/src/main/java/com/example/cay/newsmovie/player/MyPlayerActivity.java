package com.example.cay.newsmovie.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.PlayAdapter;
import com.example.cay.newsmovie.bean.PlayBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MyPlayerActivity extends AppCompatActivity implements Runnable {
    private RecyclerView mRecyclerView;
    private static final int TIME = 1;
    private static final int BATTERY = 2;
    private MyJcPlayer jcVideoPlayerStandard;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    jcVideoPlayerStandard.mTime.setText(msg.obj.toString());
                    break;
                case BATTERY:
                    setBattery(msg.obj.toString());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_player);
        Intent intent = getIntent();
        jcVideoPlayerStandard = (MyJcPlayer) findViewById(R.id.videoplayer);
        jcVideoPlayerStandard.setUp(intent.getStringExtra("url")
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, intent.getStringExtra("name"));
        jcVideoPlayerStandard.startButton.performClick();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_play);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        List<PlayBean> list = new ArrayList<>();
        PlayAdapter playAdapter = new PlayAdapter(R.layout.play_talk_item, list);
        mRecyclerView.setAdapter(playAdapter);
        View view = View.inflate(this, R.layout.layout_play_emput, null);
        playAdapter.setEmptyView(view);
        registerBoradcastReceiver();
        new Thread(this).start();
    }

    public static void star(Context context, String movie_url, String name) {
        Intent intent = new Intent(context, MyPlayerActivity.class);
        intent.putExtra("url", movie_url);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }


 @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
     super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        JCMediaManager.instance().mediaPlayer.start();

    }
    //注册一个广播，接收电池电量改变
    private BroadcastReceiver batteryBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                //获取当前电量
                int level = intent.getIntExtra("level", 0);
                //电量的总刻度
                int scale = intent.getIntExtra("scale", 100);
                //把它转成百分比
                //tv.setText("电池电量为"+((level*100)/scale)+"%");
                Message msg = new Message();
                msg.obj = (level * 100) / scale + "";
                msg.what = BATTERY;
                mHandler.sendMessage(msg);
            }
        }
    };

    public void registerBoradcastReceiver() {
        //注册电量广播监听电池电量改变
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryBroadcastReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        JCMediaManager.instance().mediaPlayer.pause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
        try {
            unregisterReceiver(batteryBroadcastReceiver);
        } catch (IllegalArgumentException ex) {

        }
    }

    @Override
    public void run() {
        while (true) {
            //读取线程
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String str = sdf.format(new Date());
            Message msg = new Message();
            msg.obj = str;
            msg.what = TIME;
            mHandler.sendMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    //显示电量，
    public void setBattery(String stringBattery){
        if(jcVideoPlayerStandard.mBatt != null && jcVideoPlayerStandard.mImgBatt != null){
            jcVideoPlayerStandard.mBatt.setText( stringBattery + "%");
            int battery = Integer.valueOf(stringBattery);
            if(battery < 5)jcVideoPlayerStandard.mImgBatt.setImageDrawable(getResources().getDrawable(R.drawable.battery_0));
            if(battery < 35 && battery >= 5)jcVideoPlayerStandard.mImgBatt.setImageDrawable(getResources().getDrawable(R.drawable.battery_1));
            if(battery < 60 && battery >=35)jcVideoPlayerStandard.mImgBatt.setImageDrawable(getResources().getDrawable(R.drawable.battery_2));
            if(battery < 85 && battery >= 60)jcVideoPlayerStandard.mImgBatt.setImageDrawable(getResources().getDrawable(R.drawable.battery_3));
            if(battery >= 85 )jcVideoPlayerStandard.mImgBatt.setImageDrawable(getResources().getDrawable(R.drawable.battery_4));
        }
    }

}
