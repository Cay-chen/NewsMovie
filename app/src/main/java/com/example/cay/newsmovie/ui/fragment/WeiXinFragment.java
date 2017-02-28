package com.example.cay.newsmovie.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cay.newsmovie.MainActivity;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.NewsWeiXinAdapter;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.bean.NewsWeiXinBean;
import com.example.cay.newsmovie.bean.WeiXinBackBean;
import com.example.cay.newsmovie.databinding.FragmentNewsTopBinding;
import com.example.cay.newsmovie.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**ss
 * Created by Cay on 2017/2/3.
 */

public class WeiXinFragment extends BaseFragment<FragmentNewsTopBinding> {
    private static String WEIXIN_KEY = "72f4576172c05a67187877ece9b1ba48";
    private RecyclerView mRecyclerView;
    private NewsWeiXinAdapter newsDataAdapter;
    private MainActivity activity;
    private boolean isFirst=true;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public int setContent() {
        return R.layout.fragment_news_top;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading();
        initRecyulerView();
    }
    private void initRecyulerView() {
        mRecyclerView = bindingView.rvNews;
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

   //需加，不然滑动不流畅
      mRecyclerView.setNestedScrollingEnabled(false);
       mRecyclerView.setHasFixedSize(false);
    }
    @Override
    protected void loadData() {
        if (!isFirst) {
            return;
        }
        HttpUtils.getInstance().getJuHeDataUtil().getWeiXinData(WEIXIN_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<WeiXinBackBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WeiXinBackBean value) {
                newsDataAdapter = new NewsWeiXinAdapter(getContext(), R.layout.news_weixin_item, value.getResult().getList());
                mRecyclerView.setAdapter(newsDataAdapter);
                showContentView();
                isFirst = false;
                newsDataAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<NewsWeiXinBean> listNull = new ArrayList<>();
                                newsDataAdapter.addData(listNull);
                                newsDataAdapter.loadMoreComplete();
                                newsDataAdapter.loadMoreEnd(false);
                            }
                        }, 1500);

                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                showError();
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    protected void onRefresh() {
        loadData();
    }
}
