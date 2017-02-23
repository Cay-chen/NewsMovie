package com.example.cay.newsmovie.utils;

import android.util.Log;

import com.example.cay.newsmovie.base.DataCallBack;
import com.example.cay.newsmovie.bean.IssueBean;
import com.example.cay.newsmovie.bean.MovieDataBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Cay on 2017/2/21.
 */

public class GetDataUtils {
    private static final String TAG = "Cay";
    private Retrofit mRetrofit;
    private static GetDataUtils mGetDataUtils;
    private GetDataInfer infer;
    private static String SERVER_IP_ADDRESS = "http://60.205.183.88:8080/";
    private Call<List<MovieDataBean>> call;


    public static GetDataUtils getInstance() {
        if (mGetDataUtils == null) {
            mGetDataUtils = new GetDataUtils();
        }
        return mGetDataUtils;
    }

    /**
     * 单个模糊查询  主要用于查找电影
     *
     * @param dataCallBack 返回接口
     * @param type         类型
     * @param value        值
     */
    public void getSerchData(final DataCallBack dataCallBack, String type, String value) {
        if (mRetrofit == null) {
            Log.i(TAG, "getSerchData:mRetrofit 1");
            mRetrofit = new Retrofit.Builder().baseUrl(SERVER_IP_ADDRESS).addConverterFactory(GsonConverterFactory.create()).build();
        }
        if (infer == null) {
            Log.i(TAG, "getSerchData:mRetrofit 1");
            infer = mRetrofit.create(GetDataInfer.class);
        }
        call = infer.singelRequirementFindData(type, value);
        call.enqueue(new Callback<List<MovieDataBean>>() {
            @Override
            public void onResponse(Call<List<MovieDataBean>> call, Response<List<MovieDataBean>> response) {
                dataCallBack.success(response.body());
            }

            @Override
            public void onFailure(Call<List<MovieDataBean>> call, Throwable t) {
                dataCallBack.err();
            }
        });

    }

    /**
     * 多个条件查询
     *
     * @param callBack 返回接口
     * @param type1    条件1的名字
     * @param value1   条件1的值
     * @param type2    条件2的名字
     * @param value2   条件2的值
     * @param position 查询其实位置
     * @param num      查询多少个
     */
    public void muchFindData(String type1, String value1, String type2, String value2, String position, String num, final DataCallBack callBack) {
        if (mRetrofit == null) {
            Log.i(TAG, "getSerchData:mRetrofit 2");
            mRetrofit = new Retrofit.Builder().baseUrl(SERVER_IP_ADDRESS).addConverterFactory(GsonConverterFactory.create()).build();
        }
        if (infer == null) {
            Log.i(TAG, "getSerchData:mRetrofit 2");
            infer = mRetrofit.create(GetDataInfer.class);
        }

        if (type2 == null) {
            if (type1 == null) {
                call = infer.allFindData(position, num);
                call.enqueue(new Callback<List<MovieDataBean>>() {
                    @Override
                    public void onResponse(Call<List<MovieDataBean>> call, Response<List<MovieDataBean>> response) {
                        callBack.success(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<MovieDataBean>> call, Throwable t) {
                        callBack.err();
                    }
                });
            } else {
                //查询动漫，应该用模糊查询
                call = infer.singelRequirementFindData(type1, value1);
                call.enqueue(new Callback<List<MovieDataBean>>() {
                    @Override
                    public void onResponse(Call<List<MovieDataBean>> call, Response<List<MovieDataBean>> response) {
                        callBack.success(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<MovieDataBean>> call, Throwable t) {
                        callBack.err();
                    }
                });
            }
        } else {
            call = infer.twoRequirementFindData(type1, value1, type2, value2, position, num);
            call.enqueue(new Callback<List<MovieDataBean>>() {
                @Override
                public void onResponse(Call<List<MovieDataBean>> call, Response<List<MovieDataBean>> response) {
                    callBack.success(response.body());
                }

                @Override
                public void onFailure(Call<List<MovieDataBean>> call, Throwable t) {
                    callBack.err();
                }
            });
        }

    }

    /**
     * 获取问题列表
     * @param position 起始位置
     * @param num 数量
     * @param dataCallBack 返回接口
     */
    public void getIssueData(String position, String num,final DataCallBack dataCallBack) {
        if (mRetrofit == null) {
            Log.i(TAG, "getSerchData:mRetrofit 3");
            mRetrofit = new Retrofit.Builder().baseUrl(SERVER_IP_ADDRESS).addConverterFactory(GsonConverterFactory.create()).build();
        }
        if (infer == null) {
            Log.i(TAG, "getSerchData:mRetrofit 2");
            infer = mRetrofit.create(GetDataInfer.class);
        }
        infer.getIssueDataInf(position, num).enqueue(new Callback<List<IssueBean>>() {
            @Override
            public void onResponse(Call<List<IssueBean>> call, Response<List<IssueBean>> response) {
                dataCallBack.success(response.body());
            }
            @Override
            public void onFailure(Call<List<IssueBean>> call, Throwable t) {
                dataCallBack.err();
            }
        });

    }
}











