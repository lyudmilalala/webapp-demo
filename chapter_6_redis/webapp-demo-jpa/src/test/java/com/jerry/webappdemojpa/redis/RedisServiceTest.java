package com.jerry.webappdemojpa.redis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    private static final String TEST_KEY = "test_key_";
    private static final String TEST_VALUE = "test_value_";

    @BeforeEach
    void setUp() {
        // 清理可能存在的测试数据
        try {
            for (int i = 0; i < 5; i++) {
                redisService.delete(TEST_KEY + i);
            }
        } catch (Exception e) {
            // 忽略清理错误
        }
    }

    @Test
    void testSetAndGetWithoutTimeout() {
        String key = TEST_KEY + "no_timeout";
        String value = TEST_VALUE + "no_timeout";

        // 测试设置值（不设置超时）
        boolean setResult = redisService.set(key, value, null);
        assertTrue(setResult, "Set operation should succeed");

        // 测试获取值
        String retrievedValue = redisService.get(key);
        assertNotNull(retrievedValue, "Retrieved value should not be null");
        assertEquals(value, retrievedValue, "Retrieved value should match the set value");

        // 清理
        redisService.delete(key);
    }

    @Test
    void testSetAndGetWithTimeout() {
        String key = TEST_KEY + "with_timeout";
        String value = TEST_VALUE + "with_timeout";
        Long timeout = 3600L; // 1小时

        // 测试设置值（带超时）
        boolean setResult = redisService.set(key, value, timeout);
        assertTrue(setResult, "Set operation should succeed");

        // 测试获取值
        String retrievedValue = redisService.get(key);
        assertNotNull(retrievedValue, "Retrieved value should not be null");
        assertEquals(value, retrievedValue, "Retrieved value should match the set value");

        // 清理
        redisService.delete(key);
    }

    @Test
    void testGetNonExistentKey() {
        String nonExistentKey = TEST_KEY + "non_existent";
        
        // 确保键不存在
        redisService.delete(nonExistentKey);

        // 测试获取不存在的键
        String value = redisService.get(nonExistentKey);
        assertNull(value, "Value should be null for non-existent key");
    }

    @Test
    void testDelete() {
        String key = TEST_KEY + "delete";
        String value = TEST_VALUE + "delete";

        // 先设置值
        redisService.set(key, value, null);

        // 验证键存在
        assertTrue(redisService.exists(key), "Key should exist after set");

        // 删除键
        boolean deleted = redisService.delete(key);
        assertTrue(deleted, "Delete operation should succeed");

        // 验证键已删除
        assertFalse(redisService.exists(key), "Key should not exist after delete");
    }

    @Test
    void testDeleteNonExistentKey() {
        String nonExistentKey = TEST_KEY + "non_existent_delete";
        
        // 确保键不存在
        redisService.delete(nonExistentKey);

        // 尝试删除不存在的键
        boolean deleted = redisService.delete(nonExistentKey);
        assertFalse(deleted, "Delete operation should return false for non-existent key");
    }

    @Test
    void testExists() {
        String key = TEST_KEY + "exists";
        String value = TEST_VALUE + "exists";

        // 测试不存在的键
        assertFalse(redisService.exists(key), "Key should not exist initially");

        // 设置值
        redisService.set(key, value, null);

        // 测试存在的键
        assertTrue(redisService.exists(key), "Key should exist after set");

        // 清理
        redisService.delete(key);
    }

    @Test
    void testOverwriteExistingKey() {
        String key = TEST_KEY + "overwrite";
        String value1 = TEST_VALUE + "1";
        String value2 = TEST_VALUE + "2";

        // 设置第一个值
        redisService.set(key, value1, null);
        assertEquals(value1, redisService.get(key));

        // 覆盖为第二个值
        redisService.set(key, value2, null);
        assertEquals(value2, redisService.get(key), "Value should be overwritten");

        // 清理
        redisService.delete(key);
    }

    @Test
    void testSpecialCharacters() {
        String key = TEST_KEY + "special";
        String value = "Special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?中文测试🎉";

        // 测试包含特殊字符的值
        redisService.set(key, value, null);
        String retrievedValue = redisService.get(key);
        assertEquals(value, retrievedValue, "Special characters should be preserved");

        // 清理
        redisService.delete(key);
    }

    @Test
    void testEmptyValue() {
        String key = TEST_KEY + "empty";
        String value = "";

        // 测试空字符串
        redisService.set(key, value, null);
        String retrievedValue = redisService.get(key);
        assertEquals(value, retrievedValue, "Empty string should be preserved");

        // 清理
        redisService.delete(key);
    }

    @Test
    void testLongValue() {
        String key = TEST_KEY + "long";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("a");
        }
        String longValue = sb.toString();

        // 测试长字符串
        redisService.set(key, longValue, null);
        String retrievedValue = redisService.get(key);
        assertEquals(longValue, retrievedValue, "Long string should be preserved");
        assertEquals(1000, retrievedValue.length(), "Long string length should match");

        // 清理
        redisService.delete(key);
    }
}
