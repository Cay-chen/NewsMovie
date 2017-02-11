package com.example.cay.newsmovie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.MovieCountItemAdapter;
import com.example.cay.newsmovie.app.CloudReaderApplication;
import com.example.cay.newsmovie.base.BaseHeaderActivity;
import com.example.cay.newsmovie.bean.MovieBean;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.example.cay.newsmovie.data.Utils;
import com.example.cay.newsmovie.databinding.ActivityOneMovieDetailBinding;
import com.example.cay.newsmovie.databinding.HeaderSlideShapeBinding;
import com.example.cay.newsmovie.utils.CommonUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;

/**
 * 继承基类而写的电影详情页 2016-12-13
 */
public class OneMovieDetailActivity extends BaseHeaderActivity<HeaderSlideShapeBinding, ActivityOneMovieDetailBinding> {
    private static final String TAG = "Cay";
   // private MovieDataBean subjectsBean1;
    private String id;
    private String img_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_movie_detail);
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            img_url = getIntent().getStringExtra("img_url");
            // subjectsBean1 = (MovieDataBean) getIntent().getSerializableExtra("bean");
        }
        bindingHeaderView.executePendingBindings();
        Glide.with(this).load(img_url).bitmapTransform(new BlurTransformation(this, 23, 4)).into(bindingHeaderView.imgItemBg);
        Glide.with(this).load(img_url).into(bindingHeaderView.ivOnePhoto);
        onLoadData(id);
    }

    private void onLoadData(String id) {
        OkHttpUtils.get().url("http://60.205.183.88:8080/VMovie/FindDataServer").addParams("type","id").addParams("value",id).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

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
        initSlideShapeTheme(setHeaderImgUrl(), setHeaderImageView());
        setTitle(subjectsBean.getName());
        setSubTitle(String.format("主演：%s", subjectsBean.getAct()));
        bindingHeaderView.tvOneRatingRate.setText(this.getResources().getString(R.string.string_rating) + subjectsBean.getCode());
        bindingHeaderView.tvOneCasts.setText(subjectsBean.getAct());
        bindingHeaderView.tvOneDirectors.setText(subjectsBean.getDirector());
        bindingHeaderView.tvOneDay.setText(this.getResources().getString(R.string.movie_year) + subjectsBean.getYear());
        bindingHeaderView.tvOneCity.setText(this.getResources().getString(R.string.movie_city) + Utils.country(Integer.parseInt(subjectsBean.getCity()),this) );
        bindingHeaderView.tvOneGenres.setText(this.getResources().getString(R.string.string_type) + subjectsBean.getMovie_type());
        bindingContentView.tvOneContiont.setText(subjectsBean.getLog());
        bindingContentView.tvOneTitle.setText(subjectsBean.getOther_name());
        setMovieCount(subjectsBean);
    }

    private void setMovieCount(MovieDataBean subjectsBean) {
        List<MovieBean> mList = new ArrayList<>();
        if (subjectsBean.getType().equals("1")) {
            for (int i = 1; i <= Integer.parseInt(subjectsBean.getNum() ); i++) {
                MovieBean bean = new MovieBean();
                bean.setItemName("第" + String.valueOf(i) + "集");
                movieIpTest();
                bean.setMovieUrl("http://"+ CloudReaderApplication.ip+":8081/movie/"+subjectsBean.getMovie_url()+"/"+i+".mp4");
                bean.setMovieName(subjectsBean.getName() + " 第" + String.valueOf(i) + "集");
                mList.add(bean);
            }
        } else {
            MovieBean bean = new MovieBean();
            bean.setItemName("高清中字");
            bean.setMovieUrl("http://"+CloudReaderApplication.ip+":8081/movie/"+subjectsBean.getMovie_url()+".mp4");
            bean.setMovieName(subjectsBean.getName());
            mList.add(bean);
        }
        bindingContentView.rvCast.setLayoutManager(new GridLayoutManager(this, 4));
        bindingContentView.rvCast.setAdapter(new MovieCountItemAdapter(R.layout.movie_count_item, mList, this));
    }

    /**
     * 判断Ip是都获取到
     */
    public void movieIpTest() {
        if (CloudReaderApplication.ip.isEmpty()) {
            CloudReaderApplication.movieIp();
            if (CloudReaderApplication.ip.isEmpty()) {
                movieIpTest();
            }
        }
    }
    @Override
    protected void setTitleClickMore() {
       // WebViewActivity.loadUrl(OneMovieDetailActivity.this, mMoreUrl, mMovieName);
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
        Intent intent = new Intent(context, OneMovieDetailActivity.class);
        intent.putExtra("id", positionData.getId());
        intent.putExtra("img_url",positionData.getImg_url());
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, CommonUtils.getString(R.string.transition_movie_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }
    public static void startE(Activity context, String id, String img_url, ImageView imageView) {
        Intent intent = new Intent(context, OneMovieDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("img_url",img_url);
        if (imageView != null) {
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                            imageView, CommonUtils.getString(R.string.transition_movie_img));//与xml文件对应
            ActivityCompat.startActivity(context, intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }

    }

}
