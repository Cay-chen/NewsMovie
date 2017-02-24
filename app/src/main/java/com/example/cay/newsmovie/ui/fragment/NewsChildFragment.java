package com.example.cay.newsmovie.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.example.cay.newsmovie.MainActivity;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.NewsDataAdapter;
import com.example.cay.newsmovie.base.adapter.BaseFragment;
import com.example.cay.newsmovie.base.adapter.MultipleItemNews;
import com.example.cay.newsmovie.bean.NewsBackDataBean;
import com.example.cay.newsmovie.bean.NewsDataBean;
import com.example.cay.newsmovie.databinding.FragmentNewsTopBinding;
import com.example.cay.newsmovie.http.HttpUtils;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**dd
 * Created by Cay on 2017/2/3.
 */

public class NewsChildFragment extends BaseFragment<FragmentNewsTopBinding> {
    private List<MultipleItemNews> mmList;
    private RecyclerView mRecyclerView;
    private NewsDataAdapter newsDataAdapter;
    private MainActivity activity;
    protected String type;
    private boolean isFirst =true;

    static NewsChildFragment newInstance(String s){
        NewsChildFragment myFragment = new NewsChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA",s);
        myFragment.setArguments(bundle);
        return myFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString("DATA");
        }
    }

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
        if (isFirst){
            HttpUtils.getInstance().getJuHeDataUtil().getNewsData(type,"f9141352fe34615b0806e9fd943f9dbd").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<NewsBackDataBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(NewsBackDataBean value) {
                    mmList = new ArrayList<>();
                    for (int i = 0; i < value.getResult().getData().size(); i++) {
                        NewsDataBean bean = value.getResult().getData().get(i);
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
                    isFirst=false;
                    showContentView();
                    newsDataAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            mRecyclerView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    List<MultipleItemNews> listNull = new ArrayList<>();
                                    newsDataAdapter.addData(listNull);
                                    newsDataAdapter.loadMoreComplete();
                                    newsDataAdapter.loadMoreEnd(false);
                                }
                            }, 1500);

                        }
                    });
                }

                @Override
                public void onError(Throwable e) {
                    showError();
                }

                @Override
                public void onComplete() {

                }
            });
        }

    }

    @Override
    protected void onRefresh() {
        loadData();
    }
}
