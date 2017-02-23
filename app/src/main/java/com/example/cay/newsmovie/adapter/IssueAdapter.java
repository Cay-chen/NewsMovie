package com.example.cay.newsmovie.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.bean.IssueBean;

import java.util.List;

/**
 * Created by Cay on 2017/2/16.
 */

public class IssueAdapter extends BaseQuickAdapter<IssueBean,BaseViewHolder> {
    public IssueAdapter(int layoutResId, List<IssueBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IssueBean item) {
        helper.setText(R.id.tv_issue_time, item.getTime())
                .setText(R.id.tv__issue_content, item.getContent());
        if (item.getIsFinsh() == 0) {
            helper.setVisible(R.id.iv_issue_isfinsh, false);
        } else {
            helper.setVisible(R.id.iv_issue_isfinsh, true);

        }

    }
}
