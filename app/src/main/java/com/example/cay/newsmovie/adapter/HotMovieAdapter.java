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
import com.example.cay.newsmovie.bean.HotMovieBean;

import java.util.List;

/**热映Adapter
 * Created by Cay on 2017/2/17.
 */

public class HotMovieAdapter extends BaseQuickAdapter<HotMovieBean,BaseViewHolder>{
    private Context context;
    private ImageView mImageView;
    public HotMovieAdapter(Context context,int layoutResId, List<HotMovieBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HotMovieBean item) {
        if (helper.getAdapterPosition() < 3) {
            helper.setTextColor(R.id.tv_hot_movie_item_num, context.getResources().getColor(R.color.colorTheme))
                    .setTextColor(R.id.tv_hot_movie_item_name, context.getResources().getColor(R.color.colorTheme));
        } else {
            helper.setTextColor(R.id.tv_hot_movie_item_num, context.getResources().getColor(R.color.colorSubtitle))
                    .setTextColor(R.id.tv_hot_movie_item_name, context.getResources().getColor(R.color.colorSubtitle));
        }
        helper.setText(R.id.tv_hot_movie_item_num, String.valueOf((helper.getAdapterPosition() + 1)))
                .setText(R.id.tv_hot_movie_item_name, item.getName());
        mImageView=helper.getView(R.id.tv_hot_movie_item_img);
        Glide.with(context).load(item.getImg_url()).into(mImageView);
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView=helper.getView(R.id.tv_hot_movie_item_img);
                MovieDetailActivity.start((Activity) context,item.getMovie_id(),item.getImg_url(),mImageView);
            }
        });
    }
}
