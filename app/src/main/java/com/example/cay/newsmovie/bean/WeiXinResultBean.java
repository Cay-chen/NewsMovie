package com.example.cay.newsmovie.bean;

import java.util.List;

/**
 * Created by Cay on 2017/2/23.
 */

public class WeiXinResultBean {
    private List<NewsWeiXinBean> list;
    private int totalPage;
    private int ps;
    private int pno;

    public List<NewsWeiXinBean> getList() {
        return list;
    }

    public void setList(List<NewsWeiXinBean> list) {
        this.list = list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public int getPno() {
        return pno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }

    @Override
    public String toString() {
        return "WeiXinResultBean{" +
                "list=" + list +
                ", totalPage=" + totalPage +
                ", ps=" + ps +
                ", pno=" + pno +
                '}';
    }
}
