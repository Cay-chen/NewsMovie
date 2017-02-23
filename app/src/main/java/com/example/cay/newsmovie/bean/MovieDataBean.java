package com.example.cay.newsmovie.bean;

import java.io.Serializable;

public class MovieDataBean implements Serializable {
	private String id;//电影ID
	private String name;//名字
	private String num;//级数
	private String type;//视频类型 1为电视剧 2为电影
	private String movie_type;//视频类型   惊险 剧情
	private String city;//国家
	private String year;//上映时间
	private String director;//导演
	private String act;//主演
	private String code;//评分
	private String other_name;//又名
	private String movie_url;//电影地址
	private String img_url;//图片地址
	private String log;//剧情
	private int me_type;//资源地方

	public int getMe_type() {
		return me_type;
	}

	public void setMe_type(int me_type) {
		this.me_type = me_type;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMovie_type() {
		return movie_type;
	}
	public void setMovie_type(String movie_type) {
		this.movie_type = movie_type;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOther_name() {
		return other_name;
	}
	public void setOther_name(String other_name) {
		this.other_name = other_name;
	}
	public String getMovie_url() {
		return movie_url;
	}
	public void setMovie_url(String movie_url) {
		this.movie_url = movie_url;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	@Override
	public String toString() {
		return "MovieDataBean{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", num='" + num + '\'' +
				", type='" + type + '\'' +
				", movie_type='" + movie_type + '\'' +
				", city='" + city + '\'' +
				", year='" + year + '\'' +
				", director='" + director + '\'' +
				", act='" + act + '\'' +
				", code='" + code + '\'' +
				", other_name='" + other_name + '\'' +
				", movie_url='" + movie_url + '\'' +
				", img_url='" + img_url + '\'' +
				", log='" + log + '\'' +
				", me_type=" + me_type +
				'}';
	}
}
