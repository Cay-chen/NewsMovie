package com.example.cay.newsmovie.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.cay.newsmovie.MainActivity;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.activity.MovieDetailActivity;
import com.example.cay.newsmovie.adapter.EveryDayAdapter;
import com.example.cay.newsmovie.base.GlideImageLoader;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.base.adapter.MultipleItem;
import com.example.cay.newsmovie.bean.BannerDataBean;
import com.example.cay.newsmovie.bean.FirstRxDataBean;
import com.example.cay.newsmovie.databinding.FooterItemEverydayBinding;
import com.example.cay.newsmovie.databinding.FragmentEverydayBinding;
import com.example.cay.newsmovie.databinding.HeaderItemEverydayBinding;
import com.example.cay.newsmovie.utils.rx.RxBus;
import com.example.cay.newsmovie.utils.rx.RxBusBaseMessage;
import com.example.cay.newsmovie.utils.rx.RxCodeConstants;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;

/**
 * 每日推荐
 * 更新逻辑：判断是否是第二天(相对于2016-11-26)
 * 是：判断是否是大于12：30
 * *****     |是：刷新当天数据
 * *****     |否：使用缓存：|无：请求前一天数据
 * **********             |有：使用缓存
 * 否：使用缓存 ： |无：请求今天数据
 * **********    |有：使用缓存
 */
public class EverydayFragment extends BaseFragment<FragmentEverydayBinding> {

    private static final String TAG = "EverydayFragment";
    private RecyclerView mRecyclerView;
    private ArrayList<String> mBannerImages;
    private HeaderItemEverydayBinding mHeaderBinding;
    private FooterItemEverydayBinding mFooterBinding;

    private View mHeaderView;
    private View mFooterView;
    private boolean mIsFirst = true;
    private RotateAnimation animation;
    private EveryDayAdapter mEveryDayAdapter;
    private MainActivity activity;
    private Banner mBanner;
    private List<FirstRxDataBean> mList;
    private List<MultipleItem> mmList;

    @Override
    public int setContent() {
        return R.layout.fragment_everyday;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }
    @Override
    protected void onInvisible() {
        // 不可见时轮播图停止滚动
        if (mHeaderBinding != null && mHeaderBinding.banner != null) {
            mHeaderBinding.banner.stopAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 失去焦点，否则recyclerview第一个item会回到顶部
        bindingView.xrvEveryday.setFocusable(false);
        // 开始图片请求
        Glide.with(getActivity()).resumeRequests();
    }
    @Override
    public void onPause() {
        super.onPause();
        // 停止全部图片请求 跟随着Activity
        Glide.with(getActivity()).pauseRequests();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        bindingView.llLoading.setVisibility(View.VISIBLE);
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());//不停顿
        animation.setRepeatCount(10);
        bindingView.ivLoading.setAnimation(animation);
        animation.startNow();
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.header_item_everyday, null, false);
        mFooterBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.footer_item_everyday, null, false);
        mHeaderView = mHeaderBinding.getRoot();
        View view = mHeaderView.findViewById(R.id.include_everyday);
        ImageButton imb= (ImageButton) view.findViewById(R.id.ib_all_movie);
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE_TO_ONE, new RxBusBaseMessage());

            }
        });
        mFooterView = mFooterBinding.getRoot();
        initRecyulerView();


    }

    @Override
    protected void loadData() {
        if (mIsFirst) {
            initData();
            initFirstData();
        }

    }

    private void initRecyulerView() {
        mRecyclerView = bindingView.xrvEveryday;
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
// 需加，不然滑动不流畅
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
    }

    private void initFirstData() {
        OkHttpUtils.get().url("http://60.205.183.88:8080/VMovie/FirstRxDataServer").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showError();
                    }
                },3000);

            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: " + response);
                mList = JSON.parseArray(response, FirstRxDataBean.class);
                mmList = new ArrayList<MultipleItem>();
                for (int i = 0; i < mList.size(); i++) {
                    FirstRxDataBean bean = mList.get(i);
                    mmList.add(new MultipleItem(bean.getType(), bean.getImg1(), bean.getMid1(), bean.getCon1(), bean.getImg2(), bean.getMid2(), bean.getCon2(), bean.getImg3(), bean.getMid3(), bean.getCon3(), bean.getG_type(), bean.getTitle(), bean.getIsTitle()));
                }
                mEveryDayAdapter = new EveryDayAdapter(activity, mmList);
                mEveryDayAdapter.addHeaderView(mHeaderView);
                mEveryDayAdapter.addFooterView(mFooterView);
                mRecyclerView.setAdapter(mEveryDayAdapter);
                mIsFirst = false;
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showRotaLoading(false);
                    }
                },800);


            }
        });


    }

    private void initData() {
        OkHttpUtils.get().url("http://60.205.183.88:8080/VMovie/BannerDataServer").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                initData();
            }

            @Override
            public void onResponse(String response, int id) {
                BannerDataBean bannerDataBean = JSON.parseObject(response,BannerDataBean.class);
                    String[] imgs = bannerDataBean.getImgs();
                    String[] titles = bannerDataBean.getTitles();
                    String[] movieIds = bannerDataBean.getMovieIds();
                    String[] types = bannerDataBean.getTypes();
                initBanner(imgs,titles,movieIds,types);
                mIsFirst = false;
            }
        });


    }

    private void initBanner(final String[] img,String[] titles,String[] id,String[] type) {
       final List<String> idList =Arrays.asList(id);
        mBanner = mHeaderBinding.banner;
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(Arrays.asList(img));
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                MovieDetailActivity.startE((Activity)getContext(), idList.get(position-1), Arrays.asList(img).get(position-1), null);

            }
        });
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

    }
    private void showRotaLoading(boolean isLoading) {
        if (isLoading) {
            bindingView.llLoading.setVisibility(View.VISIBLE);
            bindingView.xrvEveryday.setVisibility(View.GONE);
            animation.startNow();
        } else {
            bindingView.llLoading.setVisibility(View.GONE);
            bindingView.xrvEveryday.setVisibility(View.VISIBLE);
            animation.cancel();
        }
    }
    @Override
    protected void onRefresh() {
        showContentView();
        showRotaLoading(true);
        initFirstData();
//        loadData();
        initData();
    }
}
