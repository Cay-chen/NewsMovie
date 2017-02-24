package com.example.cay.newsmovie.bean;

/**
 * Created by Cay on 2017/2/23.
 */

public class NewsBackDataBean {
    private String reason;
    private NewsResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public NewsResultBean getResult() {
        return result;
    }

    public void setResult(NewsResultBean result) {
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
        return "NewsBackDataBean{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }
}
