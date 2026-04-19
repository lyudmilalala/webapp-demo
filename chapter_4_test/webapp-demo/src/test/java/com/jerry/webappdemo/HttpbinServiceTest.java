package com.jerry.webappdemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@SpringBootTest
public class HttpbinServiceTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSimpleGetSuccess() throws Exception {
        // 创建HttpbinService实例
        HttpbinService httpbinService = new HttpbinService();
        
        // 准备模拟数据
        String jsonResponse = "{\"args\":{},\"headers\":{\"Host\":\"httpbin.org\"}}";
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        BaseHttpClient.HttpResponseDTO mockResponse = new BaseHttpClient.HttpResponseDTO(200, jsonNode);
        
        // 使用Mockito模拟静态方法调用
        MockedStatic<BaseHttpClient> mockedStatic = mockStatic(BaseHttpClient.class);
        try {
            mockedStatic.when(() -> BaseHttpClient.httpGet(anyString(), anyMap()))
                       .thenReturn(mockResponse);
            
            // 调用被测试的方法
            CommonEntityResponse<?> result = httpbinService.simpleGet();
            
            // 验证结果
            assertNotNull(result);
            assertEquals(200, result.getStatus());
        } finally {
            mockedStatic.close();
        }
    }
    
    @Test
    public void testSimpleGetClientError() {
        // 创建HttpbinService实例
        HttpbinService httpbinService = new HttpbinService();
        
        // 准备模拟404错误数据
        BaseHttpClient.HttpResponseDTO mockResponse = mock(BaseHttpClient.HttpResponseDTO.class);
        when(mockResponse.getHttpCode()).thenReturn(404);
        
        // 使用Mockito模拟静态方法调用
        MockedStatic<BaseHttpClient> mockedStatic = mockStatic(BaseHttpClient.class);
        try {
            mockedStatic.when(() -> BaseHttpClient.httpGet(anyString(), anyMap()))
                       .thenReturn(mockResponse);
            
            // 调用被测试的方法
            CommonEntityResponse<?> result = httpbinService.simpleGet();
            
            // 验证结果
            assertNotNull(result);
            assertEquals(BaseHttpClient.HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, result.getStatus());
        } finally {
            mockedStatic.close();
        }
    }
    
    @Test
    public void testSimpleGetServerError() {
        // 创建HttpbinService实例
        HttpbinService httpbinService = new HttpbinService();
        
        // 准备模拟500错误数据
        BaseHttpClient.HttpResponseDTO mockResponse = mock(BaseHttpClient.HttpResponseDTO.class);
        when(mockResponse.getHttpCode()).thenReturn(500);
        
        // 使用Mockito模拟静态方法调用
        MockedStatic<BaseHttpClient> mockedStatic = mockStatic(BaseHttpClient.class);
        try {
            mockedStatic.when(() -> BaseHttpClient.httpGet(anyString(), anyMap()))
                       .thenReturn(mockResponse);
            
            // 调用被测试的方法
            CommonEntityResponse<?> result = httpbinService.simpleGet();
            
            // 验证结果
            assertNotNull(result);
            assertEquals(BaseHttpClient.HTTP_REQUEST_CALLER_SIDE_ERROR_CODE, result.getStatus());
        } finally {
            mockedStatic.close();
        }
    }
    
    @Test
    public void testSimpleGetBaseException() {
        // 创建HttpbinService实例
        HttpbinService httpbinService = new HttpbinService();
        
        // 使用Mockito模拟静态方法抛出异常
        MockedStatic<BaseHttpClient> mockedStatic = mockStatic(BaseHttpClient.class);
        try {
            mockedStatic.when(() -> BaseHttpClient.httpGet(anyString(), anyMap()))
                       .thenThrow(new BaseException(1001, "网络异常"));
            
            // 调用被测试的方法
            CommonEntityResponse<?> result = httpbinService.simpleGet();
            
            // 验证结果
            assertNotNull(result);
            assertEquals(1001, result.getStatus());
        } finally {
            mockedStatic.close();
        }
    }
}