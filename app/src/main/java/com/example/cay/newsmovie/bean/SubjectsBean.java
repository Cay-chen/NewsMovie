package com.example.cay.newsmovie.bean;

import java.io.Serializable;


/**
 * Created by Cay on 2017/2/4.
 */

public class SubjectsBean implements Serializable {
    private String url;//图片
    private String mName; //电影名
    private String director;//导演
    private String type;//类型
    private String score;//评分
    private String year;//年份
    private int num;//级数
    private String synopsis;//剧情简介
    private String act;//主演
    private String movieUrl;//电影地址
    private String otherNmae;//又名

    public String getOtherNmae() {
        return otherNmae;
    }

    public void setOtherNmae(String otherNmae) {
        this.otherNmae = otherNmae;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    private String City;//diqu
    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
    public String getUrl() {
        return url;
    }

    public String getmName() {
        return mName;
    }
    public String getDirector() {
        return director;
    }
    public String getType() {
        return type;
    }
    public String getScore() {
        return score;
    }
    public String getYear() {
        return year;
    }
    public int getNum() {
        return num;
    }
    public String getSynopsis() {
        return synopsis;
    }
    public String getAct() {
        return act;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setAct(String act) {
        this.act = act;
    }


}
