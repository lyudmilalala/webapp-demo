package com.jerry.userapp;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 添加JSON转换器
        converters.add(new MappingJackson2HttpMessageConverter());
        // 添加字符串转换器
        converters.add(new StringHttpMessageConverter());
    }
}
