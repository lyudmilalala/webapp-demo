package com.jerry.webappdemo;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class HttpbinService extends BaseHttpClient {

    private static String BASE_URL = "https://httpbin.org";
    public CommonEntityResponse<?> simpleGet() {
        try {
            HashMap<String, String> headerList = new HashMap<>();
            headerList.put("Content-Type", "application/json;charset=UTF-8");
            headerList.put("Encode", "utf-8");
            headerList.put("Connection", "close");
            // some more headers for identify verification or signature

            HttpResponseDTO res = BaseHttpClient.httpGet(BASE_URL + "/get", headerList);
            if (res.getHttpCode() == 200) {
                return new CommonEntityResponse<JsonNode>(res.getBody());
            } else if (res.getHttpCode() > 400 && res.getHttpCode() < 500) {
                return new CommonEntityResponse<>(BaseHttpClient.HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, "Callee error.");
            } else {
                return new CommonEntityResponse<>(BaseHttpClient.HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, "Caller error.");
            }
        } catch (BaseException e) {
            return new CommonEntityResponse<>(e.getResponseCode(), e.getMsg());
        }
    }
}
