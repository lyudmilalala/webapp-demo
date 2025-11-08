package com.jerry.common.response;

import lombok.Data;

import java.util.List;

@Data
public class CommonListResponse<T> extends CommonResponse {

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
}