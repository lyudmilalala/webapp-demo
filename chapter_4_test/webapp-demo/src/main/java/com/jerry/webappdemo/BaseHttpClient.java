package com.jerry.webappdemo;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.BiPredicate;

@Slf4j
@Service
public class BaseHttpClient {
    static SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(300000).build();
    static CloseableHttpClient client = HttpClients.custom().setDefaultSocketConfig(socketConfig).build();

    public static int HTTP_REQUEST_CALLER_SIDE_ERROR_CODE = 1001;
    public static int HTTP_REQUEST_CALLEE_SIDE_ERROR_CODE = 1002;

    public static HttpResponseDTO httpGet(String urlStr, Map<String, String> headerList) {
        URI uri = null;
        try {
            uri = new URIBuilder(urlStr).build();
        } catch (URISyntaxException e) {
            throw new BaseException(HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, 500, "Url building error", e);
        }
        // set url
        HttpGet httpGet = new HttpGet(uri);
        // set header
        if (headerList != null) {
            for (Map.Entry<String, String> entry : headerList.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
        // send request
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            String resStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            log.info("statusCode = {}, raw resStr = {}", statusCode, resStr);
            JsonNode res = JsonUtils.objectMapper.readTree(resStr);
            return new HttpResponseDTO(statusCode, res);
        } catch (IOException e) {
            throw new BaseException(HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, 500, e.getMessage(), e);
        }
    }

    public static HttpResponseDTO httpPost(String urlStr, Map<String, String> headerList, String bodyStr) {
        URI uri = null;
        try {
            uri = new URIBuilder(urlStr).build();
        } catch (URISyntaxException e) {
            throw new BaseException(HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, 500, "Url building error", e);
        }
        // set url
        HttpPost httpost = new HttpPost(uri);
        // set header
        if (headerList != null) {
            for (Map.Entry<String, String> entry : headerList.entrySet()) {
                httpost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        // set body
        StringEntity entity = new StringEntity(bodyStr, StandardCharsets.UTF_8);
        httpost.setEntity(entity);
        // send request
        try {
            CloseableHttpResponse response = client.execute(httpost);
            int statusCode = response.getStatusLine().getStatusCode();
            String resStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            log.info("statusCode = {}, raw resStr = {}", statusCode, resStr);
            JsonNode res = JsonUtils.objectMapper.readTree(resStr);
            return new HttpResponseDTO(statusCode, res);
        } catch (IOException e) {
            throw new BaseException(HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, 500, e.getMessage(), e);
        }
    }

    public static HttpResponseDTO httpGetWithRetry(String urlStr, Map<String, String> headerList, int retryTimes, BiPredicate<Integer, String> retryPredicate) {
        URI uri = null;
        try {
            uri = new URIBuilder(urlStr).build();
        } catch (URISyntaxException e) {
            throw new BaseException(HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, 500, "Url building error", e);
        }

        // set url
        HttpGet httpGet = new HttpGet(uri);
        // set header
        if (headerList != null) {
            for (Map.Entry<String, String> entry : headerList.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }

        int attempt = 0;
        do {
            CloseableHttpResponse response = null;
            try {
                response = client.execute(httpGet);
                int statusCode = response.getStatusLine().getStatusCode();
                String resStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                // if pass the check, stop looping and return response
                if (retryPredicate.test(statusCode, resStr)) {
                    JsonNode res = JsonUtils.objectMapper.readTree(resStr);
                    return new HttpResponseDTO(statusCode, res);
                }
                // otherwise close current response and continue to next attempt
                response.close();
            } catch (IOException e) {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException closeEx) {
                        log.warn("Failed to close response", closeEx);
                    }
                }
            }
            attempt++;
        } while (attempt < retryTimes);
        throw new BaseException(HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, 500, "Exceed max retry times.");
    }

    public static HttpResponseDTO httpPostWithRetry(String urlStr, Map<String, String> headerList, String bodyStr, int retryTimes, BiPredicate<Integer, String> retryPredicate) {
        log.info("raw request urlStr = {}", urlStr);
        URI uri = null;
        try {
            uri = new URIBuilder(urlStr).build();
        } catch (URISyntaxException e) {
            throw new BaseException(HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, 500, "Url building error", e);
        }

        // set url
        HttpPost httpost = new HttpPost(uri);
        // set header
        if (headerList != null) {
            for (Map.Entry<String, String> entry : headerList.entrySet()) {
                httpost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        // set body
        if (StringUtils.hasText(bodyStr)) {
            StringEntity entity = new StringEntity(bodyStr, StandardCharsets.UTF_8);
            httpost.setEntity(entity);
        }

        int attempt = 0;
        do {
            CloseableHttpResponse response = null;
            try {
                response = client.execute(httpost);
                int httpCode = response.getStatusLine().getStatusCode();
                String resStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                log.info("raw response httpCode = {}, bodyStr = {}", httpCode, resStr);
                // if pass the check, stop looping and return response
                if (retryPredicate.test(httpCode, resStr)) {
                    JsonNode res = JsonUtils.objectMapper.readTree(resStr);
                    return new HttpResponseDTO(httpCode, res);
                }
                // otherwise close current response and continue to next attempt
                response.close();
            } catch (IOException e) {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException closeEx) {
                        log.warn("Failed to close response", closeEx);
                    }
                }
            }
            attempt++;
        } while (attempt < retryTimes);
        throw new BaseException(HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, 500, "Exceed max retry times.");
    }

    @Getter
    public static class HttpResponseDTO {
        private int httpCode;
        private JsonNode body;

        public HttpResponseDTO(int httpCode, JsonNode body) {
            this.httpCode = httpCode;
            this.body = body;
        }
    }

}
