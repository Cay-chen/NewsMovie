package com.example.cay.newsmovie.http;

import com.example.cay.newsmovie.bean.MovieDataBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Cay on 2017/2/6.
 */

public class httpDatas {
    public static List<MovieDataBean> allMovieData() {
        OkHttpUtils.get().url("http://192.168.0.227:8080/VMovie/Data").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Object response, int id) {

            }
        });
        return null;
    }
}
