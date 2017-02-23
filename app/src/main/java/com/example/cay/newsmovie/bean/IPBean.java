package com.example.cay.newsmovie.bean;

/**
 * Created by Cay on 2017/2/21.
 */

public class IPBean {
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "IPBean{" +
                "ip='" + ip + '\'' +
                '}';
    }
}
