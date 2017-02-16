package com.example.cay.newsmovie.bean;

import android.support.v4.view.PagerTitleStrip;

/**
 * Created by Cay on 2017/2/5.
 */

public class MovieBean {
    private String movieUrl;
    private String movieName;
    private String itemName;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
