package com.jerry.webappdemo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseException extends RuntimeException {
    /**
     * @description Base class of self-defined errors
     * @author Yuchen Sun
     * @createDate 2022-01-14
     */
    protected int responseCode = 1000;
    protected int httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    protected String msg = "Unknown server error.";

    public BaseException() {
        super();
    }

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(int responseCode) {
        super();
        this.responseCode = responseCode;
    }

    public BaseException(int responseCode, Throwable cause) {
        super(cause);
        this.responseCode = responseCode;
    }

    public BaseException(int responseCode, String msg) {
        super(msg);
        this.msg = msg;
        this.responseCode = responseCode;
    }

    public BaseException(int responseCode, int httpCode) {
        super();
        this.responseCode = responseCode;
        this.httpCode = httpCode;
    }

    public BaseException(int responseCode, String msg, Throwable cause) {
        super(cause);
        this.responseCode = responseCode;
        this.msg = msg;
    }

    public BaseException(int responseCode, int httpCode, Throwable cause) {
        super(cause);
        this.responseCode = responseCode;
        this.httpCode = httpCode;
    }

    public BaseException(int responseCode, int httpCode, String msg) {
        super(msg);
        this.msg = msg;
        this.responseCode = responseCode;
        this.httpCode = httpCode;
    }

    public BaseException(int responseCode, int httpCode, String msg, Throwable cause) {
        super(cause);
        this.msg = msg;
        this.responseCode = responseCode;
        this.httpCode = httpCode;
    }
}
