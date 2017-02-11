package com.example.cay.newsmovie.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;


import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.MyFragmentPagerAdapter;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.databinding.FragmentGankBinding;
import com.example.cay.newsmovie.utils.rx.RxBus;
import com.example.cay.newsmovie.utils.rx.RxCodeConstants;

import java.util.ArrayList;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Cay on 2017/2/3.
 */

public class GankFragment extends BaseFragment<FragmentGankBinding> {
    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    @Override
    public int setContent() {
        return  R.layout.fragment_gank;
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
        bindingView.vpGank.setOffscreenPageLimit(3);
        myAdapter.notifyDataSetChanged();
        bindingView.tabGank.setTabMode(TabLayout.MODE_FIXED);
        bindingView.tabGank.setupWithViewPager(bindingView.vpGank);
        showContentView();
        initRxBus();
    }
    private void initFragmentList() {
        mTitleList.add("每日推荐");
        mTitleList.add("电影");
        mTitleList.add("电视剧");
        mTitleList.add("动漫");
        mFragments.add(new EverydayFragment());
        mFragments.add(new WelfareFragment());
        mFragments.add(new CustomFragment());
        mFragments.add(AndroidFragment.newInstance("Android"));
//        mFragments.add(AndroidFragment.newInstance("iOS"));
    }
    /**
     * 每日推荐点击"更多"跳转
     */
    private void initRxBus() {
        Subscription subscription = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (integer == 0) {
                            bindingView.vpGank.setCurrentItem(3);
                        } else if (integer == 1) {
                            bindingView.vpGank.setCurrentItem(1);
                        } else if (integer == 2) {
                            bindingView.vpGank.setCurrentItem(2);
                        }
                    }
                });
        addSubscription(subscription);
    }
}
