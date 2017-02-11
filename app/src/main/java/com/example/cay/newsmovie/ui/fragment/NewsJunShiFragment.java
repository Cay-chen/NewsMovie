package com.example.cay.newsmovie.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cay.newsmovie.MainActivity;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.NewsDataAdapter;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.base.adapter.MultipleItemNews;
import com.example.cay.newsmovie.bean.NewsDataBean;
import com.example.cay.newsmovie.databinding.FragmentNewsTopBinding;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Cay on 2017/2/3.
 */

public class NewsJunShiFragment extends BaseFragment<FragmentNewsTopBinding> {
    private List<MultipleItemNews> mmList;
    private RecyclerView mRecyclerView;
    private NewsDataAdapter newsDataAdapter;
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
        Log.i("TAGAA", "Frage:....");
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
        OkHttpUtils.get().url("http://v.juhe.cn/toutiao/index").addParams("type", "junshi").addParams("key", "f9141352fe34615b0806e9fd943f9dbd").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                String data = jsonObject1.getString("data");
                List<NewsDataBean> list = JSON.parseArray(data, NewsDataBean.class);
                mmList = new ArrayList<MultipleItemNews>();
                for (int i = 0; i < list.size(); i++) {
                    NewsDataBean bean = list.get(i);
                    if (bean.getThumbnail_pic_s03()==null) {
                        if (bean.getThumbnail_pic_s02()==null) {
                            mmList.add(new MultipleItemNews(1, bean.getUniquekey(), bean.getTitle(), bean.getDate(), bean.getCategory(), bean.getAuthor_name(), bean.getUrl(), bean.getThumbnail_pic_s(), " ", " "));
                        } else {
                            mmList.add(new MultipleItemNews(2, bean.getUniquekey(), bean.getTitle(), bean.getDate(), bean.getCategory(), bean.getAuthor_name(), bean.getUrl(), bean.getThumbnail_pic_s(), bean.getThumbnail_pic_s02(), " "));
                        }
                    } else {
                        mmList.add(new MultipleItemNews(3, bean.getUniquekey(), bean.getTitle(), bean.getDate(), bean.getCategory(), bean.getAuthor_name(), bean.getUrl(), bean.getThumbnail_pic_s(), bean.getThumbnail_pic_s02(), bean.getThumbnail_pic_s03()));
                    }

                }
               newsDataAdapter = new NewsDataAdapter(getContext(), mmList);
                mRecyclerView.setAdapter(newsDataAdapter);
                showContentView();
                newsDataAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<MultipleItemNews> listNull = new ArrayList<MultipleItemNews>();
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
