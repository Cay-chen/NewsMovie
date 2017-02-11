package com.example.cay.newsmovie.http;

import android.content.Context;

/**
 * Created by Cay on 2017/1/20.
 */

public class HttpUtils {
    private Context context;
    private static HttpUtils sHttpUtils;

    public void setContext(Context context) {
        this.context = context;
    }

    public static HttpUtils getInstance() {
        if (sHttpUtils == null) {
            sHttpUtils = new HttpUtils();
        }
        return sHttpUtils;
    }
}
