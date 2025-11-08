package com.jerry.common;

/**
 * 字符串工具类
 */
public class StringUtils {
    
    /**
     * 判断字符串是否为空
     * @param str 待检测的字符串
     * @return 若字符串为null或空字符串则返回true，否则返回false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    /**
     * 判断字符串是否不为空
     * @param str 待检测的字符串
     * @return 若字符串不为null且不为空字符串则返回true，否则返回false
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}