package com.example.cay.newsmovie.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.adapter.MovieCountItemAdapter;
import com.example.cay.newsmovie.base.BaseHeaderActivity;
import com.example.cay.newsmovie.bean.MovieBean;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.example.cay.newsmovie.bean.UpDdtaBackBean;
import com.example.cay.newsmovie.data.Utils;
import com.example.cay.newsmovie.databinding.ActivityOneMovieDetailBinding;
import com.example.cay.newsmovie.databinding.HeaderSlideShapeBinding;
import com.example.cay.newsmovie.http.HttpUtils;
import com.example.cay.newsmovie.utils.CommonUtils;
import com.example.cay.newsmovie.utils.ShareUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 继承基类而写的电影详情页 2016-12-13
 */
public class MovieDetailActivity extends BaseHeaderActivity<HeaderSlideShapeBinding, ActivityOneMovieDetailBinding> {
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
        }
       // bindingHeaderView.executePendingBindings();
        movieIp();



        Glide.with(this).load(img_url).bitmapTransform(new BlurTransformation(this, 23, 4)).into(bindingHeaderView.imgItemBg);
        Glide.with(this).load(img_url).into(bindingHeaderView.ivOnePhoto);

    }

    private void onLoadData(String id) {
        HttpUtils.getInstance().getMyObservableClient().singelRequirementFindData("id",id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MovieDataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MovieDataBean> list) {
                        setAllData(list.get(0));
                        showContentView();
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
        if (subjectsBean.getMe_type() == 0) {
            if (Integer.parseInt(subjectsBean.getType()) == 1) {
                for (int i = 1; i <= Integer.parseInt(subjectsBean.getNum()); i++) {
                    MovieBean bean = new MovieBean();
                    bean.setAllName(subjectsBean.getName());
                    bean.setImg_url(subjectsBean.getImg_url());
                    bean.setMovieId(subjectsBean.getId());
                    bean.setItemName("第" + String.valueOf(i) + "集");
                    bean.setMovieUrl("http://" + ip + ":8081/movie/" + subjectsBean.getMovie_url().trim() + "/" + i + ".mp4");
                    bean.setMovieName(subjectsBean.getName() + " 第" + String.valueOf(i) + "集");
                    bean.setType(1);
                    mList.add(bean);
                }

            } else {
                MovieBean bean = new MovieBean();
                bean.setAllName(subjectsBean.getName());
                bean.setImg_url(subjectsBean.getImg_url());
                bean.setMovieId(subjectsBean.getId());
                bean.setItemName("高清中字");
                bean.setMovieUrl("http://" + ip + ":8081/movie/" + subjectsBean.getMovie_url().trim() + ".mp4");
                bean.setMovieName(subjectsBean.getName());
                bean.setType(1);
                mList.add(bean);
            }
        } else {
            switch (subjectsBean.getMe_type()) {
                case 1:
                    MovieBean bean3 = new MovieBean();
                    bean3.setItemName("爱奇艺");
                    bean3.setImg_url(subjectsBean.getImg_url());
                    bean3.setMovieId(subjectsBean.getId());
                    bean3.setAllName(subjectsBean.getName());
                    bean3.setMovieUrl(subjectsBean.getMovie_url().trim());
                    bean3.setMovieName(subjectsBean.getName());
                    bean3.setType(2);
                    mList.add(bean3);
                    break;
                case 2:
                    MovieBean bean4 = new MovieBean();
                    bean4.setItemName("优酷视频");
                    bean4.setImg_url(subjectsBean.getImg_url());
                    bean4.setMovieId(subjectsBean.getId());
                    bean4.setAllName(subjectsBean.getName());
                    bean4.setMovieUrl(subjectsBean.getMovie_url().trim());
                    bean4.setMovieName(subjectsBean.getName());
                    bean4.setType(2);
                    mList.add(bean4);
                    break;
                case 3:
                    MovieBean bean5 = new MovieBean();
                    bean5.setItemName("腾讯视频");
                    bean5.setImg_url(subjectsBean.getImg_url());
                    bean5.setMovieId(subjectsBean.getId());
                    bean5.setAllName(subjectsBean.getName());
                    bean5.setMovieUrl(subjectsBean.getMovie_url().trim());
                    bean5.setMovieName(subjectsBean.getName());
                    bean5.setType(2);
                    mList.add(bean5);
                    break;
                case 4:
                    MovieBean bean6 = new MovieBean();
                    bean6.setItemName("芒果TV");
                    bean6.setImg_url(subjectsBean.getImg_url());
                    bean6.setMovieId(subjectsBean.getId());
                    bean6.setAllName(subjectsBean.getName());
                    bean6.setMovieUrl(subjectsBean.getMovie_url().trim());
                    bean6.setMovieName(subjectsBean.getName());
                    bean6.setType(2);
                    mList.add(bean6);
                    break;
                case 5:
                    MovieBean bean7 = new MovieBean();
                    bean7.setAllName(subjectsBean.getName());
                    bean7.setItemName("搜狐视频");
                    bean7.setImg_url(subjectsBean.getImg_url());
                    bean7.setMovieId(subjectsBean.getId());
                    bean7.setMovieUrl(subjectsBean.getMovie_url().trim());
                    bean7.setMovieName(subjectsBean.getName());
                    bean7.setType(2);
                    mList.add(bean7);
                    break;
                case 6:
                    MovieBean bean8 = new MovieBean();
                    bean8.setAllName(subjectsBean.getName());
                    bean8.setItemName("土豆");
                    bean8.setImg_url(subjectsBean.getImg_url());
                    bean8.setMovieId(subjectsBean.getId());
                    bean8.setMovieUrl(subjectsBean.getMovie_url().trim());
                    bean8.setMovieName(subjectsBean.getName());
                    bean8.setType(2);
                    mList.add(bean8);
                    break;
                case 7:
                    MovieBean bean9 = new MovieBean();
                    bean9.setAllName(subjectsBean.getName());
                    bean9.setItemName("乐视视频");
                    bean9.setImg_url(subjectsBean.getImg_url());
                    bean9.setMovieId(subjectsBean.getId());
                    bean9.setMovieUrl(subjectsBean.getMovie_url().trim());
                    bean9.setMovieName(subjectsBean.getName());
                    bean9.setType(2);
                    mList.add(bean9);
                    break;
                default:
                    MovieBean bean10 = new MovieBean();
                    bean10.setAllName(subjectsBean.getName());
                    bean10.setItemName("未知");
                    bean10.setImg_url(subjectsBean.getImg_url());
                    bean10.setMovieId(subjectsBean.getId());
                    bean10.setMovieUrl(subjectsBean.getMovie_url().trim());
                    bean10.setMovieName(subjectsBean.getName());
                    bean10.setType(2);
                    mList.add(bean10);
            }
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
    
    public static void start(Activity context, String id, String img_url, ImageView imageView) {
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
        HttpUtils.getInstance().getMyObservableClient().getMovieIP()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpDdtaBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpDdtaBackBean value) {
                        ip = value.getResMsg().trim();
                        mOnRefrse = 1;
                        onLoadData(movieId);//  trim() 出去两边空格
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
