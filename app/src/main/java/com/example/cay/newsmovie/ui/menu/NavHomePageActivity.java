package com.example.cay.newsmovie.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.databinding.ActivityNavHomePageBinding;
import com.example.cay.newsmovie.statusbar.StatusBarUtil;
import com.example.cay.newsmovie.utils.ShareUtils;


public class NavHomePageActivity extends AppCompatActivity {

    private ActivityNavHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nav_home_page);

        binding.toolbarLayout.setTitle(getString(R.string.app_name));
        StatusBarUtil.setTranslucentForImageView(this, 0, binding.toolbar);
        binding.fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.share(v.getContext(), R.string.string_share_text);
            }
        });
    }

    public static void startHome(Context mContext) {
        Intent intent = new Intent(mContext, NavHomePageActivity.class);
        mContext.startActivity(intent);
    }
}
