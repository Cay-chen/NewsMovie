package com.example.cay.newsmovie.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.bean.IssueBean;

import java.util.List;

/**
 * Created by Cay on 2017/2/16.
 */

public class GetMovieAdapter extends BaseQuickAdapter<IssueBean,BaseViewHolder> {
    private Context context;
    public GetMovieAdapter(int layoutResId, List<IssueBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IssueBean item) {
        helper.setText(R.id.tv_movie_time, item.getTime())
                .setText(R.id.tv_movie_name, item.getContent());
        if (item.getIsFinsh() == 1) {
            helper.setImageResource(R.id.iv_movie_pass, R.drawable.movie_pass);
            helper.setVisible(R.id.iv_movie_pass, true);
        }
        if (item.getIsFinsh() == 2) {
            helper.setImageResource(R.id.iv_movie_pass, R.drawable.movie_refuse);
            helper.setVisible(R.id.iv_movie_pass, true);
        }

    }
}
