package com.example.cay.newsmovie.ui.fragment;


import android.app.Activity;
import android.content.Context;
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

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.activity.MovieDetailActivity;
import com.example.cay.newsmovie.adapter.MovieAdapter;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.example.cay.newsmovie.bean.MovieTopbarBean;
import com.example.cay.newsmovie.databinding.FragmentMovieBinding;
import com.example.cay.newsmovie.databinding.HeaderMovieItemBinding;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


/**
 * 电影
 */
public class MangerFragment extends BaseFragment<FragmentMovieBinding> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener  {
    private static final String TAG = "Cay";
    private RecyclerView mRecyclerView;
    private ImageView mHeadImageView;
    private TextView mHeadTextView1;
    private TextView mHeadTextView2;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HeaderMovieItemBinding mHeaderBinding;
    private boolean isFirst = true;//是否第一次请求
    private String position = "0";
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
                    MovieDetailActivity.startE((Activity) getContext(), nameId, img_url, null);
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
        httpGetData(position, "15", true);
    }

    private void loadTopbarData() {
        OkHttpUtils.get().url("http://60.205.183.88:8080/VMovie/ServerTopbarData").addParams("type", "manga").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                List<MovieTopbarBean> list = JSON.parseArray(response, MovieTopbarBean.class);
                MovieTopbarBean movieTopbarBean = list.get(0);
                img_url = movieTopbarBean.getImg_url();
                nameId = movieTopbarBean.getMovie_id();
                Glide.with(getContext()).load(movieTopbarBean.getImg_url()).into(mHeadImageView);
                mHeadTextView1.setText(movieTopbarBean.getName());
                mHeadTextView2.setText(movieTopbarBean.getTitle());
            }
        });
    }

    private void initAdapter(List<MovieDataBean> data) {
        movieAdapter = new MovieAdapter(getContext(), R.layout.movie_grid_item, data);
        mRecyclerView.setAdapter(movieAdapter);
        movieAdapter.setOnLoadMoreListener(this);
        movieAdapter.addHeaderView(mHeaderBinding.getRoot());
        position = String.valueOf(data.size());
        movieAdapter.setEnableLoadMore(true);
        isFirst = false;
        showContentView();
        if (data.size() < 15) {
            movieAdapter.loadMoreEnd(true);
        }

    }

    private void httpGetData(final String position1, String num, final boolean first) {
        OkHttpUtils.get().url("http://60.205.183.88:8080/VMovie/FindDataServer").addParams("type", "movie_type").addParams("value", "动画").addParams("position", position).addParams("num", num).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (first) {
                    showError();
                } else {
                    movieAdapter.loadMoreFail();                }

            }

            @Override
            public void onResponse(String response, int id) {
                List<MovieDataBean> list = JSON.parseArray(response, MovieDataBean.class);
                if (first) {
                    initAdapter(list);
                } else {
                    movieAdapter.addData(list);
                    position = String.valueOf(movieAdapter.getData().size());
                    movieAdapter.loadMoreComplete();
                    if (list.size() < 9) {
                        movieAdapter.loadMoreEnd(false);
                    }
                }

            }
        });

    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                httpGetData(position, "9", false);
            }
        },800);
    }

    @Override
    public void onRefresh() {
        showLoading();
        loadTopbarData();
        if (isFirst) {
            httpGetData(position, "15", true);
        } else {
            OkHttpUtils.get().url("http://60.205.183.88:8080/VMovie/FindDataServer").addParams("type", "movie_type").addParams("value", "动画").addParams("position", "0").addParams("num", "15").build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    showError();
                }

                @Override
                public void onResponse(String response, int id) {
                    List<MovieDataBean> list = JSON.parseArray(response, MovieDataBean.class);
                    movieAdapter.setNewData(list);
                    position = String.valueOf(list.size());
                    if (list.size() < 15) {
                        movieAdapter.loadMoreEnd(true);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    movieAdapter.setEnableLoadMore(true);
                    showContentView();
                }
            });
        }

    }
}
