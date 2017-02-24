package com.example.cay.newsmovie.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.PlayAdapter;
import com.example.cay.newsmovie.bean.PlayBean;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MyPlayerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_player);
        Intent intent = getIntent();
        MyJcPlayer jcVideoPlayerStandard = (MyJcPlayer) findViewById(R.id.videoplayer);
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

    @Override
    protected void onPause() {
        super.onPause();
        JCMediaManager.instance().mediaPlayer.pause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
    }
}
