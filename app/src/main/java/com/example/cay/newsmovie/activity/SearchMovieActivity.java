package com.example.cay.newsmovie.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.LoginFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.OneAdapter;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.example.cay.newsmovie.databinding.ActivitySearchMovieBinding;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class SearchMovieActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnTouchListener {
    private ActivitySearchMovieBinding movieBinding;
    private Toolbar mToolbar;
    private SearchView mSearchView;
    private InputMethodManager mImm;
    private RecyclerView mRecyclerView;
    private static final String TAG = "Cay";
    private OneAdapter mOneAdapter;
    private View notDataView;
    private View errorView;
    private View headView;
    private TextView title;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_movie);
        mToolbar = movieBinding.searchMovieToolbar;
        setSupportActionBar(mToolbar);
        getSupportActionBar().setLogo(R.drawable.icon_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initRecyclerView();
        title = (TextView) headView.findViewById(R.id.header_title);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovieDataHttp(mSearchView.getQuery().toString());

            }
        });
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyclerView.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovieDataHttp(mSearchView.getQuery().toString());

            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = movieBinding.searchMovieRv;
        mRecyclerView.setVisibility(View.GONE);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mOneAdapter = new OneAdapter(R.layout.item_one1, null, SearchMovieActivity.this);
        mRecyclerView.setAdapter(mOneAdapter);
        headView = getLayoutInflater().inflate(R.layout.header_item_one, (ViewGroup) mRecyclerView.getParent(), false);
        mOneAdapter.addHeaderView(headView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));

        mSearchView.setOnQueryTextListener(this);
        /*mSearchView.setQueryHint(getResources().getString(R.string.search_net_music));
        mSearchView.get*/
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setIconified(false);
        mSearchView.setSubmitButtonEnabled(true);
        //文字颜色
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) mSearchView.findViewById(id);
        if (searchText != null) {
            searchText.setText(getResources().getString(R.string.search_net_music));
            searchText.setTextColor(Color.CYAN);
            searchText.setHintTextColor(Color.CYAN);
        }
        /*    Field mCursorDrawableRes = null;
        try {

            mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchText, R.drawable.cursor_color);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.menu_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return false;
            }
        });

        menu.findItem(R.id.menu_search).expandActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchMovieDataHttp(mSearchView.getQuery().toString());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i(TAG, "onQueryTextChange: " + "改变");
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        hideInputManager();
        Log.i(TAG, "onTouch: ");
        return false;
    }

    public void hideInputManager() {
        if (mSearchView != null) {
            if (mImm != null) {
                mImm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
            }
            mSearchView.clearFocus();
            //SearchHistory.getInstance(this).addSearchString(mSearchView.getQuery().toString());
        }
    }

    public void searchMovieDataHttp(final String name) {
        mOneAdapter.setNewData(null);
        mOneAdapter.setEmptyView(R.layout.loading_view, (ViewGroup) mRecyclerView.getParent());
        mRecyclerView.setVisibility(View.VISIBLE);
        Log.i(TAG, "name: " + name);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.get().url("http://60.205.183.88:8080/VMovie/FindDataServer").addParams("type", "name").addParams("value", name).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mOneAdapter.setEmptyView(errorView);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<MovieDataBean> list = JSON.parseArray(response, MovieDataBean.class);
                        Log.i(TAG, "list.size(): " + list.size());
                        if (list.size() == 0) {
                            mOneAdapter.setEmptyView(notDataView);
                        } else {
                            title.setText("搜索到相关视频" + list.size() + "个");
                            mOneAdapter.setNewData(list);
                        }
                    }
                });
            }
        }, 1000);
    }

}
