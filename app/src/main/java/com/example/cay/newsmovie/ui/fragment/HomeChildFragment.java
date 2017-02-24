package com.example.cay.newsmovie.ui.fragment;


import android.app.Activity;
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
import com.example.cay.newsmovie.ui.activity.MovieDetailActivity;
import com.example.cay.newsmovie.adapter.MovieAdapter;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.example.cay.newsmovie.bean.MovieTopbarBean;
import com.example.cay.newsmovie.databinding.FragmentMovieBinding;
import com.example.cay.newsmovie.databinding.HeaderMovieItemBinding;
import com.example.cay.newsmovie.http.HttpUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 电影
 */
public class HomeChildFragment extends BaseFragment<FragmentMovieBinding> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private ImageView mHeadImageView;
    private TextView mHeadTextView1;
    private TextView mHeadTextView2;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HeaderMovieItemBinding mHeaderBinding;
    private boolean isFirst = true;//是否第一次请求
    private String position = "0";//请求位置记录
    private String FIRST_NUM = "15";//初始请求个数
    private String LAOD_NUM = "9";//上拉加载个数
    private MovieAdapter movieAdapter;
    private String img_url;
    private String nameId;
    private String topbar;
    private String type;

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
                    MovieDetailActivity.start((Activity) getContext(), nameId, img_url, null);
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topbar = getArguments().getString("topbar");
            type = getArguments().getString("type");
        }
    }

    public static HomeChildFragment newInstance(String topbar, String type) {
        HomeChildFragment fragment = new HomeChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("topbar", topbar);
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
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
        httpGetData(position, FIRST_NUM, true, false);
    }

    private void loadTopbarData() {
        HttpUtils.getInstance().getMyObservableClient().getTopBarData(topbar).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<MovieTopbarBean>>() {
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
        position = String.valueOf(data.size());
        movieAdapter.setEnableLoadMore(true);
        isFirst = false;
        showContentView();
        if (data.size() < 15) {
            movieAdapter.loadMoreEnd(true);
        }

    }

    private void httpGetData(final String position1, String num, final boolean first, final boolean isResfres) {
        HttpUtils.getInstance().getMyObservableClient().oneRequirementFindData("type", type, position1, num).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<MovieDataBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<MovieDataBean> list) {
                if (first) {
                    initAdapter(list);
                } else {
                    if (isResfres) {
                        movieAdapter.setNewData(list);
                        position = String.valueOf(list.size());
                        if (list.size() < 15) {
                            movieAdapter.loadMoreEnd(true);
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                        movieAdapter.setEnableLoadMore(true);
                        showContentView();
                    } else {
                        movieAdapter.addData(list);
                        position = String.valueOf(movieAdapter.getData().size());
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
                    if (isResfres) {
                        showError();
                    } else {
                        movieAdapter.loadMoreFail();
                    }
                }
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
                httpGetData(position, LAOD_NUM, false, false);
            }
        }, 800);
    }

    @Override
    public void onRefresh() {
        showLoading();
        loadTopbarData();
        if (isFirst) {
            httpGetData(position, FIRST_NUM, true, false);
        } else {
            position = "0";
            httpGetData(position, FIRST_NUM, false, true);
        }

    }
}
