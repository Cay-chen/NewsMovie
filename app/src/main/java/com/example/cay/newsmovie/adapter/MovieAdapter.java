package com.example.cay.newsmovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.ui.activity.MovieDetailActivity;
import com.example.cay.newsmovie.bean.MovieDataBean;

import java.util.List;

/**
 * Created by Cay on 2017/2/14.
 */

public class MovieAdapter extends BaseQuickAdapter<MovieDataBean,BaseViewHolder> {
    private Context context;
    private ImageView mImageView;
    public MovieAdapter(Context context,int layoutResId, List<MovieDataBean> data) {
        super(layoutResId, data);
        this.context =context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MovieDataBean item) {
        helper.setText(R.id.tv_grid_movie, item.getName());
        mImageView =  helper.getView(R.id.iv_grid_movie_item);
        Glide.with(context).load(item.getImg_url()).into(mImageView);
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MovieDetailActivity.start((Activity)context,item.getId(),item.getImg_url(),(ImageView) helper.getView(R.id.iv_grid_movie_item));
            }
        });
    }


}
