package com.example.cay.newsmovie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.MovieCountItemAdapter;
import com.example.cay.newsmovie.app.MyApplication;
import com.example.cay.newsmovie.base.BaseHeaderActivity;
import com.example.cay.newsmovie.bean.MovieBean;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.example.cay.newsmovie.data.Utils;
import com.example.cay.newsmovie.databinding.ActivityOneMovieDetailBinding;
import com.example.cay.newsmovie.databinding.HeaderSlideShapeBinding;
import com.example.cay.newsmovie.utils.CommonUtils;
import com.example.cay.newsmovie.utils.ShareUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;

/**
 * 继承基类而写的电影详情页 2016-12-13
 */
public class MovieDetailActivity extends BaseHeaderActivity<HeaderSlideShapeBinding, ActivityOneMovieDetailBinding> {
    private String MOVIE_IP_URL = "http://60.205.183.88:8080/UpDataIp/servlet/MovieIp";
    // private MovieDataBean subjectsBean1;
    private String ip = null;
    private String movieId;
    private String img_url;
    private String name;
    private int mOnRefrse = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_movie_detail);
        if (getIntent() != null) {
            movieId = getIntent().getStringExtra("id");
            img_url = getIntent().getStringExtra("img_url");
            // subjectsBean1 = (MovieDataBean) getIntent().getSerializableExtra("bean");
        }
        bindingHeaderView.executePendingBindings();
        movieIp();
        Glide.with(this).load(img_url).bitmapTransform(new BlurTransformation(this, 23, 4)).into(bindingHeaderView.imgItemBg);
        Glide.with(this).load(img_url).into(bindingHeaderView.ivOnePhoto);

    }

    private void onLoadData(String id) {
        OkHttpUtils.get().url("http://60.205.183.88:8080/VMovie/FindDataServer").addParams("type", "id").addParams("value", id).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                showError();
            }

            @Override
            public void onResponse(String response, int id) {
                List<MovieDataBean> list = JSON.parseArray(response, MovieDataBean.class);
                setAllData(list.get(0));
                showContentView();
            }
        });
    }

    private void setAllData(MovieDataBean subjectsBean) {
        name = subjectsBean.getName();
        initSlideShapeTheme(setHeaderImgUrl(), setHeaderImageView());
        setTitle(name);
        setSubTitle(String.format("主演：%s", subjectsBean.getAct()));
        bindingHeaderView.tvOneRatingRate.setText(this.getResources().getString(R.string.string_rating) + subjectsBean.getCode());
        bindingHeaderView.tvOneCasts.setText(subjectsBean.getAct());
        bindingHeaderView.tvOneDirectors.setText(subjectsBean.getDirector());
        bindingHeaderView.tvOneDay.setText(this.getResources().getString(R.string.movie_year) + subjectsBean.getYear());
        bindingHeaderView.tvOneCity.setText(this.getResources().getString(R.string.movie_city) + Utils.country(Integer.parseInt(subjectsBean.getCity()), this));
        bindingHeaderView.tvOneGenres.setText(this.getResources().getString(R.string.string_type) + subjectsBean.getMovie_type());
        bindingContentView.tvOneContiont.setText(subjectsBean.getLog());
        bindingContentView.tvOneTitle.setText(subjectsBean.getOther_name());
        setMovieCount(subjectsBean);
    }

    private void setMovieCount(MovieDataBean subjectsBean) {
        List<MovieBean> mList = new ArrayList<>();
        switch (Integer.parseInt(subjectsBean.getType())) {
            case 1:
                for (int i = 1; i <= Integer.parseInt(subjectsBean.getNum()); i++) {
                    MovieBean bean = new MovieBean();
                    bean.setItemName("第" + String.valueOf(i) + "集");
                    bean.setMovieUrl("http://" + ip + ":8081/movie/" + subjectsBean.getMovie_url().trim() + "/" + i + ".mp4");
                    bean.setMovieName(subjectsBean.getName() + " 第" + String.valueOf(i) + "集");
                    bean.setType(1);
                    mList.add(bean);
                }
                break;
            case 2:
                MovieBean bean = new MovieBean();
                bean.setItemName("高清中字");
                bean.setMovieUrl("http://" + ip + ":8081/movie/" + subjectsBean.getMovie_url().trim() + ".mp4");
                bean.setMovieName(subjectsBean.getName());
                bean.setType(1);
                mList.add(bean);
                break;
            case 3:
                MovieBean bean3 = new MovieBean();
                bean3.setItemName("爱奇艺");
                bean3.setMovieUrl(subjectsBean.getMovie_url().trim());
                bean3.setMovieName(subjectsBean.getName());
                bean3.setType(2);
                mList.add(bean3);
                break;
            case 4:
                MovieBean bean4 = new MovieBean();
                bean4.setItemName("优酷视频");
                bean4.setMovieUrl(subjectsBean.getMovie_url().trim());
                bean4.setMovieName(subjectsBean.getName());
                bean4.setType(2);
                mList.add(bean4);
                break;
            case 5:
                MovieBean bean5 = new MovieBean();
                bean5.setItemName("腾讯视频");
                bean5.setMovieUrl(subjectsBean.getMovie_url().trim());
                bean5.setMovieName(subjectsBean.getName());
                bean5.setType(2);
                mList.add(bean5);
                break;
            case 6:
                MovieBean bean6 = new MovieBean();
                bean6.setItemName("芒果TV");
                bean6.setMovieUrl(subjectsBean.getMovie_url().trim());
                bean6.setMovieName(subjectsBean.getName());
                bean6.setType(2);
                mList.add(bean6);
                break;
            case 7:
                MovieBean bean7 = new MovieBean();
                bean7.setItemName("搜狐视频");
                bean7.setMovieUrl(subjectsBean.getMovie_url().trim());
                bean7.setMovieName(subjectsBean.getName());
                bean7.setType(2);
                mList.add(bean7);
                break;
            case 8:
                MovieBean bean8 = new MovieBean();
                bean8.setItemName("土豆");
                bean8.setMovieUrl(subjectsBean.getMovie_url().trim());
                bean8.setMovieName(subjectsBean.getName());
                bean8.setType(2);
                mList.add(bean8);
                break;
        }
        bindingContentView.rvCast.setLayoutManager(new GridLayoutManager(this, 4));
        bindingContentView.rvCast.setAdapter(new MovieCountItemAdapter(R.layout.movie_count_item, mList, this));
        if (ip == null) {
            movieIp();
        }
    }

    @Override
    protected void setTitleClickMore() {
        ShareUtils.share(this, "正在使用V视观看【" + name + "】 下载V视地址:https://fir.im/vision");

    }

    @Override
    protected int setHeaderLayout() {
        return R.layout.header_slide_shape;
    }

    @Override
    protected String setHeaderImgUrl() {
        if (img_url == null) {
            return "";
        }
        return img_url;
    }

    @Override
    protected ImageView setHeaderImageView() {
        return bindingHeaderView.imgItemBg;
    }


    public static void start(Activity context, MovieDataBean positionData, ImageView imageView) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("id", positionData.getId());
        intent.putExtra("img_url", positionData.getImg_url());
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, CommonUtils.getString(R.string.transition_movie_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

    public static void startE(Activity context, String id, String img_url, ImageView imageView) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("img_url", img_url);
        if (imageView != null) {
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                            imageView, CommonUtils.getString(R.string.transition_movie_img));//与xml文件对应
            ActivityCompat.startActivity(context, intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }

    }

    public void movieIp() {
        OkHttpUtils.get().url(MOVIE_IP_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                showError();
            }

            @Override
            public void onResponse(String response, int id) {
                ip = response.trim();
                mOnRefrse = 1;
                onLoadData(movieId);//  trim() 出去两边空格
            }
        });
    }

    @Override
    protected void onRefresh() {
        bindingContentView.activityOneMovieDetail.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mOnRefrse == 0) {
                    movieIp();
                } else {
                    onLoadData(movieId);
                }
            }
        }, 1000);


    }
}