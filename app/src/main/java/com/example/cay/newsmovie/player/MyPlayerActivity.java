package com.example.cay.newsmovie.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.PlayAdapter;
import com.example.cay.newsmovie.bean.CommentDataBean;
import com.example.cay.newsmovie.bean.UpDdtaBackBean;
import com.example.cay.newsmovie.http.HttpUtils;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyPlayerActivity extends AppCompatActivity implements Runnable ,BaseQuickAdapter.RequestLoadMoreListener{
    private static final String TAG = "Cay";
    private RecyclerView mRecyclerView;
    private static final int TIME = 1;
    private static final int BATTERY = 2;
    private boolean isRun = true;
    private String nowPosition = "0";
    private String FIRST_NUM = "15";
    private String LOAD_NUM = "10";
    private boolean isFirst = true;
    private String name;
    private View errorView;
    private View empetView;
    private PlayAdapter playAdapter;
    private MyJcPlayer jcVideoPlayerStandard;
   /* private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    Log.i(TAG, "handleMessage:TIME --"+msg.obj.toString());
                    jcVideoPlayerStandard.setTime(msg.obj.toString());
                    break;
                case BATTERY:
                    Log.i(TAG, "handleMessage:BATTERY --"+msg.obj.toString());
                    jcVideoPlayerStandard.setBattery(msg.obj.toString());
                    break;
            }
        }
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_my_player);
        Intent intent = getIntent();
        name=intent.getStringExtra("name");
        jcVideoPlayerStandard = (MyJcPlayer) findViewById(R.id.videoplayer);
        jcVideoPlayerStandard.setUp(intent.getStringExtra("url")
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, name);
        jcVideoPlayerStandard.startButton.performClick();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_play);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyclerView.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        empetView = View.inflate(this, R.layout.layout_play_emput, null);
        loadData(name, nowPosition, FIRST_NUM, false);
        //registerBoradcastReceiver();
       // new Thread(this).start();
        final EditText editText = (EditText) findViewById(R.id.et_player);
        Button button = (Button) findViewById(R.id.btn_player);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(MyPlayerActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtils.getInstance().getMyObservableClient().upCommentData(name,editText.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<UpDdtaBackBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(UpDdtaBackBean value) {
                                    if (value.getResMsg().equals("1")) {
                                        Toast.makeText(MyPlayerActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                                        editText.setText("");
                                        nowPosition = "0";
                                        loadData(name, nowPosition, FIRST_NUM, false);

                                    } else {
                                        Toast.makeText(MyPlayerActivity.this, "发表异常", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(MyPlayerActivity.this, "发表失败", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }
        });
    }

    public static void star(Context context, String movie_url, String name) {
        Intent intent = new Intent(context, MyPlayerActivity.class);
        intent.putExtra("url", movie_url);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    public void loadData(String name, String position, String num, final boolean isLoad) {
        HttpUtils.getInstance().getMyObservableClient().getCommentData(name,position,num).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CommentDataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CommentDataBean> list) {
                        if (isFirst) {
                            playAdapter = new PlayAdapter(R.layout.play_talk_item, list);
                            playAdapter.setOnLoadMoreListener(MyPlayerActivity.this);
                            mRecyclerView.setAdapter(playAdapter);
                            isFirst = false;
                            if (list.size() ==0) {
                                playAdapter.setEmptyView(empetView);
                            }
                            if (list.size() < 15) {
                                playAdapter.loadMoreEnd(true);
                            }
                            nowPosition = String.valueOf(list.size());
                        } else {
                            if (isLoad) {
                                playAdapter.addData(list);
                                playAdapter.setEnableLoadMore(true);
                                playAdapter.loadMoreComplete();
                                if (list.size() < 10) {
                                    playAdapter.loadMoreEnd(false);
                                }
                                nowPosition = String.valueOf(playAdapter.getData().size());
                            } else {
                                playAdapter.setNewData(list);
                                playAdapter.loadMoreComplete();
                                if (list.size() ==0) {
                                    playAdapter.setEmptyView(empetView);
                                }
                                if (list.size() < 15) {
                                    playAdapter.loadMoreEnd(true);
                                }
                                nowPosition = String.valueOf(playAdapter.getData().size());
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        playAdapter.setEmptyView(errorView);
                    }
                    @Override
                    public void onComplete() {

                    }
                });
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
 /*   //注册一个广播，接收电池电量改变
    private BroadcastReceiver batteryBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                //获取当前电量
                int level = intent.getIntExtra("level", 0);
                //电量的总刻度
                Log.i(TAG, "onReceive:level-- "+level);
                int scale = intent.getIntExtra("scale", 100);
                //把它转成百分比
                //tv.setText("电池电量为"+((level*100)/scale)+"%");

                String batt = (level * 100) / scale + "";
                RxBus.getDefault().send(RxCodeConstants.JUMP_BATT,batt);

              *//*  Message msg = new Message();
                msg.obj = (level * 100) / scale + "";
                msg.what = BATTERY;
                mHandler.sendMessage(msg);*//*
            }
        }
    };*/

 /*   public void registerBoradcastReceiver() {
        //注册电量广播监听电池电量改变
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryBroadcastReceiver, intentFilter);

    }*/

    @Override
    protected void onPause() {
        super.onPause();
        if (JCMediaManager.instance().mediaPlayer.getCurrentPosition() < 50) {
            JCMediaManager.instance().mediaPlayer.reset();
        } else {
            JCMediaManager.instance().mediaPlayer.pause();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
        isRun= false;
    }

    @Override
    public void run() {
      /*  while (isRun) {
            //读取线程
            Log.i(TAG, "run: ");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String str = sdf.format(new Date());

            RxBus.getDefault().send(RxCodeConstants.JUMP_TIME,str);
          *//*  Message msg = new Message();
            msg.obj = str;
            msg.what = TIME;
            mHandler.sendMessage(msg);*//*
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }*/
    }


    @Override
    public void onLoadMoreRequested() {
        loadData(name, nowPosition, LOAD_NUM, true);

    }
}
