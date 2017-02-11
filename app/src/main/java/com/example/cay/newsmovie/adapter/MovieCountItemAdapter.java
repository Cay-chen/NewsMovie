package com.example.cay.newsmovie.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.bean.MovieBean;
import com.example.cay.newsmovie.player.PlayActivity;


import java.util.List;

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
                PlayActivity.actionStart(context,item.getMovieUrl(),item.getMovieName());
            }
        });
    }
}
