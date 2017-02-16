package com.example.cay.newsmovie.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.base.BaseActivity;
import com.example.cay.newsmovie.databinding.ActivityNavDownloadBinding;
import com.example.cay.newsmovie.utils.PerfectClickListener;
import com.example.cay.newsmovie.utils.QRCodeUtil;
import com.example.cay.newsmovie.utils.ShareUtils;


public class NavDownloadActivity extends BaseActivity<ActivityNavDownloadBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_download);
        showContentView();

        setTitle("扫码下载");
//        String url = "https://github.com/youlookwhat/CloudReader";
        String url = "https://fir.im/vision";
        QRCodeUtil.showThreadImage(this, url, bindingView.ivErweima, R.mipmap.icon);
        bindingView.tvShare.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ShareUtils.share(v.getContext(), R.string.string_share_text);
            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavDownloadActivity.class);
        mContext.startActivity(intent);
    }
}
