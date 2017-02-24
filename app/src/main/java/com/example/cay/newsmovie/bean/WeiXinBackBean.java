package com.example.cay.newsmovie.bean;

/**
 * Created by Cay on 2017/2/23.
 */

public class WeiXinBackBean {
    private String reason;
    private WeiXinResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public WeiXinResultBean getResult() {
        return result;
    }

    public void setResult(WeiXinResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "WeiXinBackBean{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }
}
