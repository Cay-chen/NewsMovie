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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.cay.newsmovie.MainActivity;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.OneAdapter;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.example.cay.newsmovie.databinding.FragmentOneBinding;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class OneFragment extends BaseFragment<FragmentOneBinding> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private String getDataUri = "http://60.205.183.88:8080/VMovie/MuchFindDataServer";
    // 初始化完成后加载数据
    private boolean isPrepared = false;
    // 第一次显示时加载数据，第二次不显示
    private boolean isFirst = true;
    //每次刷新加载个数
    private String LOAD_MORE_NUM = "3";
    //初始化加载个数
    private String FIRST_LOAD_MORE_NUM = "5";
    // 是否正在刷新（用于刷新数据时返回页面不再刷新）
    private boolean mIsLoading = false;
    private MainActivity activity;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private OneAdapter oneAdapter;
    private RecyclerView mRecyclerView;
    private List<MovieDataBean> mList;
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
        isPrepared = true;
    }

    @Override
    protected void loadData() {
        if (!isFirst) {
            return;
        }
        // httpGetData(null,null,null,null,nowPosition,FIRST_LOAD_MORE_NUM,isFirst);
        OkHttpUtils.get().url(getDataUri).addParams("position", "0").addParams("num", "10").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                mList = JSON.parseArray(response, MovieDataBean.class);
                nowPosition = String.valueOf(mList.size());
                initAdapter(mList);
                showContentView();
                isFirst = true;
            }
        });
    }

    private void initAdapter(List<MovieDataBean> data) {
        oneAdapter = new OneAdapter(R.layout.item_one1, data, activity);
        oneAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(oneAdapter);
        addHeadView();
        if (data.size() < 5) {
            oneAdapter.loadMoreEnd(true);
        }

    }

    private void addHeadView() {
        View mHeaderView = View.inflate(getContext(), R.layout.header_item_all_movie, null);
        oneAdapter.addHeaderView(mHeaderView);
        initHeader(mHeaderView);
        mTitle = (TextView) mHeaderView.findViewById(R.id.tx_name);
        //View headView = activity.getLayoutInflater().inflate(R.layout.header_item_one, (ViewGroup) mRecyclerView.getParent(), false);
        // ((TextView) headView.findViewById(R.id.header_title)).setText("所有电影");
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
                        httpGetData("type", "2", "city", "1", nowPosition, FIRST_LOAD_MORE_NUM, false);
                        break;
                    case 3:
                        httpGetData("type", "1", "city", "2", nowPosition, FIRST_LOAD_MORE_NUM, false);
                        break;
                    case 4:
                        httpGetData("type", "2", "city", "2", nowPosition, FIRST_LOAD_MORE_NUM, false);
                        break;
                    case 5:
                        httpGetData("type", "1", "city", "4", nowPosition, FIRST_LOAD_MORE_NUM, false);
                        break;
                    case 6:
                        httpGetData("type", "2", "city", "4", nowPosition, FIRST_LOAD_MORE_NUM, false);
                        break;
                    case 7:
                        httpGetData("movie_type", "动画", null, null, nowPosition, FIRST_LOAD_MORE_NUM, false);
                        break;
                }


            }
        }, 1000);

    }

    @Override
    public void onRefresh() {
        showLoading();
        oneAdapter.setEnableLoadMore(false);
        nowPosition ="0";
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

    public void httpGetData(String type1, String value1, String type2, String value2, final String position, String num, final boolean isRefresh) {

        if (type2 == null) {
            if (type1 == null) {
                OkHttpUtils.get().url(getDataUri).addParams("position", position).addParams("num", num).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(activity, "刷新失败", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "response: " + response);
                        mList = JSON.parseArray(response, MovieDataBean.class);
                        if (isRefresh) {
                            Log.i(TAG, "刷新请求:");
                            oneAdapter.setNewData(mList);
                            nowPosition = String.valueOf(mList.size());
                            if (mList.size() < 5) {
                                oneAdapter.loadMoreEnd(true);
                            }
                            mSwipeRefreshLayout.setRefreshing(false);
                            oneAdapter.setEnableLoadMore(true);
                            showContentView();
                        } else {
                            Log.i(TAG, "加载请求:");
                            oneAdapter.addData(mList);
                            nowPosition = String.valueOf(oneAdapter.getData().size());
                            oneAdapter.loadMoreComplete();
                            if (mList.size() < 3) {
                                oneAdapter.loadMoreEnd(false);
                            }
                        }

                    }
                });
                System.out.println("全部条查询");
            } else {
                OkHttpUtils.get().url("http://192.168.0.227:8080/VMovie/FindDataServer").addParams("type", type1).addParams("value", value1).addParams("position", position).addParams("num", num).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(activity, "刷新失败", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mList = JSON.parseArray(response, MovieDataBean.class);
                        if (isRefresh) {
                            Log.i(TAG, "刷新请求:");
                            oneAdapter.setNewData(mList);
                            nowPosition = String.valueOf(mList.size());
                           /* if (mList.size() < 5) {
                                oneAdapter.loadMoreEnd(true);
                            }*/
                            oneAdapter.loadMoreComplete();
                            oneAdapter.loadMoreEnd(false);
                            mSwipeRefreshLayout.setRefreshing(false);
                            //oneAdapter.setEnableLoadMore(true);
                            showContentView();
                        } else {
                            Log.i(TAG, "加载请求:");
                            oneAdapter.addData(mList);
                           // nowPosition = String.valueOf(oneAdapter.getData().size());
                           // oneAdapter.loadMoreComplete();

                        }
                    }
                });
                System.out.println("一条查询");
            }
        } else {
            OkHttpUtils.get().url(getDataUri).addParams("type1", type1).addParams("value1", value1).addParams("type2", type2).addParams("value2", value2).addParams("position", position).addParams("num", num).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(activity, "刷新失败", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    mList = JSON.parseArray(response, MovieDataBean.class);
                    if (isRefresh) {
                        Log.i(TAG, "刷新请求:");
                        oneAdapter.setNewData(mList);
                        nowPosition = String.valueOf(mList.size());
                        if (mList.size() < 5) {
                            oneAdapter.loadMoreEnd(true);
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                        oneAdapter.setEnableLoadMore(true);
                        showContentView();
                    } else {
                        Log.i(TAG, "加载请求:");
                        oneAdapter.addData(mList);
                        nowPosition = String.valueOf(oneAdapter.getData().size());
                        oneAdapter.loadMoreComplete();
                        if (mList.size() < 3) {
                            oneAdapter.loadMoreEnd(false);
                        }
                    }
                }
            });
        }

    }
}
