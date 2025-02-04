package com.hhu.utils;

import java.util.Map;

/**
 * ThreadLocal 工具类
 */
@SuppressWarnings("all")
public class HHUThreadLocalUtil {
    //提供ThreadLocal对象,
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    //根据键获取值
    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }
	
    //存储键值对
    public static void set(Map<Object, Object> value){
        THREAD_LOCAL.set(value);
    }

    //清除ThreadLocal 防止内存泄漏
    public static void remove(){
        THREAD_LOCAL.remove();
    }

    public static Long getUserId(){
        Map<Object, Object> entries = (Map<Object, Object>) THREAD_LOCAL.get();
        Long userId = Long.parseLong(entries.get("userId").toString());
        return userId;
    }

    public static Map<Object, Object> getEntries(){
        Map<Object, Object> entries = (Map<Object, Object>) THREAD_LOCAL.get();
        return entries;
    }

}
