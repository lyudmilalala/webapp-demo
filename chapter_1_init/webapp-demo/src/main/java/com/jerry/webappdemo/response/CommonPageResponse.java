package com.jerry.webappdemo.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class CommonPageResponse<T> extends CommonResponse {
    /**
     * @description Base Response Class
     * @author Yuchen Sun
     * @createDate 2021-10-20
     */
    protected List<T> items;
    protected int pageLimit;
    protected int totalSize;

    public CommonPageResponse() {super();}

    public CommonPageResponse(List<T> items, int pageLimit, int totalSize) {
        super();
        this.items = items;
        this.pageLimit = pageLimit;
        this.totalSize = totalSize;
    }

    public CommonPageResponse(Page<T> page) {
        super();
        this.items = page.getContent();
        this.pageLimit = page.getSize();
        this.totalSize = Integer.parseInt(String.valueOf(page.getTotalElements()));
    }

    public CommonPageResponse(int status, String msg) {
        super(status, msg);
    }

    public CommonPageResponse(int status, String msg, List<T> items, int pageLimit, int totalSize) {
        super(status, msg);
        this.items = items;
        this.pageLimit = pageLimit;
        this.totalSize = totalSize;
    }
}
