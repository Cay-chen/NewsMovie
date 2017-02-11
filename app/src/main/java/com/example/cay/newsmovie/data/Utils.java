package com.example.cay.newsmovie.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.cay.newsmovie.R;
import com.example.cay.newsmovie.bean.MovieDataBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Cay on 2017/2/6.
 */

public class Utils {
    private static boolean isIp = false;
    public static String country(int city, Context context) {
        String countryName =null;
        switch (city) {
            case 1:
                countryName = context.getResources().getString(R.string.CHA);
                break;
            case 2:
                countryName = context.getResources().getString(R.string.USA);
                break;
            case 3:
                countryName = context.getResources().getString(R.string.HK);
                break;
            case 4:
                countryName = context.getResources().getString(R.string.KOR);
                break;
            case 5:
                countryName = context.getResources().getString(R.string.JAP);
                break;
            case 6:
                countryName = context.getResources().getString(R.string.THA);
                break;
        }
        return  countryName;
    }

    public static String getData(String position, RecyclerView recyclerView, List<MovieDataBean> list) {
        String data=null;
        OkHttpUtils.get().url("http://192.168.0.227:8080/VMovie/Data").addParams("position",position).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                List<MovieDataBean> movieDataBeen = JSON.parseArray(response, MovieDataBean.class);
               // list.addAll(movieDataBeen);
               // data = response;
            }
        });

        return null;

    }
}
