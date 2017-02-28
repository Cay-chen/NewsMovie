package com.example.cay.newsmovie.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.HotMovieAdapter;
import com.example.cay.newsmovie.bean.HotMovieBean;
import com.example.cay.newsmovie.databinding.ActivityHotMoviePageBinding;
import com.example.cay.newsmovie.http.HttpUtils;
import com.example.cay.newsmovie.statusbar.StatusBarUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Cay on 2017/2/17.
 */

public class HotMovieActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    private ActivityHotMoviePageBinding pageBinding;
    private RecyclerView mRecyclerView;
    private HotMovieAdapter mAdapter;
    private String nowPosition="0";
    private View errorView;
    private boolean isFirst = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageBinding = DataBindingUtil.setContentView(this, R.layout.activity_hot_movie_page);

        pageBinding.toolbarLayout.setTitle("热映榜");
        initTranslucentBar();

        mRecyclerView = pageBinding.rvHotMovie;
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        laodData(nowPosition, "15", false);
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyclerView.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laodData(nowPosition, "5", false);
            }
        });
    }

    private void initTranslucentBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, pageBinding.toolbarLayout);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) pageBinding.toolbarLayout.getLayoutParams();
        layoutParams.setMargins(0, -StatusBarUtil.getStatusBarHeight(this), 0, 0);
        ViewGroup.MarginLayoutParams layoutParams2 = (ViewGroup.MarginLayoutParams) pageBinding.toolbar.getLayoutParams();
        layoutParams2.setMargins(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
    }
    private void laodData(final String position, final String num, final boolean isLoadMor) {
        HttpUtils.getInstance().getMyObservableClient().getHotMovieData(position,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<HotMovieBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<HotMovieBean> list) {
                        if (isLoadMor) {
                            mAdapter.addData(list);
                            mAdapter.setEnableLoadMore(true);
                            mAdapter.loadMoreComplete();
                            if (list.size() < 5) {
                                mAdapter.loadMoreEnd(false);
                            }
                            nowPosition = String.valueOf(mAdapter.getData().size());
                        } else {
                            initAdapter(list);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isFirst) {
                            mAdapter.setEmptyView(errorView);
                        } else {
                            mAdapter.loadMoreFail();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    private void initAdapter(List<HotMovieBean> data) {
        mAdapter = new HotMovieAdapter(HotMovieActivity.this,R.layout.hot_movie_rv_item, data);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);
        isFirst = false;
        if (data.size() < 15) {
            mAdapter.loadMoreEnd(true);
        }
        nowPosition = String.valueOf(data.size());
    }

    @Override
    public void onLoadMoreRequested() {
        laodData(nowPosition, "5", true);
    }
}
