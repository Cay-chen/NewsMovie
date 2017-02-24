package com.example.cay.newsmovie.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.MyFragmentPagerAdapter;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.databinding.FragmentMovieHomeBinding;


import java.util.ArrayList;

/**
 * ss
 * Created by Cay on 2017/2/3.
 */

public class NewsFragment extends BaseFragment<FragmentMovieHomeBinding> {
    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @Override
    public int setContent() {
        return  R.layout.fragment_movie_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻3个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);

        bindingView.vpGank.setAdapter(myAdapter);
        // 左右预加载页面的个数
        bindingView.vpGank.setOffscreenPageLimit(10);
        myAdapter.notifyDataSetChanged();
        bindingView.tabGank.setTabMode(TabLayout.MODE_SCROLLABLE);
        bindingView.tabGank.setupWithViewPager(bindingView.vpGank);
        showContentView();

    }

    @Override
    protected void loadData() {

    }

    private void initFragmentList() {
        mTitleList.add("头条");
        mTitleList.add("微信精选");
        mTitleList.add("社会");
        mTitleList.add("国内");
        mTitleList.add("国际");
        mTitleList.add("娱乐");
        mTitleList.add("体育");
        mTitleList.add("军事");
        mTitleList.add("科技");
        mTitleList.add("财经");
        mTitleList.add("时尚");
        mFragments.add(NewsChildFragment.newInstance("top"));
        mFragments.add(new WeiXinFragment());
        mFragments.add(NewsChildFragment.newInstance("shehui"));
        mFragments.add(NewsChildFragment.newInstance("guonei"));
        mFragments.add(NewsChildFragment.newInstance("guoji"));
        mFragments.add(NewsChildFragment.newInstance("yule"));
        mFragments.add(NewsChildFragment.newInstance("tiyu"));
        mFragments.add(NewsChildFragment.newInstance("junshi"));
        mFragments.add(NewsChildFragment.newInstance("keji"));
        mFragments.add(NewsChildFragment.newInstance("caijing"));
        mFragments.add(NewsChildFragment.newInstance("shishang"));
    }

}
