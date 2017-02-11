package com.example.cay.newsmovie.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cay.newsmovie.MainActivity;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.NewsDataAdapter;
import com.example.cay.newsmovie.adapter.NewsWeiXinAdapter;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.base.adapter.MultipleItemNews;
import com.example.cay.newsmovie.bean.NewsDataBean;
import com.example.cay.newsmovie.bean.NewsWeiXinBean;
import com.example.cay.newsmovie.databinding.FragmentNewsTopBinding;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Cay on 2017/2/3.
 */

public class NewsWeiXinFragment extends BaseFragment<FragmentNewsTopBinding> {
    private List<NewsWeiXinFragment> mmList;
    private RecyclerView mRecyclerView;
    private NewsWeiXinAdapter newsDataAdapter;
    private MainActivity activity;



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
        OkHttpUtils.get().url("http://v.juhe.cn/weixin/query").addParams("key", "72f4576172c05a67187877ece9b1ba48").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                String data = jsonObject1.getString("list");
                List<NewsWeiXinBean> list = JSON.parseArray(data, NewsWeiXinBean.class);
                newsDataAdapter = new NewsWeiXinAdapter(getContext(), R.layout.news_weixin_item, list);
                mRecyclerView.setAdapter(newsDataAdapter);
                showContentView();
                newsDataAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<NewsWeiXinBean> listNull = new ArrayList<NewsWeiXinBean>();
                                newsDataAdapter.addData(listNull);
                                newsDataAdapter.loadMoreComplete();
                                newsDataAdapter.loadMoreEnd(false);
                            }
                        }, 1500);

                    }
                });



            }
        });
    }

    @Override
    protected void onRefresh() {

    }
}
