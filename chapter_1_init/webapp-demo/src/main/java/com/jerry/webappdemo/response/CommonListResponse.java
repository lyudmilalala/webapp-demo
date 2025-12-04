package com.jerry.webappdemo.response;

import lombok.Data;

import java.util.List;

@Data
public class CommonListResponse<T> extends CommonResponse {
    /**
     * @description Base List Response class
     * @author Yuchen Sun
     * @createDate 2021-10-20
     */
    private List<T> items;

    public CommonListResponse() {
        super();
    }

    public CommonListResponse(List<T> items) {
        super();
        this.items = items;
    }

    public CommonListResponse(int status, String msg) {
        super(status, msg);
    }

    public CommonListResponse(int status, String msg, List<T> items) {
        super(status, msg);
        this.items = items;
    }
}
