package com.example.cay.newsmovie.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.bean.NewsWeiXinBean;
import com.example.cay.newsmovie.webview.WebViewActivity;

import java.util.List;

/**
 * Created by Cay on 2017/2/11.
 */

public class NewsWeiXinAdapter extends BaseQuickAdapter<NewsWeiXinBean,BaseViewHolder> {
    private Context context;
    public NewsWeiXinAdapter(Context context,int layoutResId, List<NewsWeiXinBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final NewsWeiXinBean item) {
        helper.setText(R.id.tv_news_weixin_title, item.getTitle())
                .setText(R.id.tv_news_weixin_author, item.getSource());
        Glide.with(context).load(item.getFirstImg()).into((ImageView)helper.getView(R.id.iv_news_weixin_img));
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(context,item.getUrl(),"加载中……");
            }
        });
    }
}
