package com.example.cay.newsmovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.ui.activity.MovieDetailActivity;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.example.cay.newsmovie.utils.CommonUtils;
import com.example.cay.newsmovie.utils.PerfectClickListener;

import java.util.List;


/**
 * Created by jingbin on 2016/11/25.
 */

public class MovieDetailsAdapter extends BaseQuickAdapter<MovieDataBean,BaseViewHolder> {
    private ImageView mImageView;
    private Context context;
    private ImageView mImageView1;
    public MovieDetailsAdapter(int layoutResId, List data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MovieDataBean item) {
                helper.setText(R.id.tv_one_title,item.getName())
                        .setText(R.id.tv_one_directors,item.getDirector())
                        .setText(R.id.tv_one_casts,item.getAct())
                        .setText(R.id.tv_one_genres,context.getResources().getString(R.string.string_type)+item.getMovie_type())
                        .setBackgroundColor(R.id.view_color, CommonUtils.randomColor())
                        .setText(R.id.tv_one_rating_rate,context.getResources().getString(R.string.string_rating)+item.getCode());
                        mImageView =helper.getView(R.id.iv_one_photo);
        Glide.with(context).load(item.getImg_url()).crossFade().into(mImageView);

      /*  ViewHelper.setScaleX(helper.itemView,0.8f);
        ViewHelper.setScaleY(helper.itemView,0.8f);
        ViewPropertyAnimator.animate(helper.itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
        ViewPropertyAnimator.animate(helper.itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();*/
        LinearLayout mLinearLayout =helper.getView(R.id.ll_one_item);
        mLinearLayout.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                mImageView1=helper.getView(R.id.iv_one_photo);
            MovieDetailActivity.start((Activity) context, item.getId(),item.getImg_url(),mImageView1);

            }
        });

    }

}
