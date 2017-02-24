package com.example.cay.newsmovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.GetMovieAdapter;
import com.example.cay.newsmovie.base.BaseActivity;
import com.example.cay.newsmovie.bean.IssueBean;
import com.example.cay.newsmovie.bean.UpDdtaBackBean;
import com.example.cay.newsmovie.databinding.ActivityIssueBinding;
import com.example.cay.newsmovie.http.HttpUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetMovieActivity extends BaseActivity<ActivityIssueBinding> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private boolean isFirst = true;
    private GetMovieAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String nowPosition = "0";
    private EditText mEditText;
    private boolean isSubmit;
    private View mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        showLoading();
        setTitle("更新请求");
        mHeaderView = View.inflate(this, R.layout.get_movie_head, null);
        mEditText = (EditText) mHeaderView.findViewById(R.id.et_get_movie_name);
        Button mButton = (Button) mHeaderView.findViewById(R.id.btn_movie_submit);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(GetMovieActivity.this, "请输入电影名或电视名", Toast.LENGTH_SHORT).show();
                } else {
                    if (!isSubmit) {
                        isSubmit = true;
                        submit();
                    }
                }
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = bindingView.rvIssue;
        mSwipeRefreshLayout = bindingView.srlIssue;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        laodData("0", "15", false);
    }

    private void initAdapter(List<IssueBean> data) {
        mAdapter = new GetMovieAdapter(R.layout.get_movie_updata_item, data);
        mAdapter.addHeaderView(mHeaderView);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);
        if (data.size() < 10) {
            mAdapter.loadMoreEnd(true);
        }
        nowPosition = String.valueOf(data.size());
    }

    private void laodData(final String position, final String num, final boolean isLoadMor) {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                HttpUtils.getInstance().getMyObservableClient().getUpMovieData(position, num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<IssueBean>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<IssueBean> list) {
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

                            @Override
                            public void onError(Throwable e) {
                                if (isLoadMor) {
                                    mAdapter.loadMoreFail();
                                } else {
                                    showError();
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                            }

                            @Override
                            public void onComplete() {

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
        nowPosition = "0";
        laodData(nowPosition, "15", false);
    }

    private void submit() {
        HttpUtils.getInstance().getMyObservableClient().upMoviePlease(mEditText.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpDdtaBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpDdtaBackBean value) {
                        if (value.getResMsg().equals("1"))
                            mEditText.setText("");
                        Toast.makeText(GetMovieActivity.this, "上传成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(GetMovieActivity.this, "上传失败", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
