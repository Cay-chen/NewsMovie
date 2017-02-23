package com.example.cay.newsmovie.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.bean.PlayBean;

import java.util.List;

/**
 * Created by Cay on 2017/2/18.
 */

public class PlayAdapter extends BaseQuickAdapter<PlayBean,BaseViewHolder> {
    public PlayAdapter(int layoutResId, List<PlayBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayBean item) {
        helper.setText(R.id.tv_play_time, item.getTime())
                .setText(R.id.tv_play_content, item.getContent());
    }
}
