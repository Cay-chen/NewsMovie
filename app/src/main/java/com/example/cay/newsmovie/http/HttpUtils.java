package com.example.cay.newsmovie.http;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/** 请求数据工具
 * Created by Cay on 2017/1/20.
 */

public class HttpUtils {
    private Context context;
    private static HttpUtils sHttpUtils;
    private RetrofitHttpClient mJuHeClient;
    private RetrofitHttpClient mMyObservableClient;
    private OkHttpClient okHttpClient;
    public static HttpUtils getInstance() {
        if (sHttpUtils == null) {
            sHttpUtils = new HttpUtils();
        }
        return sHttpUtils;
    }


    public void setContext(Context context) {
        this.context = context;
    }
    /**
     * 创建Okhttp客服端
     */
    private   OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            File cacheFile = new File(context.getApplicationContext().getCacheDir().getAbsolutePath(), "HttpCache");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(cacheFile, cacheSize);
            okHttpClient = new OkHttpClient.Builder()
                    .cache(cache).readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20,TimeUnit.SECONDS).writeTimeout(20,TimeUnit.SECONDS).build();
        }
        return okHttpClient;
    }


    public RetrofitHttpClient getJuHeDataUtil() {
        if (mJuHeClient == null) {
            mJuHeClient = new Retrofit.Builder().baseUrl("http://v.juhe.cn").addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(getOkHttpClient()).build().create(RetrofitHttpClient.class);
        }
        return mJuHeClient;
    }

    public RetrofitHttpClient getMyObservableClient() {
        if (mMyObservableClient == null) {
            mMyObservableClient = new Retrofit.Builder().baseUrl("http://60.205.183.88:8080/").addConverterFactory(FastJsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(getOkHttpClient()).build().create(RetrofitHttpClient.class);
        }
        return mMyObservableClient;
    }
}
