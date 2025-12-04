package com.jerry.webappdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();
}
