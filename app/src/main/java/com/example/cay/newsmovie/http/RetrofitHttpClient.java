package com.example.cay.newsmovie.http;

import com.example.cay.newsmovie.bean.BannerDataBean;
import com.example.cay.newsmovie.bean.CommentDataBean;
import com.example.cay.newsmovie.bean.FirstRxDataBean;
import com.example.cay.newsmovie.bean.HotMovieBean;
import com.example.cay.newsmovie.bean.IssueBean;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.example.cay.newsmovie.bean.MovieTopbarBean;
import com.example.cay.newsmovie.bean.NewsBackDataBean;
import com.example.cay.newsmovie.bean.UpDdtaBackBean;
import com.example.cay.newsmovie.bean.VersionUpdataBean;
import com.example.cay.newsmovie.bean.WeiXinBackBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 请求数据接口
 * Created by Cay on 2017/2/23.
 */

public interface RetrofitHttpClient {
    /**
     * 获取电影动态IP
     *
     * @return dd
     */
    @GET("VMovie/ServerGetMovieIp")
    Observable<UpDdtaBackBean> getMovieIP();


    /**
     * 1个条件精确查找
     *
     * @param type1    type1 第一个条件的值
     * @param position 起始位置的值
     * @param num      查询的个数
     * @return dd
     */

    @GET("VMovie/MuchFindDataServer")
    Observable<List<MovieDataBean>> oneRequirementFindData(@Query("type1") String type1, @Query("value1") String value1, @Query("position") String position, @Query("num") String num);


    /**
     * 2个条件精确查找
     *
     * @param type1    type1 第一个条件的值
     * @param type2    第二个条件的值
     * @param position 起始位置的值
     * @param num      查询的个数
     * @return dd
     */

    @GET("VMovie/MuchFindDataServer")
    Observable<List<MovieDataBean>> twoRequirementFindData(@Query("type1") String type1, @Query("value1") String value1, @Query("type2") String type2, @Query("value2") String value2, @Query("position") String position, @Query("num") String num);


    /**
     * 3个条件精确查找
     *
     * @param type1    type1 第一个条件的值
     * @param type2    第二个条件的值
     * @param type3    第三个条件的值
     * @param position 起始位置的值
     * @param num      查询的个数
     * @return dd
     */

    @GET("VMovie/MuchFindDataServer")
    Call<List<MovieDataBean>> thrRequirementFindData(@Query("type1") String type1, @Query("value1") String value1, @Query("type2") String type2, @Query("value2") String value2, @Query("type3") String type3, @Query("value3") String value3, @Query("position") String position, @Query("num") String num);


    /**
     * 所有有查询
     *
     * @param position 起始位置的值
     * @param num      查询的个数
     * @return dd
     */

    @GET("VMovie/MuchFindDataServer")
    Observable<List<MovieDataBean>> allFindData(@Query("position") String position, @Query("num") String num);

    /**
     * 单个条件模糊查询，主要用于查找电影
     *
     * @param type  type的名字
     * @param value type的值
     * @return dd
     */
    @GET("VMovie/FindDataServer")
    Observable<List<MovieDataBean>> singelRequirementFindData(@Query("type") String type, @Query("value") String value);


    /**
     * 获取问题数据
     *
     * @param position 起始位置
     * @param num      获取的数量
     * @return dd
     */
    @GET("VMovie/ServerIssueData")
    Observable<List<IssueBean>> getIssueDataInf(@Query("position") String position, @Query("num") String num);


    /**
     * 上传问题
     *
     * @param issue 问题
     * @return dd
     */
    @POST("VMovie/UpIssueServer")
    Observable<UpDdtaBackBean> upIssueData(@Query("issue") String issue);


    /**
     * 获取请求更新列表
     */
    @GET("VMovie/ServerGetUpMovieData")
    Observable<List<IssueBean>> getUpMovieData(@Query("position") String position, @Query("num") String num);

    /**
     * 上传播放数据
     *
     * @param name     aa
     * @param movie_id aa
     * @param img_url  aa
     */
    @POST("VMovie/ServerCountMoviePlayerNum")
    Observable<UpDdtaBackBean> upMoviePlayerData(@Query("name") String name, @Query("movie_id") String movie_id, @Query("img_url") String img_url);

    /**
     * 上传请求更新电影
     *
     * @param issue ss
     * @return ss
     */
    @POST("VMovie/ServerUpGetMovie")
    Observable<UpDdtaBackBean> upMoviePlease(@Query("issue") String issue);


    /**
     * 上传登录统计
     *
     * @return 等等
     */
    @POST("VMovie/ServerCountLogin")
    Observable<UpDdtaBackBean> upCountLogin();

    /**
     * 获取热映榜
     */

    @POST("VMovie/ServerGetHotMovieData")
    Observable<List<HotMovieBean>> getHotMovieData(@Query("position") String position, @Query("num") String num);

    /**
     * 获取新闻数据
     *
     * @param type 类型
     * @return dd
     */
    @GET("/toutiao/index")
    Observable<NewsBackDataBean> getNewsData(@Query("type") String type, @Query("key") String key);

    /**
     * 获取微信数据
     *
     * @param key 秘钥
     * @return dd
     */
    @GET("/weixin/query")
    Observable<WeiXinBackBean> getWeiXinData(@Query("key") String key);

    /**
     * 请求top数据
     *
     * @param type 类型
     * @return ss
     */
    @GET("VMovie/ServerTopbarData")
    Observable<List<MovieTopbarBean>> getTopBarData(@Query("type") String type);

    /**
     * 请求轮播图数据
     * @return d
     */
    @GET("VMovie/BannerDataServer")
    Observable<BannerDataBean> getBannerData();


    /**
     * 每日推荐 rv数据
     * @return ss
     */
    @GET("VMovie/FirstRxDataServer")
    Observable<List<FirstRxDataBean>> getEveryDayRvData();

    /**
     * 版本更新检测
     * @return
     */
    @GET("VMovie/VersionUpdataServer")
    Observable<List<VersionUpdataBean>> verJianCe();

    /**
     * 上穿评论
     * @param name 电影名字
     * @param comment 评论
     * @return dd
     */
    @POST("VMovie/ServerComment")
    Observable<UpDdtaBackBean> upCommentData(@Query("name") String name, @Query("comment") String comment);
    /**
     * 获取评论数据
     * @param position 其实位置
     * @param num 数量
     * @return dd
     */
    @GET("VMovie/ServerGetCommentData")
    Observable<List<CommentDataBean>> getCommentData(@Query("name")String name, @Query("position") String position, @Query("num") String num);

}

