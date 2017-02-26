package com.example.cay.newsmovie.bean;

/**
 * Created by Cay-chen on 2017/2/25.
 */

public class CommentDataBean {
    private String name;
    private String comment;
    private String time;
    private int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "CommentDataBean{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", time='" + time + '\'' +
                ", num=" + num +
                '}';
    }
}
