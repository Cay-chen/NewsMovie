package com.example.cay.newsmovie.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;


import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.MyFragmentPagerAdapter;

import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.databinding.FragmentMovieHomeBinding;
import com.example.cay.newsmovie.http.RxBus.RxBus;
import com.example.cay.newsmovie.http.RxBus.RxCodeConstants;

import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * dd
 * Created by Cay on 2017/2/3.
 */

public class MovieHomeFragment extends BaseFragment<FragmentMovieHomeBinding> {
    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    private CompositeDisposable disposable;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

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
        mFragments.add(HomeChildFragment.newInstance("movie","2"));
        mFragments.add(HomeChildFragment.newInstance("tv","1"));
        mFragments.add(new MangerFragment());
    }
/**
     * 每日推荐点击"更多"跳转
     */
    private void initRxBus() {
        disposable = new CompositeDisposable();
        disposable.add(   RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE, Integer.class)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if ((Integer)o == 0) {
                            bindingView.vpGank.setCurrentItem(3);
                        } else if ((Integer)o == 1) {
                            bindingView.vpGank.setCurrentItem(1);
                        } else if ((Integer)o == 2) {
                            bindingView.vpGank.setCurrentItem(2);
                        }
                    }
                }
                 ));

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //取消订阅
        disposable.clear();
    }
}
