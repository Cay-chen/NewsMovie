package com.example.cay.newsmovie.bean;

import java.util.List;

/**
 * Created by Cay on 2017/2/23.
 */

public class NewsResultBean {
    private String start;
    private List<NewsDataBean> data;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public List<NewsDataBean> getData() {
        return data;
    }

    public void setData(List<NewsDataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NewsResultBean{" +
                "start='" + start + '\'' +
                ", data=" + data +
                '}';
    }
}
