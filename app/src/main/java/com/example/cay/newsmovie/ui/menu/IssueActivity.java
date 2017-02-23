package com.example.cay.newsmovie.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.IssueAdapter;
import com.example.cay.newsmovie.base.BaseActivity;
import com.example.cay.newsmovie.base.DataCallBack;
import com.example.cay.newsmovie.bean.IssueBean;
import com.example.cay.newsmovie.databinding.ActivityIssueBinding;
import com.example.cay.newsmovie.utils.GetDataUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class IssueActivity extends BaseActivity<ActivityIssueBinding> implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private boolean isFirst = true;
    private  IssueAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String nowPosition="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        showLoading();
        setTitle("问题反馈列表");
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = bindingView.rvIssue;
        mSwipeRefreshLayout = bindingView.srlIssue;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        laodData("0", "15",false);
    }

    private void initAdapter(List<IssueBean> data) {
        mAdapter = new IssueAdapter(R.layout.issue_item, data);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);
        if (data.size() < 15) {
            mAdapter.loadMoreEnd(true);
        }
        nowPosition = String.valueOf(data.size());
    }
    private void laodData(final String position, final String num, final boolean isLoadMor) {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetDataUtils.getInstance().getIssueData(position, num, new DataCallBack() {
                    @Override
                    public void err() {
                        if (isLoadMor) {
                            mAdapter.loadMoreFail();
                        } else {
                            showError();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void success(List list) {
                        if (isLoadMor) {
                            mAdapter.addData(list);
                            mAdapter.setEnableLoadMore(true);
                            mAdapter.loadMoreComplete();
                            if (list.size() < 5) {
                                mAdapter.loadMoreEnd(false);
                            }
                            nowPosition = String.valueOf(mAdapter.getData().size());
                        } else {
                            if (isFirst) {
                                initAdapter(list);
                                isFirst = false;
                                showContentView();
                            } else {
                                mAdapter.setNewData(list);
                                mAdapter.loadMoreComplete();
                                mAdapter.loadMoreEnd(false);
                                mSwipeRefreshLayout.setRefreshing(false);
                                showContentView();
                            }
                        }
                    }
                });
            }
        }, 1000);

    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, IssueActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void onLoadMoreRequested() {
        laodData(nowPosition, "5", true);
    }

    @Override
    public void onRefresh() {
        showLoading();
        nowPosition ="0";
        laodData(nowPosition, "15", false);
    }
}