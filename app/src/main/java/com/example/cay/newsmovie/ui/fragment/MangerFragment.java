package com.example.cay.newsmovie.ui.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.MovieAdapter;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.example.cay.newsmovie.bean.MovieTopbarBean;
import com.example.cay.newsmovie.databinding.FragmentMovieBinding;
import com.example.cay.newsmovie.databinding.HeaderMovieItemBinding;
import com.example.cay.newsmovie.http.HttpUtils;
import com.example.cay.newsmovie.ui.activity.MovieDetailActivity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 电影
 */
public class MangerFragment extends BaseFragment<FragmentMovieBinding> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener  {

    private RecyclerView mRecyclerView;
    private ImageView mHeadImageView;
    private TextView mHeadTextView1;
    private TextView mHeadTextView2;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HeaderMovieItemBinding mHeaderBinding;
    private boolean isFirst = true;//是否第一次请求
    private MovieAdapter movieAdapter;
    private String img_url;
    private String nameId;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = bindingView.listMovie;
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.header_movie_item, null, false);
        mHeadImageView = mHeaderBinding.ivHeaderItemMovie;
        mHeadTextView1 = mHeaderBinding.tvHeaderItemText1;
        mHeadTextView2 = mHeaderBinding.tvHeaderItemText2;
        mSwipeRefreshLayout = bindingView.swipeLayout;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mHeadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!img_url.isEmpty()) {
                    MovieDetailActivity.start(getActivity(), nameId, img_url, null);
                }
            }
        });
    }


    @Override
    public int setContent() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (!isFirst) {
            return;
        }
        loadTopbarData();
        httpGetData(true,false);
    }

    private void loadTopbarData() {
        HttpUtils.getInstance().getMyObservableClient().getTopBarData("manga")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MovieTopbarBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MovieTopbarBean> list) {
                        MovieTopbarBean movieTopbarBean = list.get(0);
                        img_url = movieTopbarBean.getImg_url();
                        nameId = movieTopbarBean.getMovie_id();
                        Glide.with(getContext()).load(movieTopbarBean.getImg_url()).into(mHeadImageView);
                        mHeadTextView1.setText(movieTopbarBean.getName());
                        mHeadTextView2.setText(movieTopbarBean.getTitle());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initAdapter(List<MovieDataBean> data) {
        movieAdapter = new MovieAdapter(getContext(), R.layout.movie_grid_item, data);
        mRecyclerView.setAdapter(movieAdapter);
        movieAdapter.setOnLoadMoreListener(this);
        movieAdapter.addHeaderView(mHeaderBinding.getRoot());
        movieAdapter.setEnableLoadMore(true);
        isFirst = false;
        showContentView();
        if (data.size() < 15) {
            movieAdapter.loadMoreEnd(true);
        }

    }

    private void httpGetData(final boolean first, final boolean isRefresh) {
        HttpUtils.getInstance().getMyObservableClient().singelRequirementFindData("movie_type","动画")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MovieDataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MovieDataBean> list) {
                        if (first) {
                            initAdapter(list);
                        } else {
                            if (isRefresh) {
                                movieAdapter.setNewData(list);

                                mSwipeRefreshLayout.setRefreshing(false);
                                movieAdapter.setEnableLoadMore(true);
                                showContentView();
                            } else {
                                movieAdapter.addData(list);
                                movieAdapter.loadMoreComplete();
                                if (list.size() < 9) {
                                    movieAdapter.loadMoreEnd(false);
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (first) {
                            showError();
                        } else {
                            movieAdapter.loadMoreFail();                }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                httpGetData(false,false);
            }
        },800);
    }

    @Override
    public void onRefresh() {
        showLoading();
        loadTopbarData();
        if (isFirst) {
            httpGetData(true,false);
        } else {
            httpGetData(false,true);

        }

    }
}
