package com.example.cay.newsmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.bean.MovieBean;
import com.example.cay.newsmovie.player.MyPlayerActivity;
import com.example.cay.newsmovie.player.PlayActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.List;

import okhttp3.Call;

/**
 * Created by Cay on 2017/2/5.
 */

public class MovieCountItemAdapter extends BaseQuickAdapter<MovieBean,BaseViewHolder> {
    private Context context;

    public MovieCountItemAdapter(int layoutResId, List<MovieBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }
    @Override
    protected void convert(final BaseViewHolder helper, final MovieBean item) {
        helper.setText(R.id.movie_count_btn, item.getItemName());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upCountMoviePlayernum(item.getMovieId(),item.getAllName(),item.getImg_url());
                if (item.getType() == 1) {
                    MyPlayerActivity.star(context,item.getMovieUrl(),item.getMovieName());
                   // PlayActivity.actionStart(context, item.getMovieUrl(), item.getMovieName());
                } else {
                    Uri issuesUrl = Uri.parse(item.getMovieUrl().trim());
                    Intent intent = new Intent(Intent.ACTION_VIEW, issuesUrl);
                    context.startActivity(intent);
                }
            }
        });
    }

    public void upCountMoviePlayernum(String id, String name, String img_url) {
        OkHttpUtils.get().url("http://60.205.183.88:8080/VMovie/ServerCountMoviePlayerNum").addParams("name",name).addParams("movie_id",id).addParams("img_url",img_url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

            }
        });
    }
}
