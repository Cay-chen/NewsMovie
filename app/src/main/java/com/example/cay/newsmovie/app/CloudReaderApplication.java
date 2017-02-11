package com.example.cay.newsmovie.app;

import android.app.Application;

import com.example.cay.newsmovie.http.HttpUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


/**
 * Created by jingbin on 2016/11/22.
 */

public class CloudReaderApplication extends Application {
    private static String MOVIE_IP_URL = "http://60.205.183.88:8080/UpDataIp/servlet/MovieIp";
    public static String ip = "";
    private static CloudReaderApplication cloudReaderApplication;

    public static CloudReaderApplication getInstance() {
        // if语句下是不会走的，Application本身已单例
        if (cloudReaderApplication == null) {
            synchronized (CloudReaderApplication.class) {
                if (cloudReaderApplication == null) {
                    cloudReaderApplication = new CloudReaderApplication();
                }
            }
        }
        return cloudReaderApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        cloudReaderApplication = this;
        HttpUtils.getInstance().setContext(getApplicationContext());
        movieIp();
    }
    public static void movieIp() {
        OkHttpUtils.get().url(MOVIE_IP_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ip = response.trim();   //  trim() 出去两边空格
            }
        });
    }
}
