package com.example.cay.newsmovie.bean;

public class FirstRxDataBean {
    private int type;
    private String img1;
    private String mid1;
    private String con1;
    private String img2;
    private String mid2;
    private String con2;
    private String img3;
    private String mid3;
    private String con3;
    private String g_type;
    private String title;
    private boolean isTitle;

    public boolean getIsTitle() {return isTitle;
    }
    public void setIsTitle(boolean isTitle) {this.isTitle = isTitle;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getMid1() {
        return mid1;
    }

    public void setMid1(String mid1) {
        this.mid1 = mid1;
    }

    public String getCon1() {
        return con1;
    }

    public void setCon1(String con1) {
        this.con1 = con1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getMid2() {
        return mid2;
    }

    public void setMid2(String mid2) {
        this.mid2 = mid2;
    }

    public String getCon2() {
        return con2;
    }

    public void setCon2(String con2) {
        this.con2 = con2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getMid3() {
        return mid3;
    }

    public void setMid3(String mid3) {
        this.mid3 = mid3;
    }

    public String getCon3() {
        return con3;
    }

    public void setCon3(String con3) {
        this.con3 = con3;
    }

    public String getG_type() {
        return g_type;
    }

    public void setG_type(String g_type) {
        this.g_type = g_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FirstRxDataBean{" +
                "type=" + type +
                ", img1='" + img1 + '\'' +
                ", mid1='" + mid1 + '\'' +
                ", con1='" + con1 + '\'' +
                ", img2='" + img2 + '\'' +
                ", mid2='" + mid2 + '\'' +
                ", con2='" + con2 + '\'' +
                ", img3='" + img3 + '\'' +
                ", mid3='" + mid3 + '\'' +
                ", con3='" + con3 + '\'' +
                ", g_type='" + g_type + '\'' +
                ", title='" + title + '\'' +
                ", isTitle=" + isTitle +
                '}';
    }
}
