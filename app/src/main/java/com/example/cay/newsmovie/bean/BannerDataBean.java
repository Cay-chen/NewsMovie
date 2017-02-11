package com.example.cay.newsmovie.bean;

import java.util.Arrays;

/**
 * Created by Cay on 2017/2/9.
 */

public class BannerDataBean {
    private String[] imgs;
    private String[] titles;
    private String[] movieIds;
    private String[] types;

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public String[] getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(String[] movieIds) {
        this.movieIds = movieIds;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "BannerDataBean{" +
                "imgs=" + Arrays.toString(imgs) +
                ", titles=" + Arrays.toString(titles) +
                ", movieIds=" + Arrays.toString(movieIds) +
                ", types=" + Arrays.toString(types) +
                '}';
    }
}
