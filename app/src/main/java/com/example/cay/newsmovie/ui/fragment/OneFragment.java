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
import android.view.ViewGroup;
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
import com.example.cay.newsmovie.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class OneFragment extends BaseFragment<FragmentOneBinding> implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {
private String getDataUri = "http://60.205.183.88:8080/VMovie/MuchFindDataServer";
    // 初始化完成后加载数据
    private boolean isPrepared = false;
    // 第一次显示时加载数据，第二次不显示
    private boolean isFirst = false;
    // 是否正在刷新（用于刷新数据时返回页面不再刷新）
    private boolean mIsLoading = false;
    private MainActivity activity;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private OneAdapter oneAdapter;
    private RecyclerView mRecyclerView;
    private List<MovieDataBean> mList;
    private static final String TAG = "Cay";
    private TextView mTitle;
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
        isPrepared =true;
    }

    @Override
    protected void loadData() {
        if (isFirst) {
            return;
        }
        OkHttpUtils.get().url(getDataUri).addParams("position","0").addParams("num","10").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                mList = JSON.parseArray(response, MovieDataBean.class);
                initAdapter(mList);
                showContentView();
                isFirst =true;
            }
        });
    }
    private void initAdapter(List<MovieDataBean> data) {
        oneAdapter = new OneAdapter(R.layout.item_one1,data,activity);
        oneAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(oneAdapter);
        addHeadView();
        if (data.size() < 10) {
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
                OkHttpUtils.get().url(getDataUri).addParams("position", String.valueOf(oneAdapter.getData().size())).addParams("num","10").build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        oneAdapter.loadMoreFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<MovieDataBean> list = JSON.parseArray(response, MovieDataBean.class);
                        oneAdapter.addData(list);
                        oneAdapter.loadMoreComplete();
                        if (list.size() < 5) {oneAdapter.loadMoreEnd(false);
                        }
                    }
                });
            }
        },1000);

    }

    @Override
    public void onRefresh() {
        oneAdapter.setEnableLoadMore(false);
        OkHttpUtils.get().url(getDataUri).addParams("position","0").addParams("num","10").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(activity,"刷新失败",Toast.LENGTH_LONG).show();
              //  StyleableToast.makeText(activity, "刷新失败", Toast.LENGTH_SHORT, R.style.ToolbarStyle);
            }

            @Override
            public void onResponse(String response, int id) {
                mList = JSON.parseArray(response, MovieDataBean.class);
                oneAdapter.setNewData(mList);
                mSwipeRefreshLayout.setRefreshing(false);
                oneAdapter.setEnableLoadMore(true);
            }
        });

    }
    private void initHeader(View mHeaderView) {
        final TextView txName = (TextView) mHeaderView.findViewById(R.id.tx_name);
        String gankCala = SPUtils.getString("gank_cala", "全部");
        txName.setText(gankCala);
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
                                    mTitle.setText("全部");
                                    Log.i(TAG, "movie_all: ");
                                }

                                break;
                            case R.id.movie_china_tv:
                                if (!mTitle.getText().toString().equals("国内电视剧")) {
                                    Log.i(TAG, "movie_china_tv: ");
                                    mTitle.setText("国内电视剧");
                                }

                                break;
                            case R.id.movie_china_video:
                                if (!mTitle.getText().toString().equals("国内电影")) {
                                    Log.i(TAG, "movie_china_video: ");
                                    mTitle.setText("国内电影");
                                }

                                break;
                            case R.id.movie_usa_tv:
                                if (!mTitle.getText().toString().equals("欧美电视剧")) {
                                    Log.i(TAG, "movie_usa_tv: ");
                                    mTitle.setText("欧美电视剧");
                                }

                                break;
                            case R.id.movie_usa_video:
                                if (!mTitle.getText().toString().equals("欧美电影")) {
                                    Log.i(TAG, "movie_usa_video: ");
                                    mTitle.setText("欧美电影");
                                }

                                break;
                            case R.id.movie_kor_tv:
                                if (!mTitle.getText().toString().equals("韩国电视剧")) {
                                    Log.i(TAG, "movie_kor_tv: ");
                                    mTitle.setText("韩国电视剧");
                                }

                                break;
                            case R.id.movie_kor_video:
                                if (!mTitle.getText().toString().equals("韩国电影")) {
                                    Log.i(TAG, "movie_kor_video: ");
                                    mTitle.setText("韩国电影");
                                }

                                break;
                            case R.id.movie_dongman:
                                if (!mTitle.getText().toString().equals("动漫")) {
                                    Log.i(TAG, "movie_dongman: ");
                                    mTitle.setText("动漫");
                                }

                                break;


                        }
                    }
                }).show();

            }
        });
    }
}
