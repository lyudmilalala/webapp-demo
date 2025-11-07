package com.jerry.commonutils;

import lombok.Data;

@Data
public class CommonEntityResponse<T> extends CommonResponse {
    private T item;

    public CommonEntityResponse() {
        super(200, "");
    }

    public CommonEntityResponse(T  item) {
        super(200, "");
        this.item = item;
    }

    public CommonEntityResponse(int status, String msg) {
        super(status, msg);
    }
}