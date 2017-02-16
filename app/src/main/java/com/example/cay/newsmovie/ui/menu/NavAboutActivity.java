package com.example.cay.newsmovie.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.base.BaseActivity;
import com.example.cay.newsmovie.databinding.ActivityNavAboutBinding;
import com.example.cay.newsmovie.utils.PerfectClickListener;
import com.example.cay.newsmovie.webview.WebViewActivity;


public class NavAboutActivity extends BaseActivity<ActivityNavAboutBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_about);
        showContentView();
        setTitle("关于V视");


        // 直接写在布局文件里会很耗内存
        Glide.with(this).load(R.mipmap.icon).into(bindingView.ivIcon);
        initListener();
    }

    private void initListener() {


        bindingView.tvFunction.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
           /*     Uri issuesUrl = Uri.parse("http://jingbin.me/2016/12/30/%E6%9B%B4%E6%96%B0%E6%97%A5%E5%BF%97-%E4%BA%91%E9%98%85/");
                Intent intent = new Intent(Intent.ACTION_VIEW, issuesUrl);
                startActivity(intent);*/
            }
        });

//        bindingView.tvFunction.setOnClickListener(listener);
        bindingView.tvNewVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // ToastUtil.showToast("已是最新版本~");
                Uri issuesUrl = Uri.parse("https://fir.im/vision");
                Intent intent = new Intent(Intent.ACTION_VIEW, issuesUrl);
                startActivity(intent);
            }
        });
    }

    private PerfectClickListener listener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {
            String url = null;
            switch (v.getId()) {

                case R.id.tv_function:// 更新日志
                    url = "http://jingbin.me/2016/12/30/%E6%9B%B4%E6%96%B0%E6%97%A5%E5%BF%97-%E4%BA%91%E9%98%85/";
                    break;
            }
         //   WebViewActivity.loadUrl(v.getContext(), url, "加载中...");
        }
    };

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavAboutActivity.class);
        mContext.startActivity(intent);
    }
}
