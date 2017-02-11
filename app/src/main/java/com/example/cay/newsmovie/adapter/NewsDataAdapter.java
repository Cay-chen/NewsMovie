package com.example.cay.newsmovie.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.base.adapter.MultipleItemNews;
import com.example.cay.newsmovie.webview.WebViewActivity;


import java.util.List;

/**
 * Created by Cay on 2017/2/10.
 */

public class NewsDataAdapter extends BaseMultiItemQuickAdapter<MultipleItemNews,BaseViewHolder> {
    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewsDataAdapter(Context context,List<MultipleItemNews> data) {
        super(data);
        this.context = context;
        addItemType(MultipleItemNews.TRE3, R.layout.news_three_item);
        addItemType(MultipleItemNews.TWO2, R.layout.news_two_item);
        addItemType(MultipleItemNews.ONE1, R.layout.news_one_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MultipleItemNews item) {
       switch (helper.getItemViewType()) {
            case MultipleItemNews.ONE1:
                helper.setText(R.id.tv_news_item_one_author,item.getAuthor_name())
                        .setText(R.id.tv_news_item_one_data,item.getDate())
                        .setText(R.id.tv_news_item_one_title,item.getTitle());
                Glide.with(context).load(item.getThumbnail_pic_s()).into((ImageView) helper.getView(R.id.iv_news_item_one_img));
                break;
            case MultipleItemNews.TWO2:
                helper.setText(R.id.tv_news_item_two_author,item.getAuthor_name())
                        .setText(R.id.tv_news_item_two_data,item.getDate())
                        .setText(R.id.tv_news_item_two_title,item.getTitle());
                Glide.with(context).load(item.getThumbnail_pic_s()).into((ImageView) helper.getView(R.id.iv_news_item_two_img1));
                Glide.with(context).load(item.getThumbnail_pic_s02()).into((ImageView) helper.getView(R.id.iv_news_item_two_img2));
                break;
            case MultipleItemNews.TRE3:
                helper.setText(R.id.tv_news_item_three_author,item.getAuthor_name())
                        .setText(R.id.tv_news_item_three_data,item.getDate())
                        .setText(R.id.tv_news_item_three_title,item.getTitle());
                Glide.with(context).load(item.getThumbnail_pic_s()).into((ImageView) helper.getView(R.id.iv_news_item_three_img1));
                Glide.with(context).load(item.getThumbnail_pic_s02()).into((ImageView) helper.getView(R.id.iv_news_item_three_img2));
                Glide.with(context).load(item.getThumbnail_pic_s03()).into((ImageView) helper.getView(R.id.iv_news_item_three_img3));
                break;
        }
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(context,item.getUrl(),item.getTitle());
            }
        });
    }

}
