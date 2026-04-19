package com.jerry.webappdemojpa;

import lombok.Data;

@Data
public class CommonEntityResponse<T> extends CommonResponse {
    /**
     * @description Common response body for response with a single entity
     */
    T item;

    public CommonEntityResponse() {
        super();
    }

    public CommonEntityResponse(T item) {
        super();
        this.item = item;
    }

    public CommonEntityResponse(int status, String msg) {
        super(status, msg);
    }

    public CommonEntityResponse(int status, String msg, T item) {
        super(status, msg);
        this.item = item;
    }

}
