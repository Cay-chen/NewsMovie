package com.example.cay.newsmovie.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cocosw.bottomsheet.BottomSheet;
import com.example.cay.newsmovie.MainActivity;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.MovieDetailsAdapter;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.example.cay.newsmovie.databinding.FragmentOneBinding;
import com.example.cay.newsmovie.http.HttpUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 所有选项查询页面
 */
public class AllMovieFragment extends BaseFragment<FragmentOneBinding> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    // 第一次显示时加载数据，第二次不显示
    private boolean isFirst = true;
    //每次刷新加载个数
    private String LOAD_MORE_NUM = "5";
    //初始化加载个数
    private String FIRST_LOAD_MORE_NUM = "10";
    private MainActivity activity;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MovieDetailsAdapter movieDetailsAdapter;
    private RecyclerView mRecyclerView;
    private static final String TAG = "Cay";
    private TextView mTitle;
    private int loadWhere = 0;
    private String nowPosition = "0";

    @Override
    public int setContent() {
        return R.layout.fragment_one;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = bindingView.listOne;
        mSwipeRefreshLayout = bindingView.swipeLayout;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    protected void loadData() {
        if (!isFirst) {
            return;
        }
        httpGetData(null, null, null, null, nowPosition, FIRST_LOAD_MORE_NUM, true);

    }

    private void initAdapter(List<MovieDataBean> data) {
        movieDetailsAdapter = new MovieDetailsAdapter(R.layout.item_one1, data, activity);
        movieDetailsAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(movieDetailsAdapter);
        addHeadView();
        if (data.size() < 10) {
            movieDetailsAdapter.loadMoreEnd(true);
        }

    }

    private void addHeadView() {
        View mHeaderView = View.inflate(getContext(), R.layout.header_item_all_movie, null);
        movieDetailsAdapter.addHeaderView(mHeaderView);
        initHeader(mHeaderView);
        mTitle = (TextView) mHeaderView.findViewById(R.id.tx_name);
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (loadWhere) {
                    case 0:
                        httpGetData(null, null, null, null, nowPosition, LOAD_MORE_NUM, false);
                        break;
                    case 1:
                        httpGetData("type", "1", "city", "1", nowPosition, LOAD_MORE_NUM, false);
                        break;
                    case 2:
                        httpGetData("type", "2", "city", "1", nowPosition, LOAD_MORE_NUM, false);
                        break;
                    case 3:
                        httpGetData("type", "1", "city", "2", nowPosition, LOAD_MORE_NUM, false);
                        break;
                    case 4:
                        httpGetData("type", "2", "city", "2", nowPosition, LOAD_MORE_NUM, false);
                        break;
                    case 5:
                        httpGetData("type", "1", "city", "4", nowPosition, LOAD_MORE_NUM, false);
                        break;
                    case 6:
                        httpGetData("type", "2", "city", "4", nowPosition, LOAD_MORE_NUM, false);
                        break;
                    case 7:
                        httpGetData("movie_type", "动画", null, null, nowPosition, LOAD_MORE_NUM, false);
                        break;
                }
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        showLoading();
        movieDetailsAdapter.setEnableLoadMore(false);
        nowPosition = "0";
        switch (loadWhere) {
            case 0:
                httpGetData(null, null, null, null, nowPosition, FIRST_LOAD_MORE_NUM, true);
                break;
            case 1:
                httpGetData("type", "1", "city", "1", nowPosition, FIRST_LOAD_MORE_NUM, true);
                break;
            case 2:
                httpGetData("type", "2", "city", "1", nowPosition, FIRST_LOAD_MORE_NUM, true);
                break;
            case 3:
                httpGetData("type", "1", "city", "2", nowPosition, FIRST_LOAD_MORE_NUM, true);
                break;
            case 4:
                httpGetData("type", "2", "city", "2", nowPosition, FIRST_LOAD_MORE_NUM, true);
                break;
            case 5:
                httpGetData("type", "1", "city", "4", nowPosition, FIRST_LOAD_MORE_NUM, true);
                break;
            case 6:
                httpGetData("type", "2", "city", "4", nowPosition, FIRST_LOAD_MORE_NUM, true);
                break;
            case 7:
                httpGetData("movie_type", "动画", null, null, nowPosition, FIRST_LOAD_MORE_NUM, true);
                break;
        }
    }

    /**
     * 初始化头部，并设置item的点击事件
     * @param mHeaderView xx
     */
    private void initHeader(View mHeaderView) {
        View view = mHeaderView.findViewById(R.id.ll_choose_catalogue);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog).title("选择分类").sheet(R.menu.gank_bottomsheet).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.movie_all:
                                if (!mTitle.getText().toString().equals("全部")) {
                                    showLoading();
                                    mTitle.setText("全部");
                                    nowPosition = "0";
                                    loadWhere = 0;
                                    httpGetData(null, null, null, null, nowPosition, FIRST_LOAD_MORE_NUM, true);
                                }
                                break;
                            case R.id.movie_china_tv:
                                if (!mTitle.getText().toString().equals("国内电视剧")) {
                                    showLoading();
                                    Log.i(TAG, "movie_china_tv: ");
                                    mTitle.setText("国内电视剧");
                                    nowPosition = "0";
                                    loadWhere = 1;
                                    httpGetData("type", "1", "city", "1", nowPosition, FIRST_LOAD_MORE_NUM, true);
                                }

                                break;
                            case R.id.movie_china_video:
                                if (!mTitle.getText().toString().equals("国内电影")) {
                                    showLoading();
                                    Log.i(TAG, "movie_china_video: ");
                                    mTitle.setText("国内电影");
                                    nowPosition = "0";
                                    loadWhere = 2;
                                    httpGetData("type", "2", "city", "1", nowPosition, FIRST_LOAD_MORE_NUM, true);
                                }

                                break;
                            case R.id.movie_usa_tv:
                                if (!mTitle.getText().toString().equals("欧美电视剧")) {
                                    showLoading();
                                    Log.i(TAG, "movie_usa_tv: ");
                                    mTitle.setText("欧美电视剧");
                                    nowPosition = "0";
                                    loadWhere = 3;
                                    httpGetData("type", "1", "city", "2", nowPosition, FIRST_LOAD_MORE_NUM, true);
                                }

                                break;
                            case R.id.movie_usa_video:
                                if (!mTitle.getText().toString().equals("欧美电影")) {
                                    showLoading();
                                    Log.i(TAG, "movie_usa_video: ");
                                    mTitle.setText("欧美电影");
                                    nowPosition = "0";
                                    loadWhere = 4;
                                    httpGetData("type", "2", "city", "2", nowPosition, FIRST_LOAD_MORE_NUM, true);
                                }

                                break;
                            case R.id.movie_kor_tv:
                                if (!mTitle.getText().toString().equals("韩国电视剧")) {
                                    showLoading();
                                    Log.i(TAG, "movie_kor_tv: ");
                                    mTitle.setText("韩国电视剧");
                                    nowPosition = "0";
                                    loadWhere = 5;
                                    httpGetData("type", "1", "city", "4", nowPosition, FIRST_LOAD_MORE_NUM, true);
                                }

                                break;
                            case R.id.movie_kor_video:
                                if (!mTitle.getText().toString().equals("韩国电影")) {
                                    showLoading();
                                    Log.i(TAG, "movie_kor_video: ");
                                    mTitle.setText("韩国电影");
                                    nowPosition = "0";
                                    loadWhere = 6;
                                    httpGetData("type", "2", "city", "4", nowPosition, FIRST_LOAD_MORE_NUM, true);
                                }
                                break;
                            case R.id.movie_dongman:
                                if (!mTitle.getText().toString().equals("动漫")) {
                                    showLoading();
                                    Log.i(TAG, "movie_dongman: ");
                                    mTitle.setText("动漫");
                                    nowPosition = "0";
                                    loadWhere = 7;
                                    httpGetData("movie_type", "动画", null, null, nowPosition, FIRST_LOAD_MORE_NUM, true);
                                }
                                break;
                        }
                    }
                }).show();

            }
        });
    }

    /**
     * 根据查询条件查询数据
     * @param type1  类型1
     * @param value1 类型1值
     * @param type2 类型1
     * @param value2 类型2值
     * @param position 查询位置
     * @param num 查询数量
     * @param isRefresh  是否刷新
     */
    public void httpGetData(String type1, String value1, String type2, String value2, final String position, String num, final boolean isRefresh) {
        if (type2 == null) {
            if (type1 == null) {
                HttpUtils.getInstance().getMyObservableClient().allFindData(position, num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<MovieDataBean>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<MovieDataBean> list) {
                                successHander(isRefresh, list);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (isRefresh) {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                } else {
                                    movieDetailsAdapter.loadMoreFail();
                                }
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            } else {
                HttpUtils.getInstance().getMyObservableClient().singelRequirementFindData(type1, value1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<MovieDataBean>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<MovieDataBean> list) {
                                successHander(isRefresh, list);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (isRefresh) {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                } else {
                                    movieDetailsAdapter.loadMoreFail();
                                }
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }

        } else {
            HttpUtils.getInstance().getMyObservableClient().twoRequirementFindData(type1, value1, type2, value2, position, num)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MovieDataBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<MovieDataBean> list) {
                            successHander(isRefresh, list);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (isRefresh) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            } else {
                                movieDetailsAdapter.loadMoreFail();
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    /**
     * 数据请求成功后处理操作
     * @param isRefresh 是否刷新
     * @param list 数据
     */
    public void successHander(boolean isRefresh, List<MovieDataBean> list) {
        if (isFirst) {
            nowPosition = String.valueOf(list.size());
            initAdapter(list);
            showContentView();
            isFirst = false;
        } else {
            if (isRefresh) {
                movieDetailsAdapter.setNewData(list);
                nowPosition = String.valueOf(list.size());
                if (list.size() < Integer.parseInt(FIRST_LOAD_MORE_NUM)) {
                    movieDetailsAdapter.loadMoreEnd(true);
                }
                mSwipeRefreshLayout.setRefreshing(false);
                movieDetailsAdapter.setEnableLoadMore(true);
                showContentView();
            } else {
                movieDetailsAdapter.addData(list);
                nowPosition = String.valueOf(movieDetailsAdapter.getData().size());
                movieDetailsAdapter.loadMoreComplete();
                if (list.size() < Integer.parseInt(LOAD_MORE_NUM)) {
                    movieDetailsAdapter.loadMoreEnd(false);
                }
            }
        }

    }
}
