package com.example.cay.newsmovie.bean;

public class MovieTopbarBean {
	private String name;
	private String img_url;
	private String movie_id;
	private String title;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(String movie_id) {
		this.movie_id = movie_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "MovieTopbarBean{" +
				"name='" + name + '\'' +
				", img_url='" + img_url + '\'' +
				", movie_id='" + movie_id + '\'' +
				", title='" + title + '\'' +
				'}';
	}
}
