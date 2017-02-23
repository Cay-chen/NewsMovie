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

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MyPlayerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private JCVideoPlayerStandard jcVideoPlayerStandard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_player);
        Intent intent = getIntent();
        jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        jcVideoPlayerStandard.setUp(intent.getStringExtra("url")
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, intent.getStringExtra("name"));
        //JCVideoPlayerStandard.startFullscreen(this, JCVideoPlayerStandard.class, intent.getStringExtra("url"), intent.getStringExtra("name"));

        //jcVideoPlayerStandard.thumbImageView.setImageResource(R.mipmap.ic_launcher);
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
   /* @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        jcVideoPlayerStandard.startButton.performClick();
    }
}
