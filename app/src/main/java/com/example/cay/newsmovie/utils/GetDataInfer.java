package com.example.cay.newsmovie.utils;



import com.example.cay.newsmovie.bean.HotMovieBean;
import com.example.cay.newsmovie.bean.IPBean;
import com.example.cay.newsmovie.bean.IssueBean;
import com.example.cay.newsmovie.bean.MovieDataBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *
 * Created by Cay on 2017/2/21.
 */

public interface GetDataInfer {
    /**
     * 获取电影动态IP
     * @return dd
     */
    @GET("UpDataIp/servlet/MovieIp")
    Call<IPBean> getMovieIP();


    /**
     * 1个条件精确查找
     * @param type1   type1 第一个条件的值
     * @param position  起始位置的值
     * @param num  查询的个数
     * @return dd
     */

    @GET("VMovie/MuchFindDataServer")
    Call<List<MovieDataBean>> oneRequirementFindData(@Query("type1") String type1, @Query("value1") String value1, @Query("position") String position, @Query("num") String num);


    /**
     * 2个条件精确查找
     * @param type1   type1 第一个条件的值
     * @param type2    第二个条件的值
     * @param position  起始位置的值
     * @param num  查询的个数
     * @return dd
     */

    @GET("VMovie/MuchFindDataServer")
    Call<List<MovieDataBean>> twoRequirementFindData(@Query("type1") String type1, @Query("value1") String value1, @Query("type2") String type2, @Query("value2") String value2, @Query("position") String position, @Query("num") String num);



    /**
     * 3个条件精确查找
     * @param type1   type1 第一个条件的值
     * @param type2    第二个条件的值
     * @param type3     第三个条件的值
     * @param position  起始位置的值
     * @param num  查询的个数
     * @return dd
     */

    @GET("VMovie/MuchFindDataServer")
    Call<List<MovieDataBean>> thrRequirementFindData(@Query("type1") String type1, @Query("value1") String value1, @Query("type2") String type2, @Query("value2") String value2, @Query("type3") String type3, @Query("value3") String value3, @Query("position") String position, @Query("num") String num);


    /**
     * 所有有查询
     * @param position  起始位置的值
     * @param num  查询的个数
     * @return dd
     */

    @GET("VMovie/MuchFindDataServer")
    Call<List<MovieDataBean>> allFindData(@Query("position") String position, @Query("num") String num);

    /**
     * 单个条件模糊查询，主要用于查找电影
     * @param type  type的名字
     * @param value type的值
     * @return dd
     */
    @GET("VMovie/FindDataServer")
    Call<List<MovieDataBean>> singelRequirementFindData(@Query("type") String type, @Query("value") String value);


    /**
     * 获取问题数据
     * @param position 起始位置
     * @param num   获取的数量
     * @return dd
     */
    @GET("VMovie/ServerIssueData")
    Call<List<IssueBean>> getIssueDataInf(@Query("position") String position, @Query("num") String num);


    /**
     * 上传问题
     * @param issue  问题
     * @return dd
     */
    @POST("VMovie/UpIssueServer")
    Call<String> upIssueData(@Query("issue") String issue);


    /**
     *h获取请求更新列表
     */
    @GET("VMovie/ServerGetUpMovieData")
    Call<List<IssueBean>> getUpMovieInf(@Query("position") String position, @Query("num") String num);

    /**
     * 上传播放数据
     * @param name aa
     * @param movie_id  aa
     * @param img_url  aa
     */
    @POST("VMovie/ServerCountMoviePlayerNum")
    Call<List<IssueBean>> upMoviePlayerData(@Query("name") String name, @Query("movie_id") String movie_id, @Query("img_url") String img_url);

    /**
     * 上传请求更新电影
     *
     * @param issue ss
     * @return ss
     */
    @POST("VMovie/ServerUpGetMovie")
    Call<List<IssueBean>> upMoviePlease(@Query("issue") String issue);


    /**
     * 上传登录统计
     * @return 等等
     */
    @POST("VMovie/ServerCountLogin")
    Call upPlayerData();

    /**
     *获取热映榜
     */

    @POST("VMovie/ServerGetHotMovieData")
    Call<List<HotMovieBean>> getHotMovieInf(@Query("position") String position, @Query("num") String num);

}
