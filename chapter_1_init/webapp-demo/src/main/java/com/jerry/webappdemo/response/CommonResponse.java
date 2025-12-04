package com.jerry.webappdemo.response;

import lombok.Data;

@Data
public class CommonResponse {
    private int status;
    private String msg;

    public CommonResponse() {
        this(200, "");
    }

    public CommonResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
